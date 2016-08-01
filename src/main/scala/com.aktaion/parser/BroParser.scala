
package com.aktaion.parser

import java.sql.Timestamp
import java.time.{Instant, LocalDateTime, ZoneId}

import scala.util.Try

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
    val idOrigPort = rd(3).toInt //source port
    val idRespHost = rd(4)
    val idRespPort = rd(5).toInt
    val transDepth = rd(6).toInt //port on the webserver (usually 80 or 443)
    val method = rd(7)
    val host = rd(8)
    val uri = rd(9)
    val referrer = rd(10)
    val userAgent = rd(11)
    val requestBodyLen = rd(12)
    val responseBodyLen = rd(13)
    val statusCode =  Try(rd(14).toInt).getOrElse(-1)


    val fullUrl = if (idRespPort == 443) {
      "https://" + host + uri
    }
    else {
      "http://" + host + uri
    }

    //get a vector of meta data about a class if we have a parsing
    //issue return the null vector of meta data
    val urlData = UrlLogic.
      getUrlFromString(fullUrl).getOrElse(UrlDataVector("", "", "", 0, ""))

    return Some(BroHttpLogEvent(
      epochTime,
      uid,
      idOrigHost,
      idOrigPort,
      idRespHost,
      idRespPort,
      transDepth,
      method,
      host,
      uri,
      referrer,
      userAgent,
      requestBodyLen,
      rd(13),
      statusCode,
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
      rd(26),
      urlData,
      tsJavaTime)
    )

  }

}


