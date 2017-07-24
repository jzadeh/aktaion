#!/usr/bin/python
#
# Stolen from Ero Carrera
# http://blog.dkbza.org/2007/05/scanning-data-for-entropy-anomalies.html

import math, string
# fileinput

def range_bytes (): return list(range(256))
def range_printable(): return (ord(c) for c in string.printable)
def H(data, iterator=range_bytes):
    if not data:
        return 0
    entropy = 0
    for x in iterator():
        p_x = float(data.count(chr(x)))/len(data)
        if p_x > 0:
            entropy += - p_x*math.log(p_x, 2)
    return entropy

#def main ():
#    for row in fileinput.input():
#        string = row.rstrip('\n')
#        print ("%s: %f" % (string, H(string, range_printable)))

for str in ['gargleblaster', 'tripleee', 'magnus', 'lkjasdlk',
               'aaaaaaaa', 'sadfasdfasdf', '7&wS/p(']:
    print(("%s: %f" % (str, H(str, range_printable))))

from scipy import stats
import numpy
t1=numpy.random.normal(-2.5,0.1,1000)

t2=numpy.random.normal(-2.5,0.1,1000)

stats.entropy(t1,t2)
