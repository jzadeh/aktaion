package com.aktaion.parser

import java.sql.Timestamp


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


}

//    #fields	ts	uid	id.orig_h	id.orig_p	id.resp_h	id.resp_p	trans_depth	method	host	uri	referrer	user_agent	request_body_len	response_body_len	status_code	status_msg	info_code	info_msg	filename	tags	username	password	proxied	orig_fuids	orig_mime_types	resp_fuids	resp_mime_types
//    #types	time	string	addr	port	addr	port	count	string	string	string	string	string	count	count	count	string	count	string	string	set[enum]	string	string	set[string]	vector[string]	vector[string]	vector[string]	vector[string]
// 27 fields
case class BroHttpLogEvent(tsDouble: Double, //0
                           uid: String, //1
                           id_orig_host: String, //2
                           id_orig_port: Int, //3
                           id_resp_host: String, //4
                           id_resp_port: Int, //5
                           trans_depth: Int, //6
                           method: String, //7
                           host: String, //8
                           uri: String, //9
                           referrer: String, //10
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
                           resp_mime_types: String,
                           urlMetaData: UrlDataVector,
                           tsJavaTime: Timestamp) extends ParsedLogEvent with Ordered[BroHttpLogEvent] {

  //used for implicit sorting on the ts field
  def compare(that: BroHttpLogEvent) =
    tsDouble.compareTo(that.tsDouble)


}


case class NormalizedLogEvent(tsJavaTime: Timestamp,
                              timeString: String,
                              sourceIp: String,
                              destinationIp: String,
                              uri: String,
                              httpVersion: String,
                              mimeType: String,
                              userAgent: String,
                              statusCode: Int,
                              webReferrer: String,
                              urlMetaData: UrlDataVector) extends Ordered[NormalizedLogEvent] {

  //used for implicit sorting on the ts field
  def compare(that: NormalizedLogEvent) =
    tsJavaTime.getTime.compareTo(that.tsJavaTime.getTime)
}

object ParsingNormalizationLogic {
  def normalizeProxyLog(inputLog: GenericProxyLogEvent): NormalizedLogEvent = {
    val normData = NormalizedLogEvent(inputLog.tsJavaTime, inputLog.timeString,
      inputLog.sourceIp, inputLog.destinationIp, inputLog.urlRequested, inputLog.httpVersion,
      inputLog.mimeType, inputLog.userAgent,
      inputLog.statusCode, inputLog.webReferrer,
      inputLog.urlMetaData)
    return normData
  }

  def normalizeBroLog(inputLog: BroHttpLogEvent): NormalizedLogEvent = {
    val normData = NormalizedLogEvent(inputLog.tsJavaTime, inputLog.tsDouble.toString,
      inputLog.id_orig_host, inputLog.id_resp_host,
      inputLog.uri, "", inputLog.orig_mime_types,
      inputLog.user_agent, inputLog.status_code,
      inputLog.referrer, inputLog.urlMetaData)
    return normData
  }
}