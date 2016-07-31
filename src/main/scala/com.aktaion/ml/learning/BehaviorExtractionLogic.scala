
package com.aktaion.ml.learning

import com.aktaion.DebugLoggingLogic
import com.aktaion.ml.algorithms.EntropyUtils
import com.aktaion.ml.behaviors._
import com.aktaion.ml.weka.randomforest.ClassLabel
import com.aktaion.ml.weka.randomforest.ClassLabel.ClassLabel
import com.aktaion.parser.{GenericProxyLogEvent, NormalizedLogEvent}
import com.aktaion.shell.CommandLineUtils

import scala.collection.mutable.ArrayBuffer

object BehaviorExtractionLogic extends DebugLoggingLogic {

  def transformSeqOfLogLines(parsedEvents: Seq[NormalizedLogEvent], windowSize: Int): Option[Seq[List[MicroBehaviorData]]] = {

    if (windowSize == 0 || windowSize > parsedEvents.size) return None

    val sourceIpSet: Set[String] = parsedEvents.map { x => x.sourceIp }.toSet

    /** Step 1:
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
    val sortedData = CommandLineUtils.checkTimeSortedLowToHigh(parsedEvents)

    //gives a window to score the behaviors over
    val dataBrokenIntoWindows: Iterator[Seq[NormalizedLogEvent]] = parsedEvents.sliding(windowSize)
    var microBehaviorsDetectedInEachWindow = ArrayBuffer[List[MicroBehaviorData]]()


    /**
      * Step 2:  Main computation loop for extracting micro behaviors
      * on a per window basis
      */
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
      val differenceOfTimeStamps =
        tsVector.slice(0, windowSize) zip tsVector.slice(1, windowSize) map (x => x._2 - x._1)

      val mins = differenceOfTimeStamps.sorted
      val maxes = mins.reverse

      timingIocs.minTimeIntervalA.numData = mins.head
      timingIocs.minTimeIntervalB.numData = mins.tail.head
      timingIocs.minTimeIntervalC.numData = mins.tail.tail.head
      timingIocs.minTimeIntervalD.numData = mins.tail.tail.tail.head

      timingIocs.maxTimeIntervalA.numData = maxes.head
      timingIocs.maxTimeIntervalB.numData = maxes.tail.head
      timingIocs.maxTimeIntervalC.numData = maxes.tail.tail.head
      timingIocs.maxTimeIntervalD.numData = maxes.tail.tail.tail.head

      timingIocs.intervalLength.numData = maxes.head - mins.head

      /**
        * URI specific behaviors
        */
      val urlIocs = new ExploitationUriBehaviors
      val uriSet: Seq[String] = currentWindow.map { x => x.uri }

      urlIocs.uriMaxPathDepth.numData = uriSet.map { x => x.split("/").size }.max
      urlIocs.uriMinPathDepth.numData = uriSet.map { x => x.split("/").size }.min
      urlIocs.uriDistinct.numData = uriSet.distinct.length
      urlIocs.uriMaxEntropy.numData = uriSet.map { x => EntropyUtils.charDistribution(x) }.max
      urlIocs.uriMinEntropy.numData = uriSet.map { x => EntropyUtils.charDistribution(x) }.min
      urlIocs.uriMaxLength.numData = uriSet.map { x => x.size }.max
      urlIocs.uriMinLength.numData = uriSet.map { x => x.size }.min


      val behaviors: List[MicroBehaviorData] =
          timingIocs.behaviorVector ++ urlIocs.behaviorVector ++ genericIocs.behaviorVector

      val microBehaviorsDetected: MicroBehaviorWindow = MicroBehaviorWindow(behaviors, windowSize)

      microBehaviorsDetected.printBehaviorVector
      microBehaviorsDetectedInEachWindow += microBehaviorsDetected.behaviorVector
    }

    /**
      * Pass the set of computed microbeahaviors to a
      */
    if (microBehaviorsDetectedInEachWindow.size > 0) {
      val finalOutput= microBehaviorsDetectedInEachWindow.toSeq
      return Some(finalOutput)
    } else {
      return None
    }
  }


  /**
    * Follows the weka .arff format to build a simple string
    * for scoring the behaviors over a set of observations for a single file
    * https://weka.wikispaces.com/ARFF+(stable+version)
    *
    * @param behaviorOverWindows
    * @param fileName
    * @param classLabel
    * @return
    */
  def convertBehaviorVectorToWeka(behaviorOverWindows: Seq[List[MicroBehaviorData]],
                                  fileName: String,
                                  classLabel: ClassLabel): String = {
    val title = "@relation microbehaviors\n\n"
    var attributes = ""
    var csvRows = "@data\n"
    for ((window, ind) <- behaviorOverWindows.zipWithIndex) {
      for ((data, index) <- window.zipWithIndex) {
        //ONLY DO THE COMPUTATION ONCE
        if (ind == 0) {
          attributes = attributes + "@attribute " + "," + data.behaviorName.toLowerCase + " real\n"
        }
        if (index < window.size - 1) {
          csvRows = csvRows + data.numData.toString + ","
        } else {
          csvRows = csvRows + data.numData.toString +  "," + classLabel.toString.toLowerCase + "\n"
        }
      }
    }

    attributes = attributes + "\n" + "@attribute classlabel {" +
      ClassLabel.BENIGN.toString.toLowerCase + "," +
      ClassLabel.EXPLOIT.toString.toLowerCase + "}" + "\n"

    val fullText = title + attributes + "\n" + csvRows
    return fullText
  }

}
