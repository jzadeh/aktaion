package com.aktaion.common

import java.io.{BufferedReader, StringReader}
import java.sql.Timestamp
import java.text.SimpleDateFormat

import org.scalatest.{BeforeAndAfter, FunSuite, Matchers}

import scala.util.{Failure, Success, Try}

class SimpleTestTools extends FunSuite with Matchers with BeforeAndAfter {

  //not needed now but used for modifying JVM properties
  val systemProps = System.getProperties

    def getTimeStamp(s: String) : Timestamp = s match {

      case "" => {
        val format = new SimpleDateFormat("yyyy-MM-dd")

        new Timestamp(format.parse("0001-01-01").getTime)
      }
      case _ => {
        val format = new SimpleDateFormat("yyyy-MM-dd")

        Try(new Timestamp(format.parse(s).getTime)) match {
          case Success(t) => t
          case Failure(_) =>  new Timestamp(format.parse("0001-0-01").getTime)
        }
      }
    }

    /**
      *
      * @param resourcePath
      * @return
      */
    def getFileStringFromResourcePath(resourcePath: String) =
      getClass.getResource(resourcePath).getFile

    /**
      *
      * @param file
      * @return Array of string with end of line split using "\n"
      */
    def getLinesFromFile(file: String ): Array[String] = {
      scala.io.Source.fromFile(file).getLines().toArray
    }


  /**
    * Weka library needs to read in a file as a special type
    *
    * @param resourcePath
    * @return our file as the type [[BufferedReader]] in compliance with older java parsing methods
    */
  def getWekaReaderFromResourcePath(resourcePath: String): BufferedReader = {
    val file = getFileStringFromResourcePath(resourcePath)
    val lines = scala.io.Source.fromFile(file).getLines().mkString("\n")

    val br = new BufferedReader(new StringReader(lines))

    return br
  }


}
