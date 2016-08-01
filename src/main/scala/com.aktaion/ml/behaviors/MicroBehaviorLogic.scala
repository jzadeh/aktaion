package com.aktaion.ml.behaviors

import com.aktaion.DebugLoggingLogic

/**
  * Simple abstraction for numeric data associated to
  * some statistic we are computing about a behavior for example
  * the number of unique MIME types served by a single domain
  */
trait MicroBehaviorNumericLike {
  //just a nicer way to reference the generic value we rename data below
  var numData: Double
}

/**
  * Abstraction for non numerical data associated to a behavior
  *
  */
trait MicroBehaviorCategoricalLike {
  //just a nicer way to reference the generic value we rename data below
  var catData: String
}

/**
  * Micro Behavior: Main abstraction for individual unit of behavior
  * that we want to model in the upstream workflow
  *
  */
case class MicroBehaviorData(behaviorName: String,
                             behaviorDescription: String,
                             var numData: Double = 0.0,
                             var catData: String = "")
  extends MicroBehaviorNumericLike with MicroBehaviorCategoricalLike {
  def valueToCsv = behaviorName + "," + numData.toString + "," + catData
}


/**
  * Represents a single set of behaviors for some input data
  * this can be a single log line or multiple log lines
  * depending on how we implement the upstream logic
  */
trait MicroBehaviorSet extends DebugLoggingLogic {
  def behaviorVector: List[MicroBehaviorData]
  def vectorToString = behaviorVector.map(x => x.valueToCsv).mkString(",")
  def printBehaviorVector = logger.debug(vectorToString)
}

/**
  * In the current implementation we score a window of time
  * and derive a single set of values for each behavior we are modeling
  *
  * @param behaviorVector the computed values for each behavior in that window
  * @param windowSize the window size (number of log lines) the computation took place over
  */
case class MicroBehaviorWindow(behaviorVector: List[MicroBehaviorData], windowSize: Int) extends MicroBehaviorSet
