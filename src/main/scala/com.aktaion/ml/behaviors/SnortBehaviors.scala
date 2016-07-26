package com.aktaion.ml.behaviors


import javax.xml.bind.DatatypeConverter
import scala.collection.mutable
import scala.util.matching.Regex
import scala.util.matching.Regex.Match

/**
  * Pull in data from point solutions/IPS/IDS alarms and build a feature vector
  *
  * @param dn domain name
  */
@SerialVersionUID(1144l)
class HttpFeaturesSnort(dn: String) extends Serializable {

}

/**
  * Temporary storage location until we support customers
  * being able to upload individual rule sets
  */
object HttpIdsRulesCache {

  val lines =  """9100	/SiteServer/Admin/knowledge/persmbr/
                 9101	/Site Server/Admin/knowledge/persmbr/
                 9102	/ncommerce3/ExecMacro/orderdspc.d2w
                 9103	/servlet/con"""

}


/**
  * Helper functions for IDS rule conversion
  */
object HttpIdsUtils {

  /**
    * Used to capture different snort data fields
    *
    * @param http_uri
    * @param http_method
    * @param http_header
    */
  case class TokenTuple(http_uri: mutable.ListBuffer[String],
                        http_method: mutable.ListBuffer[String],
                        http_header: mutable.ListBuffer[String])


  /**
    * Convert hex to ascii character i.e HEX = 40 then output = @
    *
    * @param hex
    * @return ascii character
    */
  def hexToAscii(hex: String): Char = {
    val binaryData = DatatypeConverter.parseHexBinary(hex)(0).toInt

    //null char
    val nullChar: Char = 0x0000

    //ignore carriage returns, non printable chars etc..
    // ignore Decimal values from 0 to 31
    if (binaryData >= 32 && binaryData <= 127) {

      //convert the decimal value to its character representation in ascii
      return DatatypeConverter.parseHexBinary(hex)(0).toChar
    } else {
      nullChar
    }

  }

  /**
    * Used in the overloaded version of replaceAllIn for regex matching
    *
    * @param m A regex match
    * @return string that has been converted from Hex to Ascii
    */
  def processHexToken(m: Match): String = {

    val rawMatch = m.group(0).toString
    val stripChar = rawMatch.split('|')(1)

    if (stripChar.size == 2) {
      return hexToAscii(stripChar).toString
    }
    // check we have 3k + 2 total chars in the string
    // which k = 0 ... to some small finite integer
    // for instance for two hex tokens we have 3*1 + 2  total chars OA OB
    // for three hex tokens we have 3*2 + 2 total chars
    //    else if (stripChar.size > 0 && (stripChar.size - 2) % 3 == 0) {
    //
    //      //todo should we use any values that are not single characters
    //      return ""
    //    }

    else {
      return ""
    }

  }

  /**
    * Main function for converting substrings in snort from Ascii to Hex
    *
    * @param input
    * @return
    */
  def substringConvert(input: String): String = {
    //regex for extracting HEX values contained in between | |
    val hexRegex = new Regex( """\|.*?\|""")

    try {
      return hexRegex.replaceAllIn(input, m => processHexToken(m))
    } catch {
      case e: java.lang.StringIndexOutOfBoundsException => System.out.println("!!!!! Problem with Line: " + input)
        return ""
    }
  }


  /**
    * Tokenize a snort rule and output a case class that contains three types of matches
    *
    * @param inLine
    * @return
    */
  def snortTokenizeAndConvert(inLine: String): TokenTuple = {

    val uriList = mutable.ListBuffer[String]()
    val methodList = mutable.ListBuffer[String]()
    val headerList = mutable.ListBuffer[String]()

    if (inLine.contains("http_uri")) {

      //get every element in the array except the last when we split
      val uriSplit = inLine.split("http_uri").reverse.tail.reverse

      for (x <- uriSplit) {
        //todo fix this hack this logic is to simple to capture the case of multiple contents for a single uri
        val uriStr = x.split("content:").last.split(";")(0).dropRight(1).reverse.dropRight(1).reverse
        uriList += substringConvert(uriStr)
      }

    }

    if (inLine.contains(("http_method"))) {

      val methodSplit = inLine.split("http_method").reverse.tail.reverse

      for (x <- methodSplit) {
        val methodStr = x.split("content:").last.split(";")(0).dropRight(1).reverse.dropRight(1).reverse
        methodList += substringConvert(methodStr)
      }

    }

    if (inLine.contains(("http_header"))) {

      val headerSplit = inLine.split("http_header").reverse.tail.reverse

      for (x <- headerSplit) {
        val headerStr = x.split("content:").last.split(";")(0).dropRight(1).reverse.dropRight(1).reverse
        headerList += substringConvert(headerStr)
      }

    }

    return TokenTuple(uriList, methodList, headerList)

  }
}

