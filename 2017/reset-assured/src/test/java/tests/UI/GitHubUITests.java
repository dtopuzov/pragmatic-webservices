package tests.UI;

import Base.UI.BaseTest;
import GitHub.API.IssuesApi;
import GitHub.Objects.IssueDetails;
import GitHub.UI.IssuesPage;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class GitHubUITests extends BaseTest {
    @Test
    public void verifyOpenAreLessThanClosed() {
        IssuesPage issues = new IssuesPage(driver);
        int open = issues.getOpenIssuesCount();
        int closed = issues.getClosedIssuesCount();
        System.out.println("Open: " + String.valueOf(open));
        System.out.println("Closed: " + String.valueOf(closed));
        assertTrue("Open issues are more than closed.", open < closed);
    }

    @Test
    public void verifyIssuesCountIsCorrect() {

        // Get open and closed count via UI
        IssuesPage issues = new IssuesPage(driver);
        int openUI = issues.getOpenIssuesCount();

        // Get open and closed count via API
        IssuesApi issuesApi = new IssuesApi("dtopuzov", "test");
        HashMap<String, String> openFilter = new HashMap<>();
        openFilter.put("state", "open");
        int openAPI = issuesApi.getIssues(openFilter).size();
        assertEquals(openUI, openAPI, "Issues count is wrong!");
    }
}