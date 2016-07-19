package com.aktaion.ml.behaviors

class GenericBehaviors extends MicroBehaviorSet {
  val logLineCount = MicroBehaviorData("TotalLogCount", "Number of log lines analyzed")
  val behaviorVector = List(logLineCount)
}


