package GitHub.Tests;

import GitHub.Objects.Issue;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.basic;
import static io.restassured.RestAssured.given;

public class GitHubAPI {

    // Please set environment variables
    //
    // GITHUB_USER -> Email of your GitHub user
    // GITHUB_USER_PASS -> Your github password
    // PERSONAL_TOKEN -> Your github personal token.
    //
    // See: https://help.github.com/articles/creating-an-access-token-for-command-line-use/
    //
    // Notes: You must be admin of the repo in order to close issues!

    private static String user = System.getenv("GITHUB_USER");
    private static String password = System.getenv("GITHUB_USER_PASS");
    private static String personalToken = System.getenv("PERSONAL_TOKEN");

    private static String baseURL = "https://api.github.com";
    private static String testRepo = "dtopuzov/test";

    @BeforeClass
    public static void setUp() {
        // This will setup basic authentication for all the tests
        RestAssured.authentication = basic("username", "password");

        // This will set base URL for all the tests
        RestAssured.baseURI = String.format("%s/repos/%s/issues", baseURL, testRepo);

        // We want all the details for failed tests
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void CreateIssueWithBasicAuthentication() {
        Issue issue = new Issue();
        issue.title = "Title1";
        issue.body = "This is body.";

        given().
                auth().
                preemptive().   // See https://github.com/rest-assured/rest-assured/issues/356#issuecomment-123187692
                basic(user, password).  // Demo of basic authentication
                contentType("application/json; charset=UTF-8").
                body(issue).
                when().
                post().
                then().
                statusCode(201);
    }

    @Test
    public void CreateIssueWithTokenAuthentication() {
        Issue issue = new Issue();
        issue.title = "Title2";
        issue.body = "This is body.";

        given().
                auth().
                preemptive().
                oauth2(personalToken).  // Oauth2 authentication sample
                contentType("application/json; charset=UTF-8").
                body(issue).
                when().
                post().
                then().
                statusCode(201);
    }

    @Test
    public void CloseAllOpenIssues() {
        // Get ids of all open issues
        List<Integer> issueIds =
                given().
                        param("state", "open").
                        when().
                        get().
                        then().
                        statusCode(200).
                        extract().
                        response().path("number");

        // Close all open issues
        for (int id : issueIds) {
            System.out.println("Closing issue #" + String.valueOf(id));

            Issue issue = new Issue();
            issue.state = "closed";

            given().
                    auth().
                    preemptive(). // See https://github.com/rest-assured/rest-assured/issues/356#issuecomment-123187692
                    basic(user, password).
                    contentType("application/json; charset=UTF-8").
                    //param("state", "closed").
                            body(issue).
                    when().
                    patch("/" + String.valueOf(id)).
                    then().
                    statusCode(200);
        }
    }

    @Test
    public void VerifyClosedIssuesAreMoreThanOpened() {
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
