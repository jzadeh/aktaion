
package com.aktaion.shell

import sys.process._

object BroCommandLineLogic {

  /**
    * Command line execution of bro against a pcap
    *
    * @param inputFile pcap file
    * @return exit code status
    */
  def generateBroFiles(inputFile: String): Boolean = {
    //val broString = "bro -?"
    val broString = "bro -r " + inputFile
    val exitCode = broString.!
    val result = if (exitCode == 0) true else false
    return result
  }
}
