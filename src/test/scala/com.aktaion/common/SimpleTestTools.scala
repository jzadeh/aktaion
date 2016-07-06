package com.aktaion.common

import java.sql.Timestamp
import java.text.SimpleDateFormat
import org.scalatest.{FunSuite, BeforeAndAfter, Matchers}
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
      * @return string with end of line split using "\n"
      */
    def getLinesFromFile(file: String ): Array[String] = {
      scala.io.Source.fromFile(file).getLines().toArray
    }


}
