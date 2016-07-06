/**
  * Copyright 2016 - Caspida Inc., All rights reserved.
  * This is Caspida proprietary and confidential material and its use
  * is subject to license terms.
  * Created on 7/5/16.
  *
  * @author joez
  */

package com.aktaion.parser

/**
  * Generic Interface for Simple Parsing Logic
  */
trait GenericParser {
  def tokenizeData(rawInput: String): Option[ParsedLogEvent]
}


/**
  * Represents a type of log
  * @param logType name of the log type
  */
abstract class ParsedLogEvent(logType: String)