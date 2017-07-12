import bro_log_reader

#take a Bro log file, formatted as follows:
#fields	ts	uid	id.orig_h	id.orig_p	id.resp_h	id.resp_p	trans_depth	method	host	uri	referrer	user_agent	request_body_len	response_body_len	status_code	status_msg	info_code	info_msg	filename	tags	username	password	proxied	orig_fuids	orig_mime_types	resp_fuids	resp_mime_types
#return a dictionary with all the info, including legacy variable names from the scala version.


def BroHttpParser(inFile):
    #reader = bro_log_reader.BroLogReader('/Users/Gary/PycharmProjects/Aktaion/data/broData/ExploitExample/http.log')
    reader = bro_log_reader.BroLogReader(inFile)
    for row in reader.readrows():


        #cast row dictionary into a new dictionary to provide legacy variable names
        row1 = {"uid": row['uid'],
                "idOrigHost": row['id.orig_h'],
                "idOrigPort": row['id.orig_p'],
                "idRespHost": row['id.resp_h'],
                "idRespPort": row['id.resp_p'],
                "transDepth": row['trans_depth'],
                "method":     row['method'],
                "host":       row['host'],
                "uri":        row['uri'],
                "referrer":   row['referrer'],
                "userAgent":  row['user_agent'],
                "requestBodyLen": row['request_body_len'],
                "responseBodyLen": row['response_body_len'],
                "statusCode": row['status_msg'],
                "epochTime":  row['ts'],
                }

        if row1["idRespPort"] == 443:
            fUrl = {"fullUrl": "https:\\" +row1["host"] + row1["uri"]}
        else:
                fUrl = {"fullUrl": "https:\\" + row1["host"] + row1["uri"]}
        row1.update(fUrl)

        row.update(row1)

        return(row)

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
