# coding: utf-8
print((77 * '-'))
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
print((77 * '-'))
print ("1: Analyze Bro HTTP Sample Using Default Model")
print ("2: Analyze PCAP Sample (Bro must be installed) Using Default Model")
print ("3: Demo (Unix/OS X System)")
print((77 * '-'))


choice = eval(input('Enter your choice [1-3] : '))


choice = int(choice)


if choice == 1:
    print ("Analyze Bro Http")
elif choice == 2:
    print ("Analyze PCAP Sample")
elif choice == 3:
    print ("Demo")
else:    ## default ##
    print ("Invalid number. Try again...")