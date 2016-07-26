
package com.aktaion.ml.learning

import com.aktaion.LogLogic
import com.aktaion.ml.behaviors._
import com.aktaion.parser.{BroHttpLogEvent, GenericProxyLogEvent, ParsedLogEvent}
import com.aktaion.shell.CommandLineUtils

import scala.collection.mutable.ArrayBuffer


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

  def transformSeqOfLogLines(parsedEvents: Seq[GenericProxyLogEvent], windowSize: Int): Option[Seq[WindowOfBehaviors]] = {

    if (windowSize == 0 || windowSize > parsedEvents.size) return None

    val sourceIpSet: Set[String] = parsedEvents.map { x => x.sourceIp }.toSet

    //    val sourceIpSet: Set[String] = parsedEvents.map { x => x.sourceIp }.toSet
    //    val destIpSet: Set[String] = parsedEvents.map { x => x.destinationIp }.toSet
    //    val destDomainSet: Set[String] = parsedEvents.map { x => x.urlMetaData.host }.toSet
    //    val uriSet = parsedEvents.map { x => x.urlRequested }
    //    val tsVector: Seq[Long] = parsedEvents.map { x => x.tsJavaTime.getTime }

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
      logger.warn("Multiple Sources Detected in File: group by source  ")
    }

    /** Main sorted data set we want to work with disable and sort offline for faster processing **/
    //todo this does not scale to large files!!
    val sortedData = CommandLineUtils.checkProxySortedLowToHigh(parsedEvents)

    //gives a window to score the behaviors over
    val dataBrokenIntoWindows: Iterator[Seq[GenericProxyLogEvent]] = parsedEvents.sliding(windowSize)

    var microBehaviorsDetectedInEachWindow = ArrayBuffer[WindowOfBehaviors]()

    for ((currentWindow, index) <- dataBrokenIntoWindows.zipWithIndex) {

      //Compute some basic stats
      val genericIocs = new GenericBehaviors

      //dont use window size as a feature
      // genericIocs.windowSize.numData = windowSize

      //Exploitation Phase
      /**
        * Time specific behaviors
        */
      val timingIocs = new ExploitationTimingBehaviors

      val tsVector: Seq[Long] = currentWindow.map { x => x.tsJavaTime.getTime }
      val differenceOfTimeStamps = tsVector.slice(0, windowSize) zip tsVector.slice(1, windowSize) map (x => x._2 - x._1)

      val mins = differenceOfTimeStamps.sorted

      timingIocs.minTimeIntervalA.numData = mins.head
      timingIocs.minTimeIntervalB.numData = mins.tail.head
      timingIocs.minTimeIntervalC.numData = mins.tail.tail.head
      timingIocs.minTimeIntervalD.numData = mins.tail.tail.tail.head

      val maxes = mins.reverse

      timingIocs.maxTimeIntervalA.numData = maxes.head
      timingIocs.maxTimeIntervalB.numData = maxes.tail.head
      timingIocs.maxTimeIntervalC.numData = maxes.tail.tail.head
      timingIocs.maxTimeIntervalD.numData = maxes.tail.tail.tail.head

      timingIocs.intervalLength.numData = maxes.head - mins.head

      /**
        * URI specific behaviors
        */
      val urlIocs = new ExploitationUriBehaviors

      val uriSet = currentWindow.map { x => x.urlRequested }
      urlIocs.uriMaxPathDepth.numData = uriSet.map { x => x.split("/").size }.max
      urlIocs.uriMinPathDepth.numData = uriSet.map { x => x.split("/").size }.max

      /**
        * Boiler plate to collect all the individual sets of information we computed above
        */
      val microBehaviorsDetected = new MicroBehaviorSet {
        override def behaviorVector: List[MicroBehaviorData] = timingIocs.behaviorVector ++
          urlIocs.behaviorVector ++ genericIocs.behaviorVector
      }

      microBehaviorsDetectedInEachWindow += WindowOfBehaviors(microBehaviorsDetected, index)
    }

    microBehaviorsDetectedInEachWindow.foreach(x => x.microBehaviorSet.printBehaviorVector)

    if (microBehaviorsDetectedInEachWindow.size > 0) {
      return Some(microBehaviorsDetectedInEachWindow.toSeq)
    } else {
      return None
    }
  }
}


class BehaviorExtractionHttpLogic extends SequentialTransformLogic[ParsedLogEvent] {

  def transformSeqOfLogLines[A <: ParsedLogEvent](parsedEvents: Seq[A]): Option[Seq[WindowOfBehaviors]] = {

    //step 1: extract a single entity
    //todo if we have multiple IP's break the computation down into group by (source/destination pairs)


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