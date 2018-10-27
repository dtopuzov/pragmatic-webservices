package api;

import api.objects.IssueDetails;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import settings.Settings;

import static io.restassured.RestAssured.given;

/**
 * Tests for issues api.
 * Improved version, use Java objects in requests and responses.
 */
public class IssueApiTestsV2 {

    private int issueId;

    @BeforeClass
    public void beforeIssueApiTests() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.baseURI = Settings.BASE_API_URL;
        RestAssured.basePath = String.format("/repos/%s/%s", Settings.USER, Settings.REPO);
    }

    @Test(dependsOnMethods = "createIssue")
    public void getIssueDetails() {
        IssueDetails issue = given().
                auth().preemptive().
                basic(Settings.USER, Settings.PASS).
                when().
                get("/issues/{id}", issueId).
                then().
                statusCode(200).
                extract().
                response().getBody().as(IssueDetails.class);
        Assert.assertEquals(issue.title, "Found a bug");
        Assert.assertEquals(issue.user.login, Settings.USER);
    }

    @Test(dependsOnMethods = "createIssue")
    public void getOpenIssues() {
        IssueDetails[] issues = given().
                auth().preemptive().
                basic(Settings.USER, Settings.PASS).
                param("state", "open").
                when().
                get("/issues").
                then().
                statusCode(200).extract().
                response().getBody().as(IssueDetails[].class);


        Assert.assertTrue(issues.length > 0);
        Assert.assertEquals(issues[0].title, "Found a bug");
        boolean isFound = false;
        for (IssueDetails issue : issues) {
            if (issue.title.equals("Found a bug")) {
                isFound = true;
                break;
            }
        }
        Assert.assertTrue(isFound);
    }

    @Test
    public void createIssue() {
        IssueDetails issue = new IssueDetails();
        issue.title = "Found a bug";
        issue.body = "Bug in object";

        IssueDetails result =
                given().
                        auth().preemptive().
                        basic(Settings.USER, Settings.PASS).
                        contentType("application/json; charset=UTF-8").
                        body(issue).
                        when().
                        post("/issues").
                        then().
                        statusCode(201).extract().
                        response().getBody().as(IssueDetails.class);

        Assert.assertEquals(result.user.login, Settings.USER);
        issueId = result.number;
    }

    @Test(dependsOnMethods = "createIssue")
    public void updateIssue() {
        IssueDetails issue = new IssueDetails();
        issue.state = "closed";

        IssueDetails result = given().
                auth().preemptive().
                basic(Settings.USER, Settings.PASS).
                body(issue).
                when().
                patch("/issues/{id}", issueId).
                then().
                statusCode(200).extract().
                response().getBody().as(IssueDetails.class);

        Assert.assertEquals(result.state, "closed");
        Assert.assertEquals(result.user.login, Settings.USER);
    }
}
