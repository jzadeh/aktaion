package com.aktaion

import org.slf4j.{Logger, LoggerFactory}

/**
  * Mixin this trait for logging output from a class see for example [[com.aktaion.ml.learning.BehaviorExtractionLogic]]
  *
  * This is just boiler plate for adding logging facility to the project.
  */
trait DebugLoggingLogic {
  lazy val logger = LoggerFactory.getLogger(getClass)
  implicit def log2Logger(anything: DebugLoggingLogic): Logger = anything.logger
}