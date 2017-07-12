import pandas as pd
import re

#   #given a line of a generic log entry of the format shown in line 7, return a dictionary of labeled informtion
def GenericProxyParser(logLine):
    # example data
    #logLine = '[09/Jan/2014:04:53:04 -0800] "Nico Rosberg" 172.16.2.101 77.75.107.241 1500 200 TCP_HIT "GET http://www.divernet.com/ HTTP/1.1" "Internet Services" "low risk " "text/html; charset=utf-8" 470 396 "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)" "http://www.google.com/url?sa=t&rct=j&q=&esrc=s&frm=1&source=web&cd=1&sqi=2&ved=0CCoQFjAA&url=http%3A%2F%2Fwww.divernet.com%2F&ei=opvOUpyXFrSA2QXnv4DwDg&usg=AFQjCNHeSe4ebK0u69M-TBEGNkTZy-C-Nw&bvm=bv.59026428,d.b2I" "-" "0" "" "-" '

    logPattern = '\[(?P<dateTime>.*?) (?P<timezone>.*?)\] \"(?P<userName>.*?)\" (?P<sourceIP>.*?) (?P<destinationIP>.*?) (?P<unknownValue>.*?) (?P<statusCode>.*?) (?P<cacheResult>.*?) \"(?P<httpMethod>.*?) (?P<urlRequested>.*?) HTTP/(?P<httpVersion>.*?)\" \"(?P<domainClassification>.*?)\" \"(?P<riskClassification>.*?)\" \"(?P<mimeType>.*?)\" (?P<bytesSent>.*?) (?P<bytesReceived>.*?) \"(?P<userAgentString>.*?)\" \"(?P<webReferrerString>.*?)\" \"(?P<urlMeta1>.*?)\" \"(?P<urlMeta2>.*?)\" \"(?P<urlMeta3>.*?)\" \"(?P<urlMeta4>.*?)\"'


    matched = re.match(logPattern, logLine)
    matched_dict = matched.groupdict()


    #   #get the timestamp as a string, reformated to feed into pd.to_datetime
    #   #get the epoch time using the to_epoch function

    valPartitioned = logLine.split(" ")
    dt_string = valPartitioned[0].replace("[", "").replace(":"," ", 1)
    timeZone = valPartitioned[1].replace("]","")
    #timeZone = timeZone.replace("0","")
    dt_string = dt_string + " " + timeZone


    def to_epoch(dt):
        epoch = pd.to_datetime('1970-01-01')
        return (dt - epoch).total_seconds()

    dt_obj = pd.to_datetime(dt_string)
    convertedTime = to_epoch(dt_obj)


    #now appended the dictionary with the dt_obj object and dt_string and
    #the concatinated URL meta data
    matched_dict['convertedTime'] = convertedTime
    matched_dict['timeString'] = dt_string
    matched_dict['urlMeta'] =  matched_dict['urlMeta1'] + " " + matched_dict['urlMeta2'] + " " + matched_dict['urlMeta3'] + " " + matched_dict['urlMeta4']
    return(matched_dict)
