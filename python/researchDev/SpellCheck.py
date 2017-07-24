import re, collections

def words(text):
    return re.findall('[a-z]+', text.lower())

#def words(text): return re.findall(r'\w+', text.lower())

def train(features):
    model = collections.defaultdict(lambda: 1)
    for f in features:
        model[f] += 1
    return model

#NWORDS = train(words(open(r'c:\users\dryft\desktop\text.txt','r')))
#NWORDS = open(r'c:\users\dryft\desktop\text.txt','r')
#open(r'c:\users\dryft\desktop\text.txt','r')
#ofile=open('c:\users\dryft\desktop\text.txt','r')
#ofile=open('C:\users\dryft\desktop\URLlist.txt','r')
#ofile=file('C:\users\dryft\desktop\text.txt').read()
#file('C:\\users\\dryft\\desktop\text.txt','r')
#owords=words(ofile)
#NWORDS=train(owords)
alphabet = 'abcdefghijklmnopqrstuvwxyz'

def edits1(word):
    s = [(word[:i], word[i:]) for i in range(len(word) + 1)]
    deletes    = [a + b[1:] for a, b in s if b]
    transposes = [a + b[1] + b[0] + b[2:] for a, b in s if len(b)>1]
    replaces   = [a + c + b[1:] for a, b in s for c in alphabet if b]
    inserts    = [a + c + b     for a, b in s for c in alphabet]
    return set(deletes + transposes + replaces + inserts)

x=edits1('new')
print(x)
#def known_edits2(word):
#    return set(e2 for e1 in edits1(word) for e2 in edits1(e1) if e2 in NWORDS)
#
#def known(words):
#    return set(w for w in words if w in NWORDS)
#
#def correct(word):
#    candidates = known([word]) or known(edits1(word)) or    known_edits2(word) or [word]
#    return max(candidates, key=NWORDS.get)


