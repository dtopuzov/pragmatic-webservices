package github.api;

import github.objects.IssueDetails;
import github.objects.Label;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

/**
 * Refactored version of SmokeTestsV1 using objects instead of strings in body.
 */
public class SmokeTestsV3 {

    private Integer issueNumber;
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
        Label label = new Label();
        label.name = "bug";
        List<Label> labels = new ArrayList<>();
        labels.add(label);
        IssueDetails details = new IssueDetails();
        details.title = "Found a bug";
        details.body = "Critical bug found in module X of system Z.";
        details.labels = labels;

        IssueDetails result = given().
                contentType(ContentType.JSON).
                body(details).
                when().
                post().
                then().
                statusCode(201).extract().
                response().getBody().as(IssueDetails.class);

        issueNumber = result.number;

        Assert.assertEquals(result.title, details.title);
        Assert.assertEquals(result.user.login, user);
        Assert.assertEquals(result.labels.get(0).name, label.name);
    }

    @Test(dependsOnMethods = "SmokeTestsV3.createIssue")
    public void getIssueDetails() {
        IssueDetails result = when().
                get(String.valueOf(issueNumber)).
                then().
                statusCode(200).extract().
                response().getBody().as(IssueDetails.class);

        Assert.assertEquals(result.number, issueNumber);
        Assert.assertEquals(result.user.login, user);
    }

    @Test(dependsOnMethods = "SmokeTestsV43.createIssue")
    public void updateIssue() {
        IssueDetails update = new IssueDetails();
        update.state = "closed";
        update.title = "Found a bug";
        IssueDetails result = given().
                contentType(ContentType.JSON).
                body(update).
                when().
                patch(String.valueOf(issueNumber)).
                then().
                statusCode(200)
                .extract().
                        response().getBody().as(IssueDetails.class);

        Assert.assertEquals(result.user.login, user);
        Assert.assertEquals(result.state, "closed");
    }
}
