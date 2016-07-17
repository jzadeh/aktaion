package com.aktaion.shell

import java.io.File
import com.aktaion.parser.BroHttpParser

object CommandLineUtils {

  def getFileFromFileSystemPath(fileName: String): Array[String] = {
    scala.io.Source.fromFile(fileName).getLines().toArray
  }

  def debugBroArray(array: Array[String]) = {
    val httpParser = new BroHttpParser
    for (logLine <- array) {
      println(logLine)
      val parsedLine = httpParser.tokenizeData(logLine)
      println(parsedLine)
    }
  }

  def executeBroLogic(file: String) = {
    val broLogic: BroUserInputLogic = new BroUserInputLogic(file)

    if (broLogic.output == true) {
      val jarPath: File = new File(classOf[UserInteractionLogic].getProtectionDomain.getCodeSource.getLocation.getPath)
      val broPath: String = jarPath.getParentFile.getAbsolutePath
      val broHttpFile: String = broPath + "/http.log"
      System.out.println(" Bro HTTP FilePath" + broPath)
      val broHttpData: Array[String] = CommandLineUtils.getFileFromFileSystemPath(broHttpFile)
      System.out.println(" File Length" + broHttpData.length)
      CommandLineUtils.debugBroArray(broHttpData)
    }

  }


}

