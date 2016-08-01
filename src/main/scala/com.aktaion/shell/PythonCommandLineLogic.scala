package com.aktaion.shell

import sys.process._

import com.aktaion.ml.weka.randomforest.RandomForestLogic.IocsExtracted

object PythonCommandLineLogic {


  /**
    * Command line execution of active defense script against that is meant to POC
    * the power of tying a machine learning analytic to an automation workflow in
    * this case we have chose to pass IOC's to a GPO generation script
    * that can provide automated protection for an entire user population once
    * an initial exploit pattern is detected
    *
    * @param iocData the key data points we want to generate a GPO against
    * @return exit code status
    */
  def passIocsToActiveDefenseScript(iocsData: Option[IocsExtracted], numSubDirs: Int): Boolean = {

    val iocs = iocsData.getOrElse(return false)

    val pathStr = CommandLineUtils.findFilePathRelativeToJar
    val splitStr = pathStr.split("/").take(numSubDirs).mkString("/")
    val pythonScriptPath: String = splitStr + "/python/AktaionActiveDefenseScript.py"

    val singleFile: String = iocs.suspiciousFileNames.head

    val pythonString: String = "python " + pythonScriptPath + " -n " + singleFile
    println(pythonString)
    val exitCode = pythonString.!
    val result = if (exitCode == 0) true else false
    return result
  }

}
