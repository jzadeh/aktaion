#import urllib
#resp = urllib.urlopen('http://bit.ly/2tEdiXm')
#resp.getcode()
#resp.url

import requests
url = 'http://bit.ly/2tEdiXm'
t = requests.get(url)
print(t.url)