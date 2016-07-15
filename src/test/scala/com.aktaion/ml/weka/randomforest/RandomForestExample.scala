/**
  * Copyright 2016 - Caspida Inc., All rights reserved.
  * This is Caspida proprietary and confidential material and its use
  * is subject to license terms.
  * Created on 7/7/16.
  *
  * @author joez
  */

package com.aktaion.ml.weka.randomforest

import java.io.{BufferedReader, FileReader, StringReader}
import java.net.URL
import java.util.Random

import com.aktaion.common.SimpleTestTools
import _root_.weka.classifiers.Evaluation
import _root_.weka.classifiers.trees.RandomForest
import _root_.weka.core.Instances

class RandomForestExample extends SimpleTestTools {
  //  @throws[Exception]
  //  def main(args: Array[String]) {
  //    var br: BufferedReader = null
  //    val numFolds: Int = 10
  //    br = new BufferedReader(new FileReader("D:\\martin\\DataSet\\Mozilla.arff"))
  //    val trainData: Instances = new Instances(br)
  //    trainData.setClassIndex(trainData.numAttributes - 1)
  //    br.close
  //    val rf: RandomForest = new RandomForest
  //    rf.setNumTrees(100)
  //    val evaluation: Evaluation = new Evaluation(trainData)
  //    evaluation.crossValidateModel(rf, trainData, numFolds, new Random(1))
  //    System.out.println(evaluation.toSummaryString("\nResults\n======\n", true))
  //    System.out.println(evaluation.toClassDetailsString)
  //    System.out.println("Results For Class -1- ")
  //    System.out.println("Precision=  " + evaluation.precision(0))
  //    System.out.println("Recall=  " + evaluation.recall(0))
  //    System.out.println("F-measure=  " + evaluation.fMeasure(0))
  //    System.out.println("Results For Class -2- ")
  //    System.out.println("Precision=  " + evaluation.precision(1))
  //    System.out.println("Recall=  " + evaluation.recall(1))
  //    System.out.println("F-measure=  " + evaluation.fMeasure(1))
  //  }


  test("Weka Random Forest Test Data Set") {

    //   var br: BufferedReader = null
    val numFolds: Int = 10
    // val br = new BufferedReader(new FileReader("D:\\martin\\DataSet\\Mozilla.arff"))
    val file: String = getFileStringFromResourcePath("/ml.weka/weather.arff")
    val lines = scala.io.Source.fromFile(file).getLines().mkString("\n")


    val sr = new StringReader(lines)
    val br = new BufferedReader(sr)
    //   val br = new BufferedReader(new FileReader("/ml.weka/weather.arff"))
    //  val br: URL = getClass.getResource("/ml.weka/weather.arff")


    val trainData: Instances = new Instances(br)
    trainData.setClassIndex(trainData.numAttributes - 1)
    br.close
    val rf: RandomForest = new RandomForest
    rf.setNumTrees(100)
    val evaluation: Evaluation = new Evaluation(trainData)
    evaluation.crossValidateModel(rf, trainData, numFolds, new Random(1))
    System.out.println(evaluation.toSummaryString("\nResults\n======\n", true))
    System.out.println(evaluation.toClassDetailsString)
    System.out.println("Results For Class -1- ")
    System.out.println("Precision=  " + evaluation.precision(0))
    System.out.println("Recall=  " + evaluation.recall(0))
    System.out.println("F-measure=  " + evaluation.fMeasure(0))
    System.out.println("Results For Class -2- ")
    System.out.println("Precision=  " + evaluation.precision(1))
    System.out.println("Recall=  " + evaluation.recall(1))
    System.out.println("F-measure=  " + evaluation.fMeasure(1))

  }


}

