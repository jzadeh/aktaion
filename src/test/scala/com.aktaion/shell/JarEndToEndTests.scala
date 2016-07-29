package com.aktaion.shell

import com.aktaion.common.SimpleTestTools

class JarEndToEndTests extends SimpleTestTools {

  test("Debug The Command Line Workflow") {
    val localPath: String = CommandLineUtils.tryToFindPathToDataInSourceCode(4)
    val demoFileName: String = "test.pcap"
    val testFile: String = localPath + demoFileName
    //   System.out.println(testFile)
    val proxyDataPath: String = "proxyData/exploitData/"
    CommandLineUtils.executeBroSimpleDebugLogic(testFile)
    //guess where the weka data is
    val dataPath: String = CommandLineUtils.tryToFindPathToDataInSourceCode(4)
    val trainData: String = dataPath + "wekaData/synthetic_train.arff"
    CommandLineUtils.crossValidationWekaRf(10.0d, trainData)

    //    System.out.println(trainData)
    val trainDirectory: String = dataPath + "proxyData/exploitData/"
    //    System.out.println(trainDirectory)

    CommandLineUtils.extractGenericProxyDataFromDirectory(trainDirectory, false, "/Users/User/Aktaion/test.output", "webgateway")
  }


}

