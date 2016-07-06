/**
  * Copyright 2016 - Caspida Inc., All rights reserved.
  * This is Caspida proprietary and confidential material and its use
  * is subject to license terms.
  * Created on 7/6/16.
  *
  * @author joez
  */

package com.aktaion.ml.learning

import com.aktaion.ml.behaviors.MicroBehaviorSet
import com.aktaion.parser.ParsedLogEvent

trait GenericTransformLogic {

  def transformLogLine(parsedEvent: ParsedLogEvent): Option[MicroBehaviorSet]

}
