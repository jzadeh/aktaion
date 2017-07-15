import unittest
from GenericProxyParser import generic_line_parser
unittest.TestCase.maxDiff = None


class GenericProxyParser(unittest.TestCase):


    knownValues = ((
                   '[09/Jan/2014:04:53:04 -0800] "Nico Rosberg" 172.16.2.101 77.75.107.241 1500 200 TCP_HIT "GET http://www.divernet.com/ HTTP/1.1" "Internet Services" "low risk " "text/html; charset=utf-8" 470 396 "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)" "http://www.google.com/url?sa=t&rct=j&q=&esrc=s&frm=1&source=web&cd=1&sqi=2&ved=0CCoQFjAA&url=http%3A%2F%2Fwww.divernet.com%2F&ei=opvOUpyXFrSA2QXnv4DwDg&usg=AFQjCNHeSe4ebK0u69M-TBEGNkTZy-C-Nw&bvm=bv.59026428,d.b2I" "-" "0" "" "-"'
                   , {'urlRequested': 'http://www.divernet.com/',
                      'userAgentString': 'Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)',
                      'urlMeta4': '-', 'timeString': '09/Jan/2014 04:53:04 -0800',
                      'webReferrerString': 'http://www.google.com/url?sa=t&rct=j&q=&esrc=s&frm=1&source=web&cd=1&sqi=2&ved=0CCoQFjAA&url=http%3A%2F%2Fwww.divernet.com%2F&ei=opvOUpyXFrSA2QXnv4DwDg&usg=AFQjCNHeSe4ebK0u69M-TBEGNkTZy-C-Nw&bvm=bv.59026428,d.b2I',
                      'httpMethod': 'GET', 'sourceIP': '172.16.2.101', 'statusCode': '200',
                      'mimeType': 'text/html; charset=utf-8', 'userName': 'Nico Rosberg', 'unknownValue': '1500',
                      'bytesReceived': '396', 'urlMeta1': '-', 'urlMeta3': '', 'dateTime': '09/Jan/2014:04:53:04',
                      'timezone': '-0800', 'domainClassification': 'Internet Services', 'urlMeta': '- 0  -',
                      'cacheResult': 'TCP_HIT', 'convertedTime': 1389271984.0, 'riskClassification': 'low risk ',
                      'destinationIP': '77.75.107.241', 'bytesSent': '470', 'httpVersion': '1.1', 'urlMeta2': '0'}
                   ),
                   )

    def test_type(self):
        for line, dictionary in self.knownValues:
            result = generic_line_parser(line)
            self.assertEqual(type(result), type(dictionary), 'invlaid return type')

if __name__ == '__main__':
        unittest.main()