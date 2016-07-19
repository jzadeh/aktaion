
package com.aktaion.ml.learning

import com.aktaion.LogLogic
import com.aktaion.ml.behaviors.MicroBehaviorSet
import com.aktaion.parser.{BroHttpLogEvent, GenericProxyLogEvent, ParsedLogEvent}


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


class BehaviorExtractionGenericProxyLogic extends SimpleSequentialTransformLogic[BroHttpLogEvent] with LogLogic{


  def transformSeqOfLogLines(parsedEvents: Seq[BroHttpLogEvent]): Option[MicroBehaviorSet] = {

    val sourceSet: Set[String] = parsedEvents.map{ x=>x.id_orig_host}.toSet
    val destSet: Set[String] = parsedEvents.map{ x=>x.id_orig_host}.toSet


    /**
      * pre-processing logic in case we do not have a unique source in the destination
      * in this case we can have two types of computation 'strategies'
      *
      * //todo Implement a group by sourceIP/userKey to apply the scoring logic to each group
      * //todo of source destiation pairs
      *
      *
      */
    lazy val isSourceUnique = if(sourceSet.size ==1) true else false

    if (isSourceUnique == false){
      logger.warn("Multiple Sources Detected in File: do group by our  ")
    }

    //todo check data is sorted




    None
  }
}



class BehaviorExtractionHttpLogic extends SequentialTransformLogic[ParsedLogEvent] {

    def transformSeqOfLogLines[A<:ParsedLogEvent](parsedEvents: Seq[A]): Option[MicroBehaviorSet] = {

      //step 1: extract a single entity
      //todo if we have mutiple IP's break the computation down into group by (source/destination pairs)





      parsedEvents.map { x =>

        x match {
          case b: BroHttpLogEvent =>
          case p: GenericProxyLogEvent =>

        }

      }

      //step 2:  compute individual microbehaviors per source
      //step 3: score the feature vector with Mllib/weka

      None

    }

}