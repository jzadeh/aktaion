
package com.aktaion.ml.learning

import com.aktaion.ml.behaviors.MicroBehaviorSet
import com.aktaion.parser.ParsedLogEvent

trait GenericTransformLogic {
  def transformLogLine(parsedEvent: ParsedLogEvent): Option[Seq[WindowOfBehaviors]]
}

//todo ask john/sathesh
trait SequentialTransformLogic[A <: ParsedLogEvent] {
  def transformSeqOfLogLines[A <: ParsedLogEvent](parsedEvents: Seq[A]): Option[Seq[WindowOfBehaviors]]
}


trait SimpleSequentialTransformLogic[ParsedLogEvent] {
  def transformSeqOfLogLines(parsedEvents: Seq[ParsedLogEvent], windowSize: Int): Option[Seq[WindowOfBehaviors]]
}

case class WindowOfBehaviors(microBehaviorSet: MicroBehaviorSet, windowNumber: Int)
