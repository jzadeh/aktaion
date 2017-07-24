#Whois

#import whois
#domain = whois.whois('google.com')
#print(domain.__dict__)
#print(domain.name)
#print(domain.expiration_date)

import whois 
w = whois.whois('boxden.com')
print w