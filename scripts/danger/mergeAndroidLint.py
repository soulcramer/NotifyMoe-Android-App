import sys
import os.path
from xml.etree import ElementTree

first = None
file_list = [
"cache/build/reports/lint-results.xml",
"app/build/reports/lint-results.xml",
"core/build/reports/lint-results.xml",
"domain/build/reports/lint-results.xml",
"data/build/reports/lint-results.xml",
"remote/build/reports/lint-results.xml"
]

lintFile = 'lint-report-orig.xml'
editedlintFile = 'lint-report.xml'

for filename in file_list:
    if os.path.isfile(filename):
        data = ElementTree.parse(filename).getroot()
        if first is None:
            first = data
        else:
            first.extend(data)
if first is not None:
    f = open( lintFile, 'w' )
    f.write( "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" )
    f.write( ElementTree.tostring(first) )
    f.close()

delete_list = ["/bitrise/src/"]
fin = open(lintFile)
fout = open(editedlintFile, "w+")
for line in fin:
    for word in delete_list:
        line = line.replace(word, "")
    fout.write(line)
fin.close()
fout.close()