package com.aktaion.ml.behaviors

import java.io.{BufferedWriter, File, FileWriter}


/**
  * Micro Behavior: Main abstraction for individual unit of behavior
  */
trait MicroBehaviorLike {
  //  val behaviorName: String
  //  val behaviorDescription: String
  //
  //  /**
  //    * Represents the information
  //    * for a single data point whose
  //    * type can vary depending on
  //    * which kind of behavior we want to study
  //    */
  //  def data: Any

  /**
    * Printing and debug logic
    *
    */
}

trait MicroBehaviorNumericLike {
  //just a nicer way to reference the generic value we rename data below
  var numData: Double

  // override def data = numData
}

trait MicroBehaviorCategoricalLike {
  //just a nicer way to reference the generic value we rename data below
  var catData: String

  //  override def data = catData
}

trait MicroBehaviorSet {
  def behaviorVector: List[MicroBehaviorData]

  def vectorToString = behaviorVector.map(x => x.valueToCsv).mkString(",")

  def printBehaviorVector = println(vectorToString)

  def convertBehaviorVectorToWeka(behaviorOverWindows: Seq[List[MicroBehaviorData]],
                                  fileName: String) = {
    val title = "@relation microbehaviors\n\n"
    var attributes = ""
    var csvRows = "@data\n"
    for ( (window,ind) <- behaviorOverWindows.zipWithIndex) {
      for ((data, index) <- window.zipWithIndex) {
        //ONLY DO THE COMPUTATION ONCE
        if (ind == 0) {
          attributes = attributes + "@attribute " +
          data.behaviorName.toLowerCase + "real\n"
        }
        if (index < window.size ) {
          csvRows = data.numData.toString + ","
        } else {
          csvRows = data.numData.toString + "\n"
        }
      }
    }

    val fullText = title + attributes + csvRows

    val file = new File(fileName)
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(fullText)
    bw.close()
  }

}


case class MicroBehaviorWindow(behaviorVector: List[MicroBehaviorData]) extends MicroBehaviorSet

case class MicroBehaviorData(behaviorName: String,
                             behaviorDescription: String,
                             var numData: Double = 0.0,
                             var catData: String = "")
  extends MicroBehaviorNumericLike with MicroBehaviorCategoricalLike {

  def valueToCsv = behaviorName + "," + numData.toString + "," + catData
}



