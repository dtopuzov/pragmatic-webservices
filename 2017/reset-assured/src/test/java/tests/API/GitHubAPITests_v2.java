package tests.API;

import GitHub.API.IssuesApi;
import GitHub.Objects.CreateIssue;
import GitHub.Objects.IssueDetails;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

public class GitHubAPITests_v2 {
    private static String organization = "dtopuzov";
    private static String repo = "test";

    private static IssuesApi issuesApi;
    private static IssueDetails existingIssue;

    @BeforeClass
    public static void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        issuesApi = new IssuesApi(organization, repo);
        issuesApi.closeAllOpenIssues();
    }

    @Test
    public void createIssue() {
        String title = "Title of issue";
        String body = "This issues is logged by rest-assured tests!";
        CreateIssue details = new CreateIssue();
        details.title = title;
        details.body = body;
        existingIssue = issuesApi.createIssue(details);
        Assert.assertEquals(existingIssue.title, title, "Title of created issue is not correct!");
        Assert.assertEquals(existingIssue.body, body, "Body of created issue is not correct!");
        Assert.assertTrue(existingIssue.number > 0, "Number of create issues is not correct!");
    }

    @Test(dependsOnMethods = "createIssue")
    public void getIssueDetails() {
        IssueDetails issue = issuesApi.getIssue(existingIssue.number);
        Assert.assertEquals(existingIssue.title, issue.title, "Get issue details returns wrong title!!");
        Assert.assertEquals(existingIssue.body, issue.body, "Get issue details returns wrong body!");
        Assert.assertEquals(existingIssue.number, issue.number, "Get issue details returns wrong number!");
    }

    @Test(dependsOnMethods = "createIssue")
    public void getAllOpenIssues() {
        HashMap<String, String> openFilter = new HashMap<>();
        openFilter.put("state", "open");
        List<IssueDetails> issues = issuesApi.getIssues(openFilter);
        Assert.assertTrue(issues.size() >= 1, "No issues found!");
    }

    @Test
    public void verifyClosedIssuesAreMoreThanOpened() {
        HashMap<String, String> openFilter = new HashMap<>();
        HashMap<String, String> closeFilter = new HashMap<>();
        openFilter.put("state", "open");
        closeFilter.put("state", "closed");
        int openIssues = issuesApi.getIssues(openFilter).size();
        int closedIssues = issuesApi.getIssues(closeFilter).size();
        System.out.println("Open: " + String.valueOf(openIssues));
        System.out.println("Closed: " + String.valueOf(closedIssues));
        Assert.assertTrue(openIssues < closedIssues, "Open issues are more than closed.");
    }
}
