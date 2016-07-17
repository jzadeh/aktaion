/**
  * Copyright 2016 - Caspida Inc., All rights reserved.
  * This is Caspida proprietary and confidential material and its use
  * is subject to license terms.
  * Created on 7/15/16.
  *
  * @author joez
  */

package com.aktaion.common


import java.sql.Timestamp
import java.text.SimpleDateFormat
import org.apache.spark.sql.{DataFrame, SQLContext}
import org.scalatest.{BeforeAndAfter, FunSuite, Matchers}
import org.apache.spark.{SparkConf, SparkContext}
import scala.util.{Failure, Success, Try}
import org.apache.spark.{SparkConf, SparkContext}



class SparkTestTools extends FunSuite with Matchers with BeforeAndAfter {

  //set spark to run in local mode
  val sparkConf = new SparkConf().
    setAppName("TimeSeries").setMaster("local[1]").
    set("spark.executor.memory", "8g")

  //.set("SPARK_LOCAL_IP", "127.0.0.1")

  val sc = new SparkContext(sparkConf)
  val sqlContext = new org.apache.spark.sql.SQLContext(sc)

  //disable verbose printing when we read parquet files
  sc.setLogLevel("WARN")

  //not needed now but used for modifying JVM properties
  val systemProps = System.getProperties

  def getTimeStamp(s: String): Timestamp = s match {

    case "" => {
      val format = new SimpleDateFormat("yyyy-MM-dd")

      new Timestamp(format.parse("0001-01-01").getTime)
    }
    case _ => {
      val format = new SimpleDateFormat("yyyy-MM-dd")

      Try(new Timestamp(format.parse(s).getTime)) match {
        case Success(t) => t
        case Failure(_) => new Timestamp(format.parse("0001-0-01").getTime)
      }
    }
  }

}