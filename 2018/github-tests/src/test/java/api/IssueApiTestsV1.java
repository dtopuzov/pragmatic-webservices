package api;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import settings.Settings;
import utils.Utils;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.greaterThan;

/**
 * Tests for issues api.
 * Initial version, use only RestAssured, read request body from json files.
 */
public class IssueApiTestsV1 {

    private int issueId;

    @BeforeClass
    public void beforeIssueApiTests() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.baseURI = Settings.BASE_API_URL;
        RestAssured.basePath = String.format("/repos/%s/%s", Settings.USER, Settings.REPO);
    }

    @Test(dependsOnMethods = "createIssue")
    public void getIssueDetails() {
        given().
                auth().preemptive().
                basic(Settings.USER, Settings.PASS).
                when().
                get("/issues/{id}", issueId).
                then().
                statusCode(200).
                body("number", equalTo(issueId)).
                body("user.login", equalTo(Settings.USER));
    }

    @Test(dependsOnMethods = "createIssue")
    public void getOpenIssues() {
        given().
                auth().preemptive().
                basic(Settings.USER, Settings.PASS).
                param("state", "open").
                when().
                get("/issues").
                then().
                statusCode(200).
                body("size()", greaterThan(1)).
                body("[0].title", equalTo("Found a bug")).
                body("title", hasItems("Found a bug"));
    }

    @Test
    public void createIssue() throws IOException {
        String body = Utils.readFile("data/create-issue-body.json");

        issueId =
                given().
                        auth().preemptive().
                        basic(Settings.USER, Settings.PASS).
                        body(body).
                        when().
                        post("/issues").
                        then().
                        statusCode(201).
                        body("user.login", equalTo(Settings.USER)).
                        extract().
                        path("number");
    }

    @Test(dependsOnMethods = "createIssue")
    public void updateIssue() throws IOException {
        String body = Utils.readFile("data/update-issue-body.json");

        given().
                auth().preemptive().
                basic(Settings.USER, Settings.PASS).
                body(body).
                when().
                patch("/issues/{id}", issueId).
                then().
                statusCode(200).
                body("state", equalTo("closed")).
                body("user.login", equalTo(Settings.USER));
    }
}
