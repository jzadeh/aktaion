package com.aktaion.ml.weka.randomforest

import java.io.{File, FileWriter}

import com.aktaion.ml.behaviors.MicroBehaviorData
import com.aktaion.ml.learning.BehaviorExtractionLogic
import com.aktaion.ml.weka.randomforest.ClassLabel.ClassLabel
import com.aktaion.parser._
import com.aktaion.shell.CommandLineUtils

object WekaUtilities {

  /**
    *
    * @param readDirectory
    * @param singleFilePath
    * @param format
    * @param classLabel
    * @param writeIndividualFiles
    */
  def extractAndNormalizeDataIntoWekaFormat(readDirectory: String,
                                            singleFilePath: String,
                                            format: String,
                                            classLabel: ClassLabel,
                                            writeIndividualFiles: Boolean) = {
    /**
      * Iterate over a list of file names from a directory or list of sub directories
      */
    val fileIterator: Iterator[File] = CommandLineUtils.
      GetFileTree(new File(readDirectory)).
      filter(_.getName.endsWith(format)).toIterator

    //cheap way to collect weka data we need to build a single .arff file
    var wekaHeader = ""
    var wekaDataAcrossAllFiles = ""

    try {
      for (file <- fileIterator) {
        //Logic to build full path (unix style) for writing individual .arff files
        val fileName = file.toString.split("/").last
        val directoryName = file.toString.split("/").reverse.tail.reverse.mkString("/") + "/"
        val totalStr = directoryName + fileName
        val writeStr = totalStr.replace(format, ".arff")

        println("Crawling " + totalStr + " for data...")

        val lines: Array[String] = CommandLineUtils.getFileFromFileSystemPath(totalStr)

        println("Found " + lines.length + " lines in file.  Attempting to parse.")

        /**
          * Start normalization logic here
          */
        var normData = Seq[Option[NormalizedLogEvent]]()

        if (totalStr.contains("http.log")) {
          val broHttpData: Seq[Option[BroHttpLogEvent]] = lines.map { x => BroHttpParser.tokenizeData(x) }.toSeq
          normData = broHttpData.map(x => ParsingNormalizationLogic.normalizeBroLog(x))
        }
        else if (totalStr.contains("webgateway")) {
          val proxyData: Seq[Option[GenericProxyLogEvent]] = lines.map { x => GenericProxyParser.tokenizeData(x) }.toSeq
          normData = proxyData.map(x => ParsingNormalizationLogic.normalizeProxyLog(x))
        }

        val parsedData: Seq[NormalizedLogEvent] = normData.flatMap(x => x)

        println("Parsed " + parsedData.length + " total lines.")

        val mbData: Seq[List[MicroBehaviorData]] = BehaviorExtractionLogic.transformSeqOfLogLines(parsedData, 5).get
        val wekaData: String = BehaviorExtractionLogic.convertBehaviorVectorToWeka(mbData, totalStr, classLabel)

        if (wekaHeader.size < 2) {
          wekaHeader = wekaData.split("@data")(0) + "@data"
        }
        val stripHeader = wekaData.split("@data")(1)
        val dropLastNewline = stripHeader.reverse.tail.reverse
        wekaDataAcrossAllFiles = wekaDataAcrossAllFiles + dropLastNewline

        if (writeIndividualFiles == true) {
          val fw = new FileWriter(writeStr, true)
          fw.write(wekaHeader)
          wekaDataAcrossAllFiles.foreach(line => fw.write(line))
          fw.close()
        }

      }
    } catch {
      case e: java.util.NoSuchElementException => //System.out.println("Exception " + e + " at fileIterator " + fileIterator)
    }


    println("Removing old scoring data: " + singleFilePath)
    val oldFile = new java.io.File(singleFilePath)
    oldFile.delete()

    println("Writing new scoring data: " + singleFilePath)
    val fw = new FileWriter(singleFilePath, true)

    fw.write(wekaHeader)
    wekaDataAcrossAllFiles.foreach(line => fw.write(line))
    fw.close()
  }


}
