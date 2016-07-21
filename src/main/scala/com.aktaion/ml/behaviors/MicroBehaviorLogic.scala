package com.aktaion.ml.behaviors


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

trait MicroBehaviorCategoricalLike  {
  //just a nicer way to reference the generic value we rename data below
  var catData: String

//  override def data = catData
}

trait MicroBehaviorSet {
  def behaviorVector: List[MicroBehaviorData]

  def vectorToString = behaviorVector.map(x => x.valueToCsv).mkString(",")

  def printBehaviorVector = println(vectorToString)
}


case class MicroBehaviorData(behaviorName: String,
                             behaviorDescription: String,
                             var numData: Double = 0.0,
                             var catData: String = "")
  extends MicroBehaviorNumericLike with MicroBehaviorCategoricalLike {

  def valueToCsv = behaviorName + "," + numData.toString + "," + catData
}



