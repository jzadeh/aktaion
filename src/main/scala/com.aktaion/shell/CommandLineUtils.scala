/**
  * Copyright 2016 - Caspida Inc., All rights reserved.
  * This is Caspida proprietary and confidential material and its use
  * is subject to license terms.
  * Created on 6/20/16.
  *
  * @author joez
  */

package com.aktaion.shell

import sys.process._

class CommandLineUtils {

  val stdout = new StringBuilder
  val stderr = new StringBuilder


  /**
    * Command line execution of bro against a pcap
    *
    * @param inputFile pcap file
    * @return exit code status
    */
  def generateBroFiles(inputFile: String): Boolean = {

    val broString = "bro -r " + inputFile
    val exitCode = broString.!
    val result = if (exitCode == 0) true else false

    return result

  }

  //  val exitCode = status.!
  ProcessLogger(stdout append _, stderr append _)
  //  println(status)
  println("stdout: " + stdout)
  println("stderr: " + stderr)


}

