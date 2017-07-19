#Unit test for BroHttpPyParser#
from BroHttpPyParser import bro_http_parser
import unittest
import os
import inspect

#this is to deal with the log file length exceeding the TestCase allowable length
unittest.TestCase.maxDiff = None

#make example log data file path relative to the project
directory = os.path.dirname(os.path.abspath(inspect.stack()[0][1]))
filename = os.path.join(directory, '../../data/broData/ExploitExample/http.log')
exceptionFile = os.path.join(directory, '../../testdata/badBroLog.log')

#Contract
#1.Returns a dictionary given a bro-log
#2.Throws invalidFormatError if given a non-brolog format
#3.Throws emptyFile error if given a log with no info.
#4.Throws invalidPath error if unable to locate a logfile.

class BroHttpPyParser(unittest.TestCase):
    knownValues = ( (filename
                       ,{'host': 'graphicstreeme.com', 'method': 'GET', 'idOrigPort': 49161, 'password': '-', 'uri': '/wp-content/plugins/theme-check/misc.php?572A56481F78D91A71F483FAC3626A6F89E2D4AFC98B8E4D38D901CB11D6B924D13EDDCA9E1C27D91D71987B1051AD6B2F9BEA566F4F3045C43796BFEC4C8AF763F838783B32EE6F30599814D4C07EDA1CB04100BE5491A459ED2919E1E7F57FFBF78B983B91D398700387E8A31738D900E2E32075CF665A12BD8AD4718F7B32F695E398862E28B15DE8A44AA7A63AF0648C44373229C87CD8566B3E64F4677A1B79C1DB1C9D9AB52836A8230F62BBCB144F4B8CA8A44BAAC4D35497A512995BC1865425D0F0C5E4380181F73DE7690B7680D4FA05D2A419B66DA62943BDF7276B100B5DC2B1F39D53847F3768053ED3C273A328CEF9BEBBC84D28FDEAB69E114D3DF889E54074029D8232027596623990647E1D01D1D402657382B1F51D05F5B272ED3C7615A7D0CD647F85F1FA10E55F7F1749565525526D227D5941A9867E59E45879712590AACA4336088056A91FF3A3129B1384811DE40F749EB09896F91704F83CB5A347EBE4D3B5D2D45851DF', 'info_code': '-', 'info_msg': '-', 'requestBodyLen': 0, 'trans_depth': 2, 'id.orig_p': 49161, 'resp_fuids': 'FWdp4h4pEuJOLZrJA9', 'statusCode': 'OK', 'resp_mime_types': 'text/plain', 'username': '-', 'ts': 1450372321.641135, 'epochTime': 1450372321.641135, 'referrer': '-', 'idRespPort': 80, 'request_body_len': 0, 'id.orig_h': '172.16.25.128', 'orig_mime_types': '-', 'orig_fuids': '-', 'id.resp_h': '182.50.130.156', 'idOrigHost': '172.16.25.128', 'status_msg': 'OK', 'user_agent': 'Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; Touch; rv:11.0) like Gecko', 'status_code': 200, 'filename': '-', 'transDepth': 2, 'responseBodyLen': 25, 'uid': 'CCZDtw19H01FglVpy1', 'id.resp_p': 80, 'tags': '(empty)', 'userAgent': 'Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; Touch; rv:11.0) like Gecko', 'fullUrl': 'https:\\graphicstreeme.com/wp-content/plugins/theme-check/misc.php?572A56481F78D91A71F483FAC3626A6F89E2D4AFC98B8E4D38D901CB11D6B924D13EDDCA9E1C27D91D71987B1051AD6B2F9BEA566F4F3045C43796BFEC4C8AF763F838783B32EE6F30599814D4C07EDA1CB04100BE5491A459ED2919E1E7F57FFBF78B983B91D398700387E8A31738D900E2E32075CF665A12BD8AD4718F7B32F695E398862E28B15DE8A44AA7A63AF0648C44373229C87CD8566B3E64F4677A1B79C1DB1C9D9AB52836A8230F62BBCB144F4B8CA8A44BAAC4D35497A512995BC1865425D0F0C5E4380181F73DE7690B7680D4FA05D2A419B66DA62943BDF7276B100B5DC2B1F39D53847F3768053ED3C273A328CEF9BEBBC84D28FDEAB69E114D3DF889E54074029D8232027596623990647E1D01D1D402657382B1F51D05F5B272ED3C7615A7D0CD647F85F1FA10E55F7F1749565525526D227D5941A9867E59E45879712590AACA4336088056A91FF3A3129B1384811DE40F749EB09896F91704F83CB5A347EBE4D3B5D2D45851DF', 'response_body_len': 25, 'idRespHost': '182.50.130.156', 'proxied': '-'}
                       ),
                        )
#1:
#class testLogMethods(unittest.TestCase):

    def test_type(self):
        for file, dictionary in self.knownValues:
            result = bro_http_parser(file)
            self.assertEqual(type(result), type(dictionary), 'invlaid return type')

#2
    def test_format(self):
            self.assertRaises(bro_http_parser(exceptionFile))

if __name__ == '__main__':
        unittest.main()
