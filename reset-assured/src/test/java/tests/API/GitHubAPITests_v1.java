package tests.API;

import GitHub.API.IssuesApi;
import GitHub.Objects.CreateIssue;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GitHubAPITests_v1 {

    private static String personalToken = System.getenv("PERSONAL_TOKEN");
    private static String baseURL = "https://api.github.com";
    private static String organization = "dtopuzov";
    private static String repo = "test";

    private static IssuesApi issuesApi;

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = String.format("%s/repos/%s/%s/issues", baseURL, organization, repo);
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        issuesApi = new IssuesApi(organization, repo);
        issuesApi.closeAllOpenIssues();
    }

    @Test
    public void createIssue() {
        CreateIssue issue = new CreateIssue();
        issue.title = "Title1";
        issue.body = "This is body.";

        given().
                auth().preemptive().oauth2(personalToken).
                contentType("application/json; charset=UTF-8").
                body(issue).
                when().
                post().
                then().
                statusCode(201);
    }

    @Test
    public void verifyClosedIssuesAreMoreThanOpened() {
        int openIssues =
                given().
                        param("state", "open").
                        when().
                        get().
                        then().
                        statusCode(200).
                        extract().jsonPath().getList("$").size();

        int closedIssues =
                given().
                        param("state", "closed").
                        when().
                        get().
                        then().
                        statusCode(200).
                        extract().jsonPath().getList("$").size();

        System.out.println("Open: " + String.valueOf(openIssues));
        System.out.println("Closed: " + String.valueOf(closedIssues));
        Assert.assertTrue(openIssues < closedIssues, "Open issues are more than closed.");
    }
}
