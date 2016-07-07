package com.aktaion.ml.behaviors

class UriBehaviors extends MicroBehaviorSet{
  val minFileEntropy = UriMbNumeric("MaxFileEntropy", "Maximum file entropy in the sequence of events")
  val minPathEntropy = UriMbNumeric("MaxPathEntropy", "Maximum path entropy in the sequence of events")
  val maxFileEntropy = UriMbNumeric("MaxFileEntropy", "Maximum file entropy in the sequence of events")
  val maxPathEntropy = UriMbNumeric("MaxPathEntropy", "Maximum path entropy in the sequence of events")

  val behaviorVector = List(minFileEntropy, minPathEntropy, maxFileEntropy, maxPathEntropy)
}

case class UriMbNumeric(behaviorName: String,
                                 behaviorDescription: String,
                                 var behaviorNumData: Double = 0.0) extends MicroBehaviorNumeric

case class UriMbCategorical(behaviorName: String,
                                     behaviorDescription: String,
                                     var behaviorNumData: Double = 0.0) extends MicroBehaviorNumeric
