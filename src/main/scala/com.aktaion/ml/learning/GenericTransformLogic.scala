
package com.aktaion.ml.learning

import com.aktaion.ml.behaviors.MicroBehaviorSet
import com.aktaion.parser.ParsedLogEvent

trait GenericTransformLogic {
  def transformLogLine(parsedEvent: ParsedLogEvent): Option[MicroBehaviorSet]
}

trait SequentialTransformLogic[A <: ParsedLogEvent] {
  def transformSeqOfLogLines[A <: ParsedLogEvent](parsedEvents: Seq[A]): Option[MicroBehaviorSet]
}

