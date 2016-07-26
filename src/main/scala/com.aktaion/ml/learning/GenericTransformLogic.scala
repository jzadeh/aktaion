
package com.aktaion.ml.learning

import com.aktaion.ml.behaviors.{MicroBehaviorData, MicroBehaviorSet}
import com.aktaion.parser.ParsedLogEvent

trait GenericTransformLogic {
  def transformLogLine(parsedEvent: ParsedLogEvent): Option[Seq[List[MicroBehaviorData]]]
}

//todo ask john/sathesh
trait SequentialTransformLogic[A <: ParsedLogEvent] {
  def transformSeqOfLogLines[A <: ParsedLogEvent](parsedEvents: Seq[A]): Option[Seq[List[MicroBehaviorData]]]
}


trait SimpleSequentialTransformLogic[ParsedLogEvent] {
  def transformSeqOfLogLines(parsedEvents: Seq[ParsedLogEvent], windowSize: Int): Option[Seq[List[MicroBehaviorData]]]
}

case class WindowOfBehaviors(microBehaviorSet: MicroBehaviorSet, windowNumber: Int)
