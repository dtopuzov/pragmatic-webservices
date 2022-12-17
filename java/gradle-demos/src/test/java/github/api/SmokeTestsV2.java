package github.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.Utils;

import java.io.IOException;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

/**
 * Refactored version of SmokeTestsV1 where body is read from file instead of passing plain string.
 */
public class SmokeTestsV2 {

    private int issueNumber;
    private String user = Utils.getEnvironmentVariable("USER");
    private String pass = Utils.getEnvironmentVariable("PASS");

    @BeforeClass
    public void beforeIssueApiTests() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.baseURI = "https://api.github.com";
        RestAssured.basePath = String.format("/repos/%s/%s/issues", user, "test");
        RestAssured.authentication = preemptive().basic(user, pass);
    }

    @Test
    public void createIssue() throws IOException {
        String body = Utils.readFile("data/create-issue-body.json");
        issueNumber = given().
                contentType(ContentType.JSON).
                body(body).
                when().
                post().
                then().
                statusCode(201).
                body("user.login", equalTo(user)).
                body("labels.name", hasItems("bug")).
                extract().
                path("number");

    }

    @Test(dependsOnMethods = "SmokeTestsV2.createIssue")
    public void getIssueDetails() {
        when().
                get(String.valueOf(issueNumber)).
                then().
                statusCode(200).
                body("number", equalTo(issueNumber)).
                body("user.login", equalTo(user)).
                body("labels.name", hasItems("bug"));
    }

    @Test(dependsOnMethods = "SmokeTestsV2.createIssue")
    public void updateIssue() throws IOException {
        String body = Utils.readFile("data/update-issue-body.json");
        given().
                contentType(ContentType.JSON).
                body(body).
                when().
                patch("{id}", issueNumber).
                then().
                statusCode(200).
                body("user.login", equalTo(user)).
                body("labels.name", hasItems("bug")).
                body("state", equalTo("closed"));
    }
}
