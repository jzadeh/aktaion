package com.aktaion.ml.weka.randomforest

import java.io.{BufferedReader, File, StringReader}
import java.util.Random

import com.aktaion.DebugLoggingLogic
import com.aktaion.parser.{BroHttpLogEvent, BroHttpParser, NormalizedLogEvent, ParsingNormalizationLogic}
import com.aktaion.shell.CommandLineUtils
import weka.classifiers.{Classifier, CostMatrix, Evaluation}
import weka.classifiers.meta.CostSensitiveClassifier
import weka.classifiers.trees.RandomForest
import weka.core.Instances
import weka.core.converters.ArffSaver
import weka.filters.Filter
import weka.filters.supervised.instance.Resample
import weka.filters.unsupervised.instance.Randomize

import scala.util.Try


/**
  * Used as a way for the compiler to verify
  * if we are using the right labels in the machine
  * learning step of the program workflow
  */
object ClassLabel extends Enumeration {
  type ClassLabel = Value
  val EXPLOIT, BENIGN = Value
}


/**
  * Simple entry point into running a basic machine learning
  * algorithm called Random Forest
  *
  * For more information on building models in Weka see:
  * https://weka.wikispaces.com/Use+Weka+in+your+Java+code
  *
  *
  */
object RandomForestLogic extends DebugLoggingLogic {

  /**
    * serialize the trained model to a file
    *
    * @param rf the random forest model
    * @param fileName
    */
  def saveRandomForestToFile(rf: RandomForest, fileName: String) = {
    weka.core.SerializationHelper.write(fileName, rf)
  }

  /**
    *
    * @param fileName
    * @return
    */
  def loadRandomForestFromFile(fileName: String): Classifier = {
    var cls: Classifier = null
    try {
      cls = weka.core.SerializationHelper.read(fileName).asInstanceOf[Classifier]
    } catch {
      case e1: Exception => e1.printStackTrace
    }
    return cls
  }

  /**
    * This simple prototype trains and scores two sets of data. The input data
    * is what we build the machine learning classifier against
    * (in this case a random forest model see for more info https://en.wikipedia.org/wiki/Random_forest )
    *
    * @param trainingFileName    input file in .arff format to train the model
    * @param outputModelFileName file to score once we have built the model
    * @param numFolds            number of folds in the random forest algorithm (cross validation?)
    * @param numTrees            number of trees in each iteration of the random sampling of the micro behaviors
    */
  def trainWekaRandomForest(trainingFileName: String,
                            outputModelFileName: String,
                            numFolds: Int,
                            numTrees: Int = 100) = {

    val lines = CommandLineUtils.getFileFromFileSystemPath(trainingFileName).mkString("\n")
    val br = new BufferedReader(new StringReader(lines))
    val trainData: Instances = new Instances(br)

    //set the index of the class we are predicting (Benign, Exploit)
    trainData.setClassIndex(trainData.numAttributes - 1)
    br.close

    val rf: RandomForest = new RandomForest
    rf.setNumTrees(numTrees)
    val evaluation: Evaluation = new Evaluation(trainData)

    evaluation.crossValidateModel(rf, trainData, numFolds, new Random(1))

    /**
      * This builds the actual machine learning model we will use
      * to predict if the set of micro behaviors has an exploit or not
      */
    rf.buildClassifier(trainData)

    println("Writing random forest model to file " + outputModelFileName)
    saveRandomForestToFile(rf, outputModelFileName)
  }

  /**
    *
    * @param fileNameToScore bro http.log file path
    * @param fileNameOfModel .model (serialized java object) file path
    * @param windowSize
    * @return list of malicious indicators if model predicts exploit behavior
    */
  def scoreBroHttpFile(fileNameToScore: String,
                       fileNameOfModel: String,
                       windowSize: Int): Option[IocsExtracted] = {

    val myClassifier = loadRandomForestFromFile(fileNameOfModel)

    //we set the class label to a dummy variable because it is not used in the prediction step
    val wekaData: String = WekaUtilities.extractBroHttpLogToWekaFormat(fileNameToScore, ClassLabel.BENIGN, windowSize)
    val scoreBr: BufferedReader = new BufferedReader(new StringReader(wekaData))
    val scoreData: Instances = new Instances(scoreBr)
    scoreBr.close

    //remove the last column on the right (the class label) since the model will predict its value instead
    scoreData.setClassIndex(scoreData.numAttributes - 1)
    val scored = new Evaluation(scoreData)
    val predictedOutput: Array[Double] = scored.evaluateModel(myClassifier, scoreData)

    //cheap way to initialize IOC data across multiple windows
    var domains = Set[String]()
    var ips = Set[String]()
    var files = Set[String]()

    for ((x, index) <- predictedOutput.zipWithIndex) {
      if (x == 1.0) {
        println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        println("!!!!!!!!!!!! The Presence of Evil is Near !!!!!!!!!!!!!")
        println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        println("!!!!! Exploit Behavior Detected In Window Number " + index + "!!!!!!!!!!")
        println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        println("!!!!!!!!!! Evil Is Confirmed JSON For the IOCs Below!!!!!!!!!!!!!!!!")
        println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        val iocs: IocsExtracted = extractBroIocsFromMaliciousWindow(fileNameToScore, index, windowSize)
        val jsonOutput: String = convertIocsToJson(iocs)

        domains = domains ++ iocs.suspiciousDomains
        ips = ips ++ iocs.suspiciousIps
        files = files ++ iocs.suspiciousFileNames

        println(jsonOutput)
        println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
      }
      else {
        val myData: String = wekaData

        logger.info("Benign window associated to behavior vector in window number " + index)
      }
    }

    if (domains.size > 0 || ips.size > 0 || files.size > 0) {
      val fullIocData = IocsExtracted(ips,domains,files)
      return Some(fullIocData)
    } else {
      return None
    }
  }

  /**
    * If we score a .arff file as malicious then we recover the original data by doing one more pass
    * (expensive) and building a list of IOCs out of that window
    *
    * @param inputFileName Bro filename (this method only works with bro files so far) //todo change to normalized event
    * @param indexOfEvil
    * @return
    */
  def extractBroIocsFromMaliciousWindow(inputFileName: String,
                                        indexOfEvil: Int,
                                        windowSize: Int): IocsExtracted = {

    val lines: Array[String] = CommandLineUtils.getFileFromFileSystemPath(inputFileName)

    val broHttpData: Seq[Option[BroHttpLogEvent]] = lines.map { x => BroHttpParser.tokenizeData(x) }.toSeq
    val normData: Seq[Option[NormalizedLogEvent]] = broHttpData.map(x => ParsingNormalizationLogic.normalizeBroLog(x))
    val parsedEvents: Seq[NormalizedLogEvent] = normData.flatMap(x => x)

    val dataBrokenIntoWindows: List[Seq[NormalizedLogEvent]] = if (windowSize >= parsedEvents.size) {
      parsedEvents.sliding(windowSize).toList
    } else {
      parsedEvents.sliding(parsedEvents.size).toList
    }

    val iocsInWindow: Seq[NormalizedLogEvent] = dataBrokenIntoWindows(indexOfEvil)
    val ips: Set[String] = iocsInWindow.map(x => x.destinationIp).toSet
    val domains: Set[String] = iocsInWindow.map(x => x.urlMetaData.host).toSet
    val files: Set[String] = iocsInWindow.map(x => x.urlMetaData.file).toSet

    //todo some hackey logic here to filter out lon files
    val filterFiles: Set[String] = files.filter{x=> (x.contains('.') && Try(x.split('?').tail.head.length).getOrElse(0) < 20)  }

    val data = IocsExtracted(ips, domains, filterFiles)
    return IocsExtracted(ips, domains, filterFiles)

  }

  /**
    * take a case class of data and turn into a json
    *
    * @param iocs
    * @return
    */
  def convertIocsToJson(iocs: IocsExtracted): String = {
    import net.liftweb.json._
    import net.liftweb.json.Serialization.write
    implicit val formats = DefaultFormats
    val jsonString: String = write(iocs)

    return jsonString
  }

  /**
    * used to write the output of the bad iocs to a json file
    *
    * @param suspiciousIps
    * @param suspiciousDomains
    * @param suspiciousFileNames
    */
  case class IocsExtracted(suspiciousIps: Set[String], suspiciousDomains: Set[String], suspiciousFileNames: Set[String])


  def crossValidationWekaRf(numFolds: Double, inputFilePath: String, outputPath: String) = {

    var precisionOne: Double = 0.0d
    var recallOne: Double = 0.0d
    var fmeansureOne: Double = 0.0d
    var precisionTwo: Double = 0.0d
    var recallTwo: Double = 0.0d
    var fmeansureTwo: Double = 0.0d
    var ROCone: Double = 0.0d
    var ROCtwo: Double = 0.0d
    val PRCone: Double = 0.0d
    val PRCtwo: Double = 0.0d

    val lines = CommandLineUtils.getFileFromFileSystemPath(inputFilePath).mkString("\n")
    val br = new BufferedReader(new StringReader(lines))
    val saverTraining: ArffSaver = new ArffSaver
    var trainData: Instances = new Instances(br)
    trainData.setClassIndex(trainData.numAttributes - 1)
    br.close
    val randFilterMain: Randomize = new Randomize
    randFilterMain.setInputFormat(trainData)
    trainData = Filter.useFilter(trainData, randFilterMain)
    val mySize: Int = (trainData.numInstances / numFolds).toInt
    var begin: Int = 0
    var end: Int = mySize - 1
    System.out.println("Total mySize of instances" + trainData.numInstances + " , flod mySize=" + mySize)
    var i: Int = 1

    while (i <= numFolds) {
      {
        System.out.println("Iteration # " + i + " Begin =" + begin + " , end=" + end)
        val tempTraining: Instances = new Instances(trainData)
        val tempTesting: Instances = new Instances(trainData, begin, (end - begin))
        var j: Int = 0

        while (j < (end - begin)) {
          {
            tempTraining.delete(begin)
          }
          ({
            j += 1;
            j - 1
          })
        }

        val resample: Resample = new Resample
        resample.setBiasToUniformClass(0.5f)
        resample.setInvertSelection(false)
        resample.setNoReplacement(false)
        resample.setRandomSeed(1)
        resample.setInputFormat(tempTraining)
        System.out.println("Number of instances before filter " + tempTraining.numInstances)
        val resmapleTempTraining: Instances = Filter.useFilter(tempTraining, resample)
        System.out.println("Number of instances after filter " + resmapleTempTraining.numInstances)
        val randomForest: RandomForest = new RandomForest
        randomForest.setNumTrees(100)
        System.out.println("Started building the model #" + i)
        val costSensitiveClassifier: CostSensitiveClassifier = new CostSensitiveClassifier
        val costMatrix: CostMatrix = new CostMatrix(2)
        costMatrix.setCell(1, 0, 2d)
        costSensitiveClassifier.setClassifier(randomForest)
        costSensitiveClassifier.setCostMatrix(costMatrix)
        costSensitiveClassifier.buildClassifier(resmapleTempTraining)
        saverTraining.setInstances(resmapleTempTraining)
        saverTraining.setFile(new File(outputPath + i + "_training.arff"))

        saverTraining.writeBatch
        System.out.println("Done with building the model")
        val evaluation: Evaluation = new Evaluation(tempTesting)
        evaluation.evaluateModel(costSensitiveClassifier, tempTesting)
        System.out.println("Results For Class -1- ")
        System.out.println("Precision=  " + evaluation.precision(0))
        System.out.println("Recall=  " + evaluation.recall(0))
        System.out.println("F-measure=  " + evaluation.fMeasure(0))
        System.out.println("ROC=  " + evaluation.areaUnderROC(0))
        System.out.println("Results For Class -2- ")
        System.out.println("Precision=  " + evaluation.precision(1))
        System.out.println("Recall=  " + evaluation.recall(1))
        System.out.println("F-measure=  " + evaluation.fMeasure(1))
        System.out.println("ROC=  " + evaluation.areaUnderROC(1))
        precisionOne += evaluation.precision(0)
        recallOne += evaluation.recall(0)
        fmeansureOne += evaluation.fMeasure(0)
        precisionTwo += evaluation.precision(1)
        recallTwo += evaluation.recall(1)
        fmeansureTwo += evaluation.fMeasure(1)
        ROCone += evaluation.areaUnderROC(0)
        ROCtwo += evaluation.areaUnderROC(1)
        begin = end + 1
        end += mySize
        if (i == (numFolds - 1)) {
          end = trainData.numInstances
        }
      }
      ({
        i += 1;
        i - 1
      })
    }

    System.out.println("####################################################")
    System.out.println("Results For Class -1- ")
    System.out.println("Precision=  " + precisionOne / numFolds)
    System.out.println("Recall=  " + recallOne / numFolds)
    System.out.println("F-measure=  " + fmeansureOne / numFolds)
    System.out.println("ROC=  " + ROCone / numFolds)
    System.out.println("PRC= " + PRCone / numFolds)

    System.out.println("Results For Class -2- ")
    System.out.println("Precision=  " + precisionTwo / numFolds)
    System.out.println("Recall=  " + recallTwo / numFolds)
    System.out.println("F-measure=  " + fmeansureTwo / numFolds)
    System.out.println("ROC=  " + ROCtwo / numFolds)
    System.out.println("PRC= " + PRCtwo / numFolds)

  }

}
