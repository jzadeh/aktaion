
package com.aktaion.ml.learning

import com.aktaion.ml.behaviors.MicroBehaviorSet
import com.aktaion.parser.ParsedLogEvent


//class BehaviorExtractionProxyLogic extends SequentialTransformLogic[GenericProxyLogEvent] {
//
//  def transformSeqOfLogLines(parsedEvents: Seq[GenericProxyLogEvent]): Option[MicroBehaviorSet] = {
//
//    //step 1: extract a single entity
//    //todo if we have mutiple IP's break the computation down into group by (source/destination pairs)
//
//
//    for (logLine <- parsedEvents) {
//
//
//
//
//
//
//    }
//
//
//    //step 2:  compute individual microbehaviors per source
//
//    //step 3: score the feature vector with Mllib/weka
//
//
//
//    None
//
//  }
//
//}
//
//
//
//class BehaviorExtractionBroHttpLogic extends SequentialTransformLogic[BroHttpLogEvent] {
//
//  def transformSeqOfLogLines(parsedEvents: Seq[BroHttpLogEvent]): Option[MicroBehaviorSet] = {
//
//    None
//  }




class BehaviorExtractionHttpLogic extends SequentialTransformLogic[ParsedLogEvent] {

    def transformSeqOfLogLines[A<:ParsedLogEvent](parsedEvents: Seq[A]): Option[MicroBehaviorSet] = {

      //step 1: extract a single entity
      //todo if we have mutiple IP's break the computation down into group by (source/destination pairs)

      for (logLine <- parsedEvents) {


      }

      //step 2:  compute individual microbehaviors per source

      //step 3: score the feature vector with Mllib/weka



      None

    }

}