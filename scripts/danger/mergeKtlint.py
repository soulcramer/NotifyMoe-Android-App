import sys
import os.path
from xml.etree import ElementTree

first = None
file_list = [
"cache/build/reports/ktlint/ktlintMainSourceSetCheck.xml",
"cache/build/reports/ktlint/ktlintTestSourceSetCheck.xml",
"remote/build/reports/ktlint/ktlintMainSourceSetCheck.xml",
"remote/build/reports/ktlint/ktlintTestSourceSetCheck.xml",
"data/build/reports/ktlint/ktlintMainSourceSetCheck.xml",
"data/build/reports/ktlint/ktlintTestSourceSetCheck.xml",
"domain/build/reports/ktlint/ktlintMainSourceSetCheck.xml",
"domain/build/reports/ktlint/ktlintTestSourceSetCheck.xml",
"core/build/reports/ktlint/ktlintMainSourceSetCheck.xml",
"app/build/reports/ktlint/ktlintAndroidTestSourceSetCheck.xml",
"app/build/reports/ktlint/ktlintTestSourceSetCheck.xml",
"app/build/reports/ktlint/ktlintMainSourceSetCheck.xml"
]

ktlintFile = 'ktlint-report-orig.xml'
editedKtlintFile = 'ktlint-report.xml'

for filename in file_list:
    if os.path.isfile(filename):
        data = ElementTree.parse(filename).getroot()
        if first is None:
            first = data
        else:
            first.extend(data)

if first is not None:
    f = open( ktlintFile, 'w' )
    f.write( "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" )
    f.write( ElementTree.tostring(first) )
    f.close()

delete_list = ["/bitrise/src/"]
fin = open(ktlintFile)
fout = open(editedKtlintFile, "w+")
for line in fin:
    for word in delete_list:
        line = line.replace(word, "")
    fout.write(line)
fin.close()
fout.close()