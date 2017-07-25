#TODO add docstring info
from brothon import bro_log_reader
from pprint import pprint


#take a Bro log file
#return a dictionary with all log info, including legacy variable names from the scala version.

#TODO create invalidFormattError exception handle
#TODO ceate emptyFile exception handle
#TODO create invalidPath exception handle
##^^ check how brothon handles these first, possible duplication of exceptions
    ##broLogReader throws no errors when given an invalid file path
    ##broLogReader throws 'Conversion Issue for key' message, but returns whatever data can be processed
    ##broLogReader throws 'Conversion Issue for key' message, returns nothing


def bro_http_parser(inFile):
    #reader = bro_log_reader.BroLogReader('/Users/Gary/PycharmProjects/Aktaion/data/broData/ExploitExample/http.log')
    reader = bro_log_reader.BroLogReader(inFile)
    dictionaryIndex = 1
    masterDictionary = {}
    for row in reader.readrows():

        #cast row dictionary into a new dictionary to provide legacy variable names
        row["idOrigHost"]      = row.pop('id.orig_h')
        row["idOrigPort"]      = row.pop('id.orig_p')
        row["idRespHost"]      = row.pop('id.resp_h')
        row["idRespPort"]      = row.pop('id.resp_p')
        row["transDepth"]      = row.pop('trans_depth')
        row["userAgent"]       = row.pop('user_agent')
        row["requestBodyLen"]  = row.pop('request_body_len')
        row["responseBodyLen"] = row.pop('response_body_len')
        row["statusCode"]      = row.pop('status_msg')
        row["epochTime"]       = row.pop('ts')

        if row["idRespHost"] == 443:
            fUrl = {"fullUrl": "https:\\" +row["host"] + row["uri"]}
        else:
            fUrl = {"fullUrl": "https:\\" + row["host"] + row["uri"]}
        row.update(fUrl)
        masterDictionary[dictionaryIndex] = row
        dictionaryIndex += 1
        #
        # row.update(row1) #TODO return just row1 with all 27 relabeled fields + fullUrl
        #pprint(row)

    return(masterDictionary)

#pprint(masterDictionary)

    # pd_row = pd.DataFrame.from_dict(row)
    #
    # print(type(pd_row))
    # print(pd_row)
    #
    # testlist = [1,2,3,4,5,6,7,8]
    # testval = 1
    # tesval2 = 9
    # for testlist:
    #     if testval = testlist[]
    #         print(testval)
