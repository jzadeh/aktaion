
package com.aktaion.ml.weka.randomforest

import java.io.{BufferedReader, FileReader, StringReader}
import java.net.URL
import java.util.Random

import com.aktaion.common.SimpleTestTools
import _root_.weka.classifiers.Evaluation
import _root_.weka.classifiers.trees.RandomForest
import _root_.weka.core.Instances

class RandomForestExample extends SimpleTestTools {

  ignore("Weka Random Forest Test Data Set") {

    val numFolds: Int = 10

    val br = getWekaReaderFromResourcePath("/ml.weka/weather.arff")
  //  val br = getWekaReaderFromResourcePath("/ml.weka/contact-lenses.arff")

  //weka.classifiers.trees.RandomTree: Cannot handle numeric class!
    //val br = getWekaReaderFromResourcePath("/ml.weka/cpu.arff")

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

