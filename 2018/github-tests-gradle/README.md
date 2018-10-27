# GitHub Tests

This project demonstrates how we can write API and UI tests for GitHub (and how we can mix them).

Used technologies:
- Gradle
- TestNG
- RestAssured
- Selenium

# Before Running Tests

Test config is located at `src/test/resources/config/TEST.properties` and looks like this:
```bash
baseApiUrl=https://api.github.com
baseWebUrl=https://github.com
repo=test
user=ws-test-user
pass=it-is-a-secret
```
Before running tests make sure you set correct repo, user and password.

# Run Tests

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


