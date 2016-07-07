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