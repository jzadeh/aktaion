/**
  * Copyright 2016 - Caspida Inc., All rights reserved.
  * This is Caspida proprietary and confidential material and its use
  * is subject to license terms.
  * Created on 7/6/16.
  *
  * @author joez
  */

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
