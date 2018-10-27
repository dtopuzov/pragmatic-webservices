# Gradle, Checkstyle & TestNG Demo Project

## Run Tests

   Run tests in package
   ```
   gradlew clean test --tests formula1.*
   ```

## Check for code style and errors

[Checkstyle](https://docs.gradle.org/current/userguide/checkstyle_plugin.html) is used to check code style.

Rules are listed in `config/checkstyle/checkstyle.xml`

`build` and `test` tasks dependes on `check`, so checks will be executed each time when you build or run tests.

Task depencies are defined in `build.gradle`
```
build.dependsOn(check)
test.dependsOn(check)
check.dependsOn.remove(test)
```
