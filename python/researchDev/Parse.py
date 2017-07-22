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
print (x)


