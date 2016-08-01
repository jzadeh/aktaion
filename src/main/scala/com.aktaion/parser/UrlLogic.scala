package com.aktaion.parser

import java.net._

object UrlLogic {

  /**
    *
    * @param inputString
    * @return
    */
  def getUrlFromString(inputString: String): Option[UrlDataVector] = {

    if (inputString.size < 5) {println("URL is too small: " + inputString); return None}
    val url = new URL(inputString)

    val host: String =  url.getHost
    val file: String = url.getFile
    val path: String = url.getPath
    val port: Int = url.getPort
    val query: String = url.getQuery

    return Some(UrlDataVector(host,file,path,port,query))
  }

}

case class UrlDataVector(host: String, file:String, path: String, port: Int, query: String)
