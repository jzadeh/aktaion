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
}


case class MicroBehaviorWindow(behaviorVector: List[MicroBehaviorData]) extends MicroBehaviorSet

case class MicroBehaviorData(behaviorName: String,
                             behaviorDescription: String,
                             var numData: Double = 0.0,
                             var catData: String = "")
  extends MicroBehaviorNumericLike with MicroBehaviorCategoricalLike {

  def valueToCsv = behaviorName + "," + numData.toString + "," + catData
}


/**
  * Used as a way for the compiler to verify if we are using the right labels in the machine
  * learning step of the program workflow
  */
object ClassLabel extends Enumeration {
  type ClassLabel = Value
  val EXPLOIT, BENIGN = Value
}
