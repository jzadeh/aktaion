package com.aktaion.ml.weka.randomforest

import java.io.{BufferedReader, StringReader}
import java.util.Random

import com.aktaion.shell.CommandLineUtils
import weka.classifiers.Evaluation
import weka.classifiers.trees.RandomForest
import weka.core.Instances

object RandomForestLogic {

  def trainWekaRandomForest(trainingSet: String,
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

    //is this needed for training?
    evaluation.crossValidateModel(rf, trainData, numFolds, new Random(1))

    val myClassifier = rf.buildClassifier(trainData)

    val testLines = CommandLineUtils.getFileFromFileSystemPath("/Users/User/Aktaion/data/exploitData.arff").mkString("\n")
    val testBr = new BufferedReader(new StringReader(testLines))

    val testData: Instances = new Instances(testBr)
    testBr.close

    testData.setClassIndex(trainData.numAttributes - 1)

    val scored = new Evaluation(testData)
    val predictions: Array[Double] = scored.evaluateModel(rf, testData)

    for (x<- predictions){
      println("Value of Predicted Label: " + x)
    }

  }

}
