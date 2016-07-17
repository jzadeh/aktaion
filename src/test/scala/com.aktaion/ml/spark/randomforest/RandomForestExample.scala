///**
//  * Copyright 2016 - Caspida Inc., All rights reserved.
//  * This is Caspida proprietary and confidential material and its use
//  * is subject to license terms.
//  * Created on 7/15/16.
//  *
//  * @author joez
//  */
//
//package com.aktaion.ml.spark.randomforest
//
//import com.aktaion.common.SparkTestTools
//import org.apache.spark.mllib.tree.RandomForest
//import org.apache.spark.mllib.tree.model.RandomForestModel
//import org.apache.spark.mllib.util.MLUtils
//
//
//class RandomForestExample extends SparkTestTools {
//
//  ignore("Spark MlLib Random Forest Test Data Set") {
//
//
//    // Load and parse the data file.
//    val data = MLUtils.loadLibSVMFile(sc, "src/test/resources/ml.spark/sample_libsvm_data.txt")
//    // Split the data into training and test sets (30% held out for testing)
//    val splits = data.randomSplit(Array(0.7, 0.3))
//    val (trainingData, testData) = (splits(0), splits(1))
//
//    // Train a RandomForest model.
//    // Empty categoricalFeaturesInfo indicates all features are continuous.
//    val numClasses = 2
//    val categoricalFeaturesInfo = Map[Int, Int]()
//    val numTrees = 3 // Use more in practice.
//    val featureSubsetStrategy = "auto" // Let the algorithm choose.
//    val impurity = "gini"
//    val maxDepth = 4
//    val maxBins = 32
//
//    val model = RandomForest.trainClassifier(trainingData, numClasses, categoricalFeaturesInfo,
//      numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins)
//
//    // Evaluate model on test instances and compute test error
//    val labelAndPreds = testData.map { point =>
//      val prediction = model.predict(point.features)
//      (point.label, prediction)
//    }
//
//
//
//    val testErr = labelAndPreds.filter(r => r._1 != r._2).count.toDouble / testData.count()
//    println("Test Error = " + testErr)
//    println("Learned classification forest model:\n" + model.toDebugString)
//
//    // Save and load model
//    //    model.save(sc, "target/tmp/myRandomForestClassificationModel")
//    //    val sameModel = RandomForestModel.load(sc, "target/tmp/myRandomForestClassificationModel")
//
//
//  }
//}
