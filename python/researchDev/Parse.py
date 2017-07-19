class Parse:
    def Parse():
        with open('C:\users\dryft\desktop\URLlist.txt','r') as infile:
            data = infile.read()
            testdata = "www.bit.ly"
            my_list = data.splitlines()
            for word in my_list:
                if word in testdata:
                    return True
                else:
                    return False
                    
x=Parse()
print x

import unittest
class ParseTest(unittest.TestCase):
    def test_Parse(self):
        self.assertEqual( Parse())
        self.assertEqual( Parse())
        self.assertEqual( Parse())
