# coding: utf-8

def userinterface():
## Show menu ##
    print
print((80 * '-'))
print ('  █████╗ ██╗  ██╗████████╗ █████╗ ██╗ ██████╗ ███╗   ██╗ ')
print (' ██╔══██╗██║ ██╔╝╚══██╔══╝██╔══██╗██║██╔═══██╗████╗  ██║ ')
print (' ███████║█████╔╝    ██║   ███████║██║██║   ██║██╔██╗ ██║ ')
print (' ██╔══██║██╔═██╗    ██║   ██╔══██║██║██║   ██║██║╚██╗██║ ')
print (' ██║  ██║██║  ██╗   ██║   ██║  ██║██║╚██████╔╝██║ ╚████║ ')
print (' ╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝╚═╝ ╚═════╝ ╚═╝  ╚═══╝ ')
print (' ██╗   ██╗███████╗██████╗ ███████╗██╗ ██████╗ ███╗   ██╗    ██████╗     ██████╗ ')
print (' ██║   ██║██╔════╝██╔══██╗██╔════╝██║██╔═══██╗████╗  ██║    ╚════██╗   ██╔═████╗ ')
print (' ██║   ██║█████╗  ██████╔╝███████╗██║██║   ██║██╔██╗ ██║     █████╔╝   ██║██╔██║ ')
print (' ╚██╗ ██╔╝██╔══╝  ██╔══██╗╚════██║██║██║   ██║██║╚██╗██║    ██╔═══╝    ████╔╝██║ ')
print ('  ╚████╔╝ ███████╗██║  ██║███████║██║╚██████╔╝██║ ╚████║    ███████╗██╗╚██████╔╝ ')
print ('   ╚═══╝  ╚══════╝╚═╝  ╚═╝╚══════╝╚═╝ ╚═════╝ ╚═╝  ╚═══╝    ╚══════╝╚═╝ ╚═════╝ ')
print((80 * '-'))
print ("1: Analyze Bro HTTP Sample Using Default Model")
print ("2: Analyze PCAP Sample (Bro must be installed) Using Default Model")
print ("3: Demo (Unix/OS X System)")
print((80 * '-'))

## Get input ###
choice = eval(input('Enter your choice [1-3] : '))

### Convert string to int type ##
choice = int(choice)

### Take action as per selected menu-option ###
if choice == 1:
    print ("Analyze Bro Http")
elif choice == 2:
    print ("Analyze PCAP Sample")
elif choice == 3:
    print ("Demo")
else:    ## default ##
    print ("Invalid number. Try again...")