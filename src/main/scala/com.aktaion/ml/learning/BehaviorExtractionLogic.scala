
package com.aktaion.ml.learning

import com.aktaion.ml.behaviors.MicroBehaviorSet
import com.aktaion.parser.{BroHttpLogEvent, GenericProxyLogEvent, ParsedLogEvent}

class BehaviorExtractionProxyLogic extends SequentialTransformLogic[GenericProxyLogEvent] {

  def transformSeqOfLogLines(parsedEvents: Seq[GenericProxyLogEvent]): Option[MicroBehaviorSet] = {


    //step 1: extract a single entity
    //todo if we have mutiple IP's reak the computation down into group by IP's


    //step 2:  compute individual microbehaviors per source

    //step 3: score the feaure vector with Mllib/weka



    None

  }

}



class BehaviorExtractionBroHttpLogic extends SequentialTransformLogic[BroHttpLogEvent] {

  def transformSeqOfLogLines(parsedEvents: Seq[BroHttpLogEvent]): Option[MicroBehaviorSet] = {

    None
  }

}