package com.aktaion.shell

import java.io._
import java.util.zip.GZIPInputStream

import com.aktaion.parser._
import weka.classifiers.{CostMatrix, Evaluation}
import weka.classifiers.meta.CostSensitiveClassifier
import weka.classifiers.trees.RandomForest
import weka.core.Instances
import weka.core.converters.ArffSaver
import weka.filters.Filter
import weka.filters.supervised.instance.Resample
import weka.filters.unsupervised.instance.Randomize

import scala.io.{BufferedSource, Source}

object CommandLineUtils {

  /**
    * Recursively walk a directory and get a list of files
    *
    * @param f
    * @return
    */
  def GetFileTree(f: File): Stream[File] =
    f #:: (if (f.isDirectory) f.listFiles().toStream.flatMap(GetFileTree)
    else Stream.empty)

  /**
    *
    * @param readDirectory input read path
    * @param writeFile     ouput filename
    * @param format        "pcap" or "gzip"
    */
  def extractPcapDataFromDirectory(readDirectory: String,
                                   writeFile: String,
                                   format: String) = {

    val directoryname = "/Users/User/Aktaion/data/exploitData/"

    val fileIterator = GetFileTree(new File(directoryname)).filter(_.getName.endsWith(format)).toIterator

    for (file <- fileIterator) {
      val fileName = file.toString.split("/").last.dropRight(3) + format
      println(fileName)
      val directoryName = file.toString.split("/").reverse.tail.reverse.mkString("/") + "/"
      println(directoryName)
      val totalStr = directoryName + fileName
      println(totalStr)
      val fw = new FileWriter(totalStr, true)
      println("Crawling " + file + " for data...")

      var rawFile: BufferedSource = null
      if (format == "gzip") {
        rawFile = Source.fromInputStream(new GZIPInputStream(new BufferedInputStream(new FileInputStream(file))))
      } else {
        rawFile = Source.fromInputStream(new BufferedInputStream(new FileInputStream(file)))
      }

      while (rawFile.hasNext) {
        val line = rawFile.next
        fw.write(line)

      }
      fw.close()
    }

  }


  /**
    *
    * @param file
    */
  def executeBroSimpleDebugLogic(file: String) = {
    val broLogic: BroCommandLineInteractionLogic = new BroCommandLineInteractionLogic(file)
    if (broLogic.output == true) {
      val broPath = findFilePathRelativeToJar()

      //file is generated in same directory as the jar
      val broHttpFile: String = broPath + "/http.log"
      //  System.out.println(" Bro HTTP FilePath" + broPath)
      val broHttpData: Array[String] = CommandLineUtils.getFileFromFileSystemPath(broHttpFile)
      val parsedData: Array[BroHttpLogEvent] = broHttpData.flatMap { x => BroHttpParser.tokenizeData(x) }
      //  System.out.println(" File Length" + broHttpData.length)
      // CommandLineUtils.debugBroArray(broHttpData)
    }
  }

  /**
    *
    * @return
    */
  def findFilePathRelativeToJar(): String = {
    val jarPath: File = new File(classOf[UserInteractionLogic].getProtectionDomain.getCodeSource.getLocation.getPath)
    val absolutePath: String = jarPath.getParentFile.getAbsolutePath
    return absolutePath
  }

  /**
    * Only will work on unix type paths
    *
    * @return
    */
  def tryToFindPathToDataInSourceCode(numOfSubDirs: Int = 4): String = {
    val pathStr = findFilePathRelativeToJar()
    val splitStr = pathStr.split("/").take(numOfSubDirs).mkString("/")
    val dataPath = splitStr + "/data/"
    return dataPath
  }


  /**
    *
    * @param input
    * @return
    */
  def checkBroSortedLowToHigh(input: Seq[BroHttpLogEvent]): Seq[BroHttpLogEvent] = {
    val firstTime = input.head.tsDouble
    val reverseData = input.reverse
    val lastTime = reverseData.head.tsDouble
    if (firstTime < lastTime) return input else return reverseData
  }

  /**
    *
    * @param input
    * @return
    */
  def checkProxySortedLowToHigh(input: Seq[GenericProxyLogEvent]): Seq[GenericProxyLogEvent] = {
    val firstTime = input.head.tsJavaTime.getTime
    val reverseData = input.reverse
    val lastTime = reverseData.head.tsJavaTime.getTime
    if (firstTime < lastTime) return input else return reverseData
  }

  def checkTimeSortedLowToHigh(input: Seq[NormalizedLogEvent]): Seq[NormalizedLogEvent] = {
    val firstTime = input.head.tsJavaTime.getTime
    val reverseData = input.reverse
    val lastTime = reverseData.head.tsJavaTime.getTime
    if (firstTime < lastTime) return input else return reverseData
  }



  /**
    *
    * @param fileName
    * @return
    */
  def getFileFromFileSystemPath(fileName: String): Array[String] = {
    scala.io.Source.fromFile(fileName).getLines().toArray
  }

  /**
    *
    * @param array
    */
  def debugBroArray(array: Array[String]) = {
    for (logLine <- array) {
      println(logLine)
      val parsedLine = BroHttpParser.tokenizeData(logLine)
      println(parsedLine)
    }
  }





}

