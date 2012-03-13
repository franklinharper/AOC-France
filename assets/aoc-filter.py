import csv

f = csv.reader( open('aoc-wine.csv'), delimiter=',', quotechar='"' )
aoc_code_set = set()
for line in f:
    code = line[ 0 ]
    aoc_code_set.add( code )

f = csv.reader( open('aoc-complete.csv'), delimiter=',', quotechar='"' )
for line in f:
    code_commune = line[ 0 ]
    nom_commune = line[ 2 ]
    nom_aoc = line[ 4 ]
    code_aoc = line[ 5 ]
    #print code_aoc
    if code_aoc in aoc_code_set:
        output = ', '.join( ( code_commune, nom_commune, nom_aoc, code_aoc ) )
        str.replace( output, '\U0000009c', 'oe' )
        print output

