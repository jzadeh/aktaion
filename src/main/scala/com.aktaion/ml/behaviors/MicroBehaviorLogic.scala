package com.aktaion.ml.behaviors


/**
  * Micro Behavior: Main abstraction for individual unit of behavior
  */
trait MicroBehavior {
  val behaviorName: String
  val behaviorDescription: String

  /**
    *  Represents the primary abstraction
    *  for a single data point whose
    *  type can vary depending on
    *  which kind of behavior we want to study
    */
  def data: Any

  /**
    * Printing and debug logic
    *
    */
  def valueToCsv = behaviorName + "," + data.toString
}

trait MicroBehaviorNumeric extends MicroBehavior {
  var behaviorNumData: Double
  override def data = behaviorNumData
}

trait MicroBehaviorCategorical extends MicroBehavior {
  var behaviorCatData: String
  override def data = behaviorCatData
}

trait MicroBehaviorSet {
  def behaviorVector: List[MicroBehavior]

  def vectorToString= behaviorVector.map(x=>x.valueToCsv).mkString(",")
}

