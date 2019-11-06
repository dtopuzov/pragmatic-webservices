package github.api;

import github.objects.IssueDetails;
import github.objects.Label;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import settings.Settings;

import java.util.ArrayList;
import java.util.List;

/**
 * Refactored version of SmokeTestsV3 where:
 * 1. All calls are wrapped in class that abstract GitHub API logic.
 * 2. Base url, user and pass are read from Settings object (which read configs from properties file).
 */
public class SmokeTestsV4 {
    private Integer issueNumber;
    private IssuesApi issuesApi;

    @BeforeClass
    public void beforeIssueApiTests() {
        issuesApi = new IssuesApi(Settings.BASE_API_URL, Settings.REPO, Settings.USER, Settings.PASS);
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

        IssueDetails result = issuesApi.createIssue(details);
        Assert.assertEquals(result.title, details.title);
        Assert.assertEquals(result.user.login, Settings.USER);
        Assert.assertEquals(result.labels.get(0).name, label.name);
        issueNumber = result.number;
    }

    @Test(dependsOnMethods = "SmokeTestsV4.createIssue")
    public void getIssueDetails() {
        IssueDetails issue = issuesApi.getIssueDetail(issueNumber);
        Assert.assertEquals(issue.number, issueNumber);
        Assert.assertEquals(issue.user.login, Settings.USER);
    }

    @Test(dependsOnMethods = "SmokeTestsV4.createIssue")
    public void updateIssue() {
        IssueDetails issue = new IssueDetails();
        issue.state = "closed";
        IssueDetails result = issuesApi.updateIssue(issueNumber, issue);
        Assert.assertEquals(result.user.login, Settings.USER);
        Assert.assertEquals(result.state, "closed");
    }
}
