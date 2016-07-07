package com.aktaion.ml.learning

import com.aktaion.ml.behaviors.MicroBehaviorSet
import com.aktaion.parser.{GenericProxyEvent, ParsedLogEvent}

class BehaviorExtractionProxyLogic extends SequentialTransformLogic[GenericProxyEvent] {

  def transformSeqOfLogLines(parsedEvents: Seq[GenericProxyEvent]): Option[MicroBehaviorSet] = {

    None

  }

}



class BehaviorExtractionBroHttpLogic extends SequentialTransformLogic[GenericProxyEvent] {

  def transformSeqOfLogLines(parsedEvents: Seq[Bro]): Option[MicroBehaviorSet] = {

    None

  }

}
