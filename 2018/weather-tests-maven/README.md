# Google Search for Weather Tests

This project demonstrates how we can mix UI and API calls in single test case.

The Task:
- Write test that verify Google search for temperature.

The Problem:
- Temperature value is dynamic.

The Solution:
- Write test that get value of temperature from web site and from rest api then compare values.ó

Used technologies:
- Maven
- TestNG
- RestAssured
- Selenium

# Run Tests

```bash
mvn clean test -Dtest=weather.*
```

Reports of test run are available at:
`$projectRoot/target/surefire-reports/index.html`
