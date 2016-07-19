package com.aktaion.ml.behaviors

class GenericBehaviors extends MicroBehaviorSet {

  val logLineCount = GenericMbNumeric("TotalLogCount", "Number of log lines analyzed")

  val behaviorVector = List(logLineCount)

}


case class GenericMbNumeric(behaviorName: String,
                                 behaviorDescription: String,
                                 var behaviorNumData: Double = 0.0) extends MicroBehaviorNumeric
