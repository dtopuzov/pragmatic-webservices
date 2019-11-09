package github.web;

import base.WebTest;
import github.api.IssuesApi;
import github.objects.IssueDetails;
import github.pages.IssueDetailsPage;
import github.settings.Settings;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SmokeTests extends WebTest {

    @Test
    public void issueDetailsPage() {
        // Init IssuesApi
        IssuesApi issuesApi = new IssuesApi(Settings.BASE_API_URL, Settings.REPO, Settings.USER, Settings.PASS);

        // Define issue details
        IssueDetails details = new IssueDetails();
        details.title = "Found a bug";
        details.body = "Critical bug found in module X of system Z.";

        // Create issue via API
        IssueDetails result = issuesApi.createIssue(details);
        Assert.assertEquals(result.title, details.title);
        Assert.assertEquals(result.user.login, Settings.USER);
        int issueNumber = result.number;

        // Open issue details web page and verify it looks OK
        IssueDetailsPage issueDetailsPage = new IssueDetailsPage(driver, issueNumber);
        Assert.assertEquals(issueDetailsPage.getTitle(), result.title);
        Assert.assertEquals(issueDetailsPage.getBody(), result.body);
    }
}
