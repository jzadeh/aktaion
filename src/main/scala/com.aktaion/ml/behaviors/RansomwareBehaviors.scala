package com.aktaion.ml.behaviors

class RansomwareBehaviors extends MicroBehaviorSet {

  val windowSize = MicroBehaviorData("WindowSize", "Number of log lines analyzed in window")

  val behaviorVector = List(windowSize)

}
