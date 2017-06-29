# API and UI Tests with Java

## Maven dependencies in this solutions
```
    <dependencies>
        <dependency>
            <groupId>org.testng</groupId>
            <artifactId>testng</artifactId>
            <version>6.11</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.rest-assured</groupId>
            <artifactId>rest-assured</artifactId>
            <version>3.0.3</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.codehaus.jackson</groupId>
            <artifactId>jackson-mapper-asl</artifactId>
            <version>1.8.5</version>
        </dependency>
    </dependencies>
```

## HowTos

[jsonschema2pojo](http://www.jsonschema2pojo.org/)
For current proejct we use Jackson 1.x

## Command-line execution
mvn clean test -Dtest=GitHub.API.GitHubAPITests

