package com.aktaion.parser

import java.sql.Timestamp


//    #fields	ts	uid	id.orig_h	id.orig_p	id.resp_h	id.resp_p	trans_depth	method	host	uri	referrer	user_agent	request_body_len	response_body_len	status_code	status_msg	info_code	info_msg	filename	tags	username	password	proxied	orig_fuids	orig_mime_types	resp_fuids	resp_mime_types
//    #types	time	string	addr	port	addr	port	count	string	string	string	string	string	count	count	count	string	count	string	string	set[enum]	string	string	set[string]	vector[string]	vector[string]	vector[string]	vector[string]
// 27 fields
case class BroHttpLogEvent(tsJavaTime: Timestamp,
                           tsDouble: Double,
                           uid: String,
                           id_orig_host: String,
                           id_orig_port: Int,
                           id_resp_host: String,
                           id_resp_port: Int,
                           trans_depth: Int,
                           method: String,
                           host: String,
                           uri: String,
                           referrer: String,
                           user_agent: String,
                           request_body_len: String,
                           response_body_len: String,
                           status_code: Int,
                           status_msg: String,
                           info_code: String,
                           info_msg: String,
                           filename: String,
                           tags: String,
                           username: String,
                           password: String,
                           proxied: String,
                           orig_fuids: String,
                           orig_mime_types: String,
                           resp_fuids: String,
                           resp_mime_types: String) extends ParsedLogEvent with Ordered[BroHttpLogEvent] {

  //used for implicit sorting on the ts field
  def compare(that: BroHttpLogEvent) =
    tsDouble.compareTo(that.tsDouble)


  override def normalizeLogType[BroHttpLogEvent](inputLog: BroHttpLogEvent): Option[NormalizedLogEvent] = {
   None
 }

}



case class GenericProxyLogEvent(tsJavaTime: Timestamp, //0
                                timeString: String, //extra field
                                userName: String, //1
                                sourceIp: String, //2
                                destinationIp: String, //3
                                unknownField1: String, //4
                                statusCode: Int, //5
                                cacheResult: String, //6
                                httpMethod: String, //7
                                urlRequested: String, //8
                                httpVersion: String, //9
                                domainClass: String, //10
                                riskClass: String, //11
                                mimeType: String, //12
                                //   encodingFormat: String, //13
                                bytesSent: Int, //14
                                bytesReceived: Int, //15
                                userAgent: String, //16
                                webReferrer: String, //17
                                urlMetaData: UrlDataVector
                               ) extends ParsedLogEvent with Ordered[GenericProxyLogEvent] {

  //used for implicit sorting on the ts field
  def compare(that: GenericProxyLogEvent) =
    tsJavaTime.getTime.compareTo(that.tsJavaTime.getTime)

  override def normalizeLogType[GenericProxyLogEvent](inputLog: GenericProxyLogEvent): Option[NormalizedLogEvent] = {
    None
  }


}



case class NormalizedLogEvent(tsJavaTime: Timestamp)  extends Ordered[NormalizedLogEvent] {

  //used for implicit sorting on the ts field
  def compare(that: NormalizedLogEvent) =
    tsJavaTime.getTime.compareTo(that.tsJavaTime.getTime)
}