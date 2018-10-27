package api;

import api.api.IssuesApi;
import api.objects.IssueDetails;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import settings.Settings;

/**
 * Tests for issues api.
 * Improved version, GitHub api calls moved in separate class.
 */
public class IssueApiTestsV3 {

    private int issueId;
    private IssuesApi issuesApi;

    @BeforeClass
    public void beforeIssueApiTests() {
        issuesApi = new IssuesApi(Settings.BASE_API_URL, Settings.REPO, Settings.USER, Settings.PASS);
    }

    @Test(dependsOnMethods = "createIssue")
    public void getIssueDetails() {
        IssueDetails issue = issuesApi.getIssueDetail(issueId);
        Assert.assertEquals(issue.title, "Found a bug");
        Assert.assertEquals(issue.user.login, Settings.USER);
    }

    @Test(dependsOnMethods = "createIssue")
    public void getOpenIssues() {
        IssueDetails[] issues = issuesApi.getIssues();

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
        IssueDetails result = issuesApi.createIssue(issue);
        Assert.assertEquals(result.user.login, Settings.USER);
        issueId = result.number;
    }

    @Test(dependsOnMethods = "createIssue")
    public void updateIssue() {
        IssueDetails issue = new IssueDetails();
        issue.state = "closed";
        IssueDetails result = issuesApi.updateIssue(issueId, issue);
        Assert.assertEquals(result.state, "closed");
        Assert.assertEquals(result.user.login, Settings.USER);
    }
}
