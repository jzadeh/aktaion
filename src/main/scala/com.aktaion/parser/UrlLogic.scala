/**
  * Copyright 2016 - Caspida Inc., All rights reserved.
  * This is Caspida proprietary and confidential material and its use
  * is subject to license terms.
  * Created on 7/6/16.
  *
  * @author joez
  */

package com.aktaion.parser

import java.net._

object UrlLogic {


  def getUrlFromString(inputString: String): Option[UrlDataVector] = {

    if (inputString.size < 5) {println("URL is too small: " + inputString); None}
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
