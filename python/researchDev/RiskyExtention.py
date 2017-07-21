class RiskyExtention:
    def RiskyExtention():

        from urlparse import urlparse
        o = urlparse('http://www.cwi.nl:80/%7Eguido/Python.html')
        o.scheme
        o.port
        print(o)
        currentpath=o.path
        #splitpath=currentpath.split(".")
        testlist=[".pdf",".exe",".mp3",".mp4",".bin",".zip",".gif",".jpg",".ps1",".bat",".bin",".ps",".jar",".txt",".rar",".avi",".mov",".avi"]
        lastpath=currentpath[:-4]
        if lastpath in testlist:
            return True
        else:
            return False
    
x=RiskyExtention()
print x
    
import unittest
class RiskyTest(unittest.TestCase):
    def test_Risky(self):
        self.assertEqual()
        self.assertEqual()
        self.assertEqual()
#def palindrome(x):
#    return x == x[::-1]
#print palindrome('look')