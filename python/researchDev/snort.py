import re
from collections import OrderedDict

class SnortRule(object):
    header_keys = ['action','protocol', 'srcip','srcport','direction','dstip','dstport']
    content_modifiers = [
        'nocase','rawbytes','depth','offset','distance','within',
        'http_client_body','http_cookie','http_raw_cookie','http_header',
        'http_raw_header','http_method','http_uri','http_raw_uri','http_stat_code',
        'http_stat_msg','fast_pattern',
    ]
    argless_keywords = [
        'http_method', 'ftpbounce','file_data','nocase','rawbytes','dce_stub_data',
        'fast_pattern','http_client_body', 'http_header','http_raw_cookie','http_raw_header',
        'http_method','http_uri','http_stat_code','http_stat_msg','http_cookie'
    ]

    def __init__ (self, rule=None):
        self.__dict__['classdict'] = OrderedDict()
        if rule is not None:
            self.parse(rule)
        else:
            self.action = 'alert'
            self.protocol = 'tcp'
            self.srcip = 'any'
            self.srcport = 'any'
            self.direction = '<>'
            self.dstip = 'any'
            self.dstport = 'any'
            self.msg = ['"default message"']

    def __getattr__(self, value):
        return self.__dict__['classdict'][value]
    def __setattr__(self, key, value):
        self.__dict__['classdict'][key] = value

    def in_dict(self, dictkey):
        if dictkey in self.__dict__['classdict']:
            result = [(key, value) for key, value in self.__dict__['classdict'].items() if key.startswith(dictkey)]
            return len(result)
        else:
            return 0

    def parse(self, rule):
        rule = str(rule).strip()
        rule_header = rule.split(' ')[0:7]

        # set the headers as class attributes
        for header, value in zip(self.header_keys, rule_header):
            setattr(self, header, value)

        rule_options = rule[len(' '.join(rule_header))+2:-1]
        rgx = re.compile('(\w+):(.*?);|(\w+);');
        matches = re.findall(rgx, rule_options)
        # loop through all the keywords first so we can handle multiple keywords
        # and handle aligning content modifiers
        for keyword,value,argless in matches:
            if argless:
                if self.in_dict(argless) == 0:
                    setattr(self, argless, 1)
                else:
                    setattr(self, argless + str(self.in_dict(argless) + 1), 1)
            else:
                if self.in_dict(keyword) == 0:
                    setattr(self, keyword, value)
                else:
                    setattr(self, keyword + str(self.in_dict(keyword) + 1), value)

    def __str__(self):
        keyword_string = ""
        for key in self.__dict__['classdict']:
            key_val = getattr(self, key)
            key = key.rstrip("0123456789")
            if key not in self.header_keys:
                # make sure we parse keywords that have no args correctly
                if key in self.argless_keywords:
                    #handle the fast_pattern exception
                    if key == 'fast_pattern' and not isinstance(key_val, int):
                        keyword_string += "%s:%s; " % (key, key_val)
                    else:
                        keyword_string += "%s; " % (key)
                else:
                    keyword_string += "%s:%s; " % (key, key_val)
        return "%s %s %s %s %s %s %s (%s)" % (self.action, self.protocol, self.srcip, self.srcport, self.direction, self.dstip, self.dstport, keyword_string[:-1])
