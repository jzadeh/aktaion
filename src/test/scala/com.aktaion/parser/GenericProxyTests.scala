
package com.aktaion.parser

import com.aktaion.common.SimpleTestTools
import com.aktaion.ml.learning.BehaviorExtractionLogic
import com.aktaion.shell.CommandLineUtils

class GenericProxyTests extends SimpleTestTools {

  test("Generic Proxy Parser Single Line Test 1") {
    val rawInputString = """[22/May/2014:14:46:48 -0700] "Nico Rosberg" 172.16.223.136 208.43.216.144 1500 200 TCP_HIT "GET http://www.christianforums.com/t7788258/ HTTP/1.1" "Internet Services" "low risk " "text/html; charset=ISO-8859-1" 505 1241 "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)" "http://www.google.com/url?sa=t&rct=j&q=&esrc=s&frm=1&source=web&cd=1&ved=0CCYQFjAA&url=http%3A%2F%2Fwww.christianforums.com%2Ft7788258%2F&ei=QHB-U-GqHsGAqga3oYHQCA&usg=AFQjCNFVvOulMtifbKPA8_rxF4cAOkhzyQ&bvm=bv.67229260,d.b2k" "-" "0" "" "-" """
    val parsedData: Option[GenericProxyLogEvent] = GenericProxyParser.tokenizeData(rawInputString)
    val output: GenericProxyLogEvent = parsedData.get

    output.urlRequested shouldBe "http://www.christianforums.com/t7788258/"

    val urlData = output.urlMetaData

    urlData.port shouldBe -1 //default port value
    urlData.host shouldBe "www.christianforums.com"
    urlData.file shouldBe "/t7788258/"
    urlData.file shouldBe "/t7788258/"


    val normalizedData = ParsingNormalizationLogic.normalizeProxyLog(parsedData).get

    normalizedData.uri shouldBe "http://www.christianforums.com/t7788258/"
    normalizedData.urlMetaData.file shouldBe "/t7788258/"

  }


  test("Single Line") {
    val rawInputString = """[12/Dec/2014:13:43:09 -0800] "Nico Rosberg" 192.168.204.137 128.199.52.211 1500 200 TCP_HIT "GET http://yquesrerman.ga/AwoVG1ADAw4OUhlVDlRTBQoHRUJTXVYOUVYaAwtGXFRVVFxXVwBOVRtA HTTP/1.1" "Internet Services" "low risk" "application/octet-stream" 412 752 "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729)" "http://yquesrerman.ga/AkNVHkgGT0Q.html" "-" "0" "" "-""""
    val parsedData: Option[GenericProxyLogEvent] = GenericProxyParser.tokenizeData(rawInputString)
    val output = parsedData.get
    output.domainClass shouldBe "Internet Services"
  }


  ignore("Generic Proxy File Normalization Logic") {

    val file: String = getFileStringFromResourcePath("/parser/genericproxy/2014-05-22-Fiesta-EK-traffic-03.webgateway")
    val lines: Array[String] = CommandLineUtils.getFileFromFileSystemPath(file)
    val parsedData: Array[Option[GenericProxyLogEvent]] = lines.map { x => GenericProxyParser.tokenizeData(x) }
    val normData: Seq[Option[NormalizedLogEvent]] =
      parsedData.map { x => ParsingNormalizationLogic.normalizeProxyLog(x) }.toSeq

    val dropErros: Seq[NormalizedLogEvent] = normData.flatMap(x => x)
    for (x <- lines) {
      println(x)
    }

    BehaviorExtractionLogic.transformSeqOfLogLines(dropErros, 5)

  }



  ignore("Generic Proxy File Normalization Logic 2") {

    val file: String = getFileStringFromResourcePath("/parser/genericproxy/2014-06-01-exe.exe-sandbox-analysis.webgateway")
    val lines: Array[String] = CommandLineUtils.getFileFromFileSystemPath(file)
    val parsedData: Array[Option[GenericProxyLogEvent]] = lines.map { x => GenericProxyParser.tokenizeData(x) }
    val normData: Seq[Option[NormalizedLogEvent]] =
      parsedData.map { x => ParsingNormalizationLogic.normalizeProxyLog(x) }.toSeq

    val dropErros: Seq[NormalizedLogEvent] = normData.flatMap(x => x)
    for (x <- lines) {
      println(x)
    }

    BehaviorExtractionLogic.transformSeqOfLogLines(dropErros, 5)

  }

  ignore("Generic Proxy File 2") {

    val file: String = getFileStringFromResourcePath("/parser/genericproxy/2014-03-09-Fiesta-EK-traffic-failed-attempt.webgateway")
    val lines: Array[String] = CommandLineUtils.getFileFromFileSystemPath(file)

    // val parsedData =  lines.flatMap{ x=> GenericProxyParser.tokenizeData(x)}.toSeq
    for ((x, i) <- lines.zipWithIndex) {
      println("Line " + i)
      GenericProxyParser.tokenizeData(x)
    }
    //  BehaviorExtractionLogic.transformSeqOfLogLines(parsedData,5)
  }

  ignore("Single Line 2") {
    val rawInputString = """[19/Feb/2014:11:02:43 -0800] "Nico Rosberg" 172.16.2.159 82.223.150.54 1500 302 TCP_HIT "POST http://interysoft.com/ HTTP/1.1" "Internet Services" "low risk " "text/html" 390 492 "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)" "" "-" "0" "" "-" """
    val parsedData: Option[GenericProxyLogEvent] = GenericProxyParser.tokenizeData(rawInputString)
    val output = parsedData.get
    output.domainClass shouldBe "Internet Services"

    // println(parsedData)
  }

}
