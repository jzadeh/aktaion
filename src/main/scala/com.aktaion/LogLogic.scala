package com.aktaion

import org.slf4j.{Logger, LoggerFactory}


trait LogLogic {
  lazy val logger = LoggerFactory.getLogger(getClass)
  implicit def log2Logger(anything: LogLogic): Logger = anything.logger
}