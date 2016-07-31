package com.aktaion.ml.weka.randomforest

import java.io.{BufferedReader, File, StringReader}
import java.util.Random

import com.aktaion.shell.CommandLineUtils
import weka.classifiers.{CostMatrix, Evaluation}
import weka.classifiers.meta.CostSensitiveClassifier
import weka.classifiers.trees.RandomForest
import weka.core.Instances
import weka.core.converters.ArffSaver
import weka.filters.Filter
import weka.filters.supervised.instance.Resample
import weka.filters.unsupervised.instance.Randomize


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
object RandomForestLogic {


  /**
    * This simple prototype trains and scores two sets of data. The input data
    * is what we build the machine learning classifier against
    * (in this case a random forest model see for more info https://en.wikipedia.org/wiki/Random_forest )
    *
    * @param trainingSet input file in .arff format to train the model
    * @param scoringSet file to score once we have built the model
    * @param numFolds number of folds in the random forest algorithm (cross validation?)
    * @param numTrees number of trees in each iteration of the random sampling of the micro behaviors
    */
  def trainWekaRandomForest(trainingSet: String,
                            scoringSet: String,
                            numFolds: Int,
                            numTrees: Int = 100) = {
    val lines = CommandLineUtils.getFileFromFileSystemPath(trainingSet).mkString("\n")
    val br = new BufferedReader(new StringReader(lines))
    val trainData: Instances = new Instances(br)

    //set the index of the class we are predicting
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

     weka.core.SerializationHelper.write("/Users/User/Aktaion/model.test",rf)
    //load model
   // String rootPath="/some/where/";
   // Classifier cls = (Classifier) weka.core.SerializationHelper.read(rootPath+"tree.model");

    val scoreNewLines = CommandLineUtils.getFileFromFileSystemPath(scoringSet).mkString("\n")
    val scoreBr = new BufferedReader(new StringReader(scoreNewLines))

    val scoreData: Instances = new Instances(scoreBr)
    scoreBr.close

    scoreData.setClassIndex(trainData.numAttributes - 1)

    val scored = new Evaluation(scoreData)
    val predictedOutput: Array[Double] = scored.evaluateModel(rf, scoreData)

    for (x <- predictedOutput) {

      if (x == 1.0)
        println("Exploit detected in window")
      else
        println("Benign window assoicated to behavior vector ")
    }

  }

//
//  def saveModel: ???
//
//  def loadModel:  ???
//
//  def scoreBroHttpFile: ???
//
//  def scoreGenericProxyFile: ???



  def crossValidationWekaRf(numFolds: Double, inputFilePath: String, outputPath: String) = {

    // val numFolds: Double = 10.0d
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

    //   ArffSaver saverTets = new ArffSaver();

    val lines = CommandLineUtils.getFileFromFileSystemPath(inputFilePath).mkString("\n")
    val br = new BufferedReader(new StringReader(lines))

    //val br = getWekaReaderFromResourcePath("/ml.weka/weather.arff")

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

        //  saverTraining.setFile(new File("/Users/User/Aktaion/wekaData/" + i + "_training.arff"))
        //    saverTets.setInstances(tempTesting)
        //   saverTets.setFile(new File("D:\\SumCost\\eclipse\\" + i + "_testing.arff"))
        saverTraining.writeBatch
        // saverTets.writeBatch
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
