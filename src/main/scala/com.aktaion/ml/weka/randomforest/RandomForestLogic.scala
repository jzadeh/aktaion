package com.aktaion.ml.weka.randomforest

import java.io.{BufferedReader, StringReader}
import java.util.Random
import com.aktaion.shell.CommandLineUtils
import weka.classifiers.Evaluation
import weka.classifiers.trees.RandomForest
import weka.core.Instances


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

}
