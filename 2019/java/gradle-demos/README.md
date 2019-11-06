# Gradle demos

## About

Automated tests for GitHub web site and REST api.

## Libraries 
- [TestNG](https://testng.org/doc/) as unit testing framework.
- [Selenium](https://www.seleniumhq.org/projects/webdriver/) to drive the browser.
- [RestAssured](http://rest-assured.io/) to interact with REST api. 

## Before Running Tests

Test config is located at `src/test/resources/config/TEST.properties` and looks like this:
```bash
baseApiUrl=https://api.github.com
baseWebUrl=https://github.com
repo=test
user=ws-test-user
pass=it-is-a-secret
```
Before running tests make sure you set correct repo, user and password.

## Run Tests

Run API tests:
```bash
./gradlew apiTests
```

Run UI tests:
```bash
./gradlew webTests
```

Reports of test run are available at:
`$projectRoot/build/reports/test/apiTests/index.html`

# Checkstyle

Run checkstyle check:
```bash
./gradlew check
```

Rules are defined at:
`$projectRoot/config/checkstyle/checkstyle.xml`

## HowTos

### Generate Pojo from Json

[jsonschema2pojo](http://www.jsonschema2pojo.org/) web site can help you generate java classes from json.

Video: https://www.youtube.com/watch?v=fxYyrbwECJ8