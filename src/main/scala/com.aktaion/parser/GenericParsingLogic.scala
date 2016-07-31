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
abstract class ParsedLogEvent {
 // def normalizeLogType[T<:ParsedLogEvent](inputLog: T): Option[NormalizedLogEvent]
}

//
//abstract class NormalizedLogEvent {
//}