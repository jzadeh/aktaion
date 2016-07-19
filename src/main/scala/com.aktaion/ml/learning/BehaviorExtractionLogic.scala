
package com.aktaion.ml.learning

import com.aktaion.LogLogic
import com.aktaion.ml.behaviors._
import com.aktaion.parser.{BroHttpLogEvent, GenericProxyLogEvent, ParsedLogEvent}
import com.aktaion.shell.CommandLineUtils


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


class BehaviorExtractionGenericProxyLogic extends SimpleSequentialTransformLogic[GenericProxyLogEvent] with LogLogic {

  def transformSeqOfLogLines(parsedEvents: Seq[GenericProxyLogEvent]): Option[MicroBehaviorSet] = {

    val sourceIpSet: Set[String] = parsedEvents.map { x => x.sourceIp }.toSet
    val destIpSet: Set[String] = parsedEvents.map { x => x.destinationIp }.toSet
    val destDomainSet: Set[String] = parsedEvents.map{ x=>x.urlMetaData.host}.toSet

    val uriSet = parsedEvents.map{ x => x.urlRequested}

    val tsVector: Seq[Long] = parsedEvents.map{ x => x.tsJavaTime.getTime}

    /**
      * pre-processing logic in case we do not have a unique source in the destination
      * in this case we can have two types of computation 'strategies'
      *
      * //todo Implement a group by sourceIP/userKey to apply the scoring logic to each group
      * //todo of source destination pairs
      *
      */
    lazy val isSourceUnique = if (sourceIpSet.size == 1) true else false

    if (isSourceUnique == false) {
      logger.warn("Multiple Sources Detected in File: do group by our  ")
    }

    /** Main sorted data set we want to work with disable and sort offline for faster processing **/
    //todo this does not scale to large files!!
    val sortedData = CommandLineUtils.checkProxySortedLowToHigh(parsedEvents)

    //Exploitation Phase
    val timingIocs = new ExploitationTimingBehaviors

    val urlIocs = new ExploitationURIBehaviors

    urlIocs.uriMaxPathDepth.numData = uriSet.map{ x => x.split("/").size }.max
    urlIocs.uriMinPathDepth.numData = uriSet.map{ x => x.split("/").size }.max

    val masterBehaviorVector: List[MicroBehaviorData] = timingIocs.behaviorVector ++ urlIocs.behaviorVector


    val microBehaviors = new MicroBehaviorSet {
      override def behaviorVector: List[MicroBehaviorData] = masterBehaviorVector
    }

    microBehaviors.printBehaviorVector
    return Some(microBehaviors)


    None
  }
}


class BehaviorExtractionHttpLogic extends SequentialTransformLogic[ParsedLogEvent] {

  def transformSeqOfLogLines[A <: ParsedLogEvent](parsedEvents: Seq[A]): Option[MicroBehaviorSet] = {

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