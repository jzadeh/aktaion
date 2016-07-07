

package com.aktaion.parser

import com.aktaion.common.SimpleTestTools

class UrlLogicTests extends SimpleTestTools {

  test("Url Conversion Test: Malicious Url"){
    val longUrl = """http://bloghersked.com/mf_llksxjSOQYxu5lzLdigricqvz2vcvt_jzifnsuoYbufEqAvPhOEip2ntpo3tzjz_yxhr_yhwg_zkbHgSqGyz1QBzxPWCvDZppsmufIfatBFvshc1QvAsVZ.html"""
    val parsedUrl: Option[UrlDataVector] = UrlLogic.getUrlFromString(longUrl)

    val output = parsedUrl.get

    output.file shouldBe "/mf_llksxjSOQYxu5lzLdigricqvz2vcvt_jzifnsuoYbufEqAvPhOEip2ntpo3tzjz_yxhr_yhwg_zkbHgSqGyz1QBzxPWCvDZppsmufIfatBFvshc1QvAsVZ.html"
    output.host shouldBe "bloghersked.com"
    output.port shouldBe -1
  }

  test("Url Conversion Test: Short Url"){

    val longUrl = """http://bloghersked.com:903/test.mp3"""
    val parsedUrl: Option[UrlDataVector] = UrlLogic.getUrlFromString(longUrl)

    val output = parsedUrl.get

    output.file shouldBe "/test.mp3"
    output.host shouldBe "bloghersked.com"
    output.port shouldBe 903
  }

}
