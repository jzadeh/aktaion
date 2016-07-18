package com.aktaion.parser

/**
  * Generic Interface for Simple Parsing Logic
  */
trait GenericParser {
  def tokenizeData(rawInput: String): Option[ParsedLogEvent]
}


/**
  * Represents a type of log
  */
abstract class ParsedLogEvent