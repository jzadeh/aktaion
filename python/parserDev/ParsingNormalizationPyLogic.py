

#create the GenericProxyLogEvent class

class GenericProxyLogEvent:

    #construtor
    def __init__(self, tsJavaTime, timeString, sourceIp ,destinationIp, uri, httpVersion,
                 mimeType, userAgent, statusCode, webReferrer, urlMetaData):
        self.tsJavaTime     = tsJavaTime
        self.timeString     = timeString
        self.sourceIP       = sourceIp
        self.destinationIP  = destinationIp
        self.uri            = uri
        self.httpVersion    = httpVersion
        self.mimeType       = mimeType
        self.userAgent      = userAgent
        self.statusCode     = statusCode
        self.webReferrer    = webReferrer
        self.urlMetaData    = urlMetaData

#define a compare method for the GenericProxyLogEvent, takes in
    def compare(self, that):
        return (self.tsJavaTime > that.tsJavaTime) - (self.tsJavaTime < that.tsJavaTime)

#create the BroHttpLogEvent class
class BroHttpLogEvent:
    def __init__(self, tsDouble, uid, id_orig_host, id_orig_port, id_resp_host, id_resp_port,
                 trans_depth, method, host, referrer, user_agent, request_body_len, response_body_len,
                 status_code, status_msg, info_code, info_msg, filename, tags, username, password, proxied,
                 orig_fuids, orig_mime_types, resp_fuids, resp_mime_types, urlMetaData, tsJavaTime):
        self.tsDouble           = tsDouble
        self.uid                = uid
        self.id_orig_host       = id_orig_host
        self.id_orig_port       = id_orig_port
        self.id_resp_host       = id_resp_host
        self.id_resp_port       = id_resp_port
        self.trans_depth        = trans_depth
        self.method             = method
        self.id_resp_host       = host
        self.referrer           = referrer
        self.user_agent         = user_agent
        self.request_body_len   = request_body_len
        self.response_body_len  = response_body_len
        self.status_code        = status_code
        self.status_msg         = status_msg
        self.info_code          = info_code
        self.info_msg           = info_msg
        self.filename           = filename
        self.tags               = tags
        self.username           = username
        self.password           = password
        self.proxied            = proxied
        self.orig_fluids        = orig_fuids
        self.orig_mime_types    = orig_mime_types
        self.resp_fluids        = resp_fuids
        self.resp_mime_types    = resp_mime_types
        self.urlMetaData        = urlMetaData
        self.tsJavaTime         = tsJavaTime

#define a compare method for the BroHttpLogEvent
    def compare(self, that):
        return (self.tsJavaTime > that.tsJavaTime) - (self.tsJavaTime < that.tsJavaTime)

#create the NormalizedLogEvent class
class NormalizedLogEvent :
    def __init__(self,  tsJavaTime, timeString, sourceIp, destinationIp, uri, httpVersion,
                 mimeType, userAgent, statusCode, webReferrer, urlMetaData):
        self.tsJavaTime         = tsJavaTime
        self.timeString         = timeString
        self.sourceIp           = sourceIp
        self.destinationIp      = destinationIp
        self.uri                = uri
        self.httpVersion        = httpVersion
        self.mimeType           = mimeType
        self.userAgent          = userAgent
        self.statusCode         = statusCode
        self.webReferrer        = webReferrer
        self.urlMetaData        = urlMetaData

##define a compare method for the NormalizedLogEvent
    def compare(self, that):
        return (self.tsJavaTime > that.tsJavaTime) - (self.tsJavaTime < that.tsJavaTime)

