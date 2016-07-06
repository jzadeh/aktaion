/**
  * Copyright 2016 - Caspida Inc., All rights reserved.
  * This is Caspida proprietary and confidential material and its use
  * is subject to license terms.
  * Created on 7/2/16.
  *
  * @author joez
  */

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

