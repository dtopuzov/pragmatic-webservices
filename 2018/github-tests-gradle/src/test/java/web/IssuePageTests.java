package web;

import api.api.IssuesApi;
import api.objects.IssueDetails;
import base.WebTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.IssuesPage;
import settings.Settings;

/**
 * Web tests for Issues page.
 * Prepare test data with API calls.
 * Also use APIs to get actual data and compare it equals data in web.
 */
public class IssuePageTests extends WebTest {

    private static IssuesApi issuesApi;
    private static IssuesPage issuesPage;

    @BeforeClass
    public void beforeIssuePageTestsClass() {

        // Initialize GitHub Issues api object.
        issuesApi = new IssuesApi(Settings.BASE_API_URL, Settings.REPO, Settings.USER, Settings.PASS);

        // Close all open issues via api.
        IssueDetails[] allIssues = issuesApi.getIssues();
        for (IssueDetails issue : allIssues) {
            issuesApi.closeIssue(issue.number);
        }

        // Create test data
        IssueDetails open1 = new IssueDetails();
        open1.title = "openIssue1";
        open1.body = "Body of openIssue1";
        issuesApi.createIssue(open1);

        IssueDetails open2 = new IssueDetails();
        open2.title = "openIssue2";
        open2.body = "Body of openIssue2";
        issuesApi.createIssue(open2);
    }

    @BeforeMethod
    public void beforeIssuePageTests() {
        issuesPage = new IssuesPage(driver);
    }

    @Test
    public void openIssuesTabShowsCorrectData() {
        int webOpenIssues = issuesPage.getIssues().size();
        int apiOpenIssues = issuesApi.getIssues().length;
        Assert.assertEquals(webOpenIssues, apiOpenIssues, "Open issues count on web page is not correct.");
    }

    @Test
    public void searchForIssue() throws InterruptedException {
        issuesPage.searchFor("openIssue1");
        Thread.sleep(1000);
        int issuesCount = issuesPage.getIssues().size();
        Assert.assertEquals(issuesCount, 1, "Filter issues by name do not work correct.");
    }
}
