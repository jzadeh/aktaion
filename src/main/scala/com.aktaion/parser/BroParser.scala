
package com.aktaion.parser

import java.sql.Timestamp
import java.time.{Instant, LocalDateTime, ZoneId}

/**
  * Used for Bro HTTP log events
  */
object BroHttpParser extends GenericParser {

  def tokenizeData(rawData: String): Option[BroHttpLogEvent] = {
    //if we see a comment stop parsing
    if (rawData.startsWith("#")) return None

    val rd: Array[String] = rawData.split("\t")

    val tsString = rd(0)
    val epochTime = tsString.toDouble

    //have to round off the seconds here we just drop the digits as a hack
    val ts = rd(0).toDouble.toLong

    val tsJavaTime = new Timestamp(ts)

    val uid = rd(1)
    val idOrigHost = rd(2)
    val idOrigPort = rd(3).toInt
    val idRespHost = rd(4)
    val idRespPort = rd(5)


    return Some(BroHttpLogEvent(
      tsJavaTime,
      epochTime,
      uid,
      idOrigHost,
      idOrigPort,
      rd(4),
      rd(5).toInt,
      rd(6).toInt,
      rd(7),
      rd(8),
      rd(9),
      rd(10),
      rd(11),
      rd(12),
      rd(13),
      rd(14).toInt,
      rd(15),
      rd(16),
      rd(17),
      rd(18),
      rd(19),
      rd(20),
      rd(21),
      rd(22),
      rd(23),
      rd(24),
      rd(25),
      rd(26))
    )

  }

}


