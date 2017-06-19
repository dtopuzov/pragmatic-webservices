rmdir result /S /Q
mkdir result
newman run tests\json-server.postman_collection.json -r cli,junit,html --reporter-junit-export result\result.xml --reporter-html-export result\result.html