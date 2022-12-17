package github.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.Utils;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

/**
 * Getting started with RestAssured
 */
public class SmokeTestsV1 {

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
    public void createIssue() {
        issueNumber = given().
                contentType(ContentType.JSON).
                body("{\"title\":\"Found a bug\",\"body\":\"I'm having a problem with this.\",\"labels\":[\"bug\"]}").
                when().
                post().
                then().
                statusCode(201).
                body("user.login", equalTo(user)).
                body("labels.name", hasItems("bug")).
                extract().
                path("number");

    }

    @Test(dependsOnMethods = "SmokeTestsV1.createIssue")
    public void getIssueDetails() {
        when().
                get(String.valueOf(issueNumber)).
                then().
                statusCode(200).
                body("number", equalTo(issueNumber)).
                body("user.login", equalTo(user)).
                body("labels.name", hasItems("bug"));
    }

    @Test(dependsOnMethods = "SmokeTestsV1.createIssue")
    public void updateIssue() {
        given().
                contentType(ContentType.JSON).
                body("{\"title\":\"Found a bug\",\"state\":\"closed\"}").
                when().
                patch(String.valueOf(issueNumber)).
                then().
                statusCode(200).
                body("user.login", equalTo(user)).
                body("labels.name", hasItems("bug")).
                body("state", equalTo("closed"));
    }
}
