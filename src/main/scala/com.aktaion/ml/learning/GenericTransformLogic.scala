
package com.aktaion.ml.learning

import com.aktaion.ml.behaviors.MicroBehaviorSet
import com.aktaion.parser.ParsedLogEvent

trait GenericTransformLogic {
  def transformLogLine(parsedEvent: ParsedLogEvent): Option[MicroBehaviorSet]
}

//todo ask john/sathesh
trait SequentialTransformLogic[A <: ParsedLogEvent] {
  def transformSeqOfLogLines[A <: ParsedLogEvent](parsedEvents: Seq[A]): Option[MicroBehaviorSet]
}


trait SimpleSequentialTransformLogic[ParsedLogEvent] {
  def transformSeqOfLogLines(parsedEvents: Seq[ParsedLogEvent]): Option[MicroBehaviorSet]
}