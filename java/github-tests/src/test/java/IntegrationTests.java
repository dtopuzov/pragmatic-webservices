import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.pragmatic.browser.Browser;
import org.pragmatic.github.api.Issues;
import org.pragmatic.github.dto.IssueDetails;
import org.pragmatic.github.settings.Settings;
import org.pragmatic.github.ui.IssueDetailsPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTests {
    private static Browser browser;

    @BeforeAll
    static void beforeAll() {
        browser = new Browser();
    }

    @AfterAll
    static void afterAll() {
        browser.close();
    }

    @Test
    void testIssueDetailsPage() {
        // Init IssuesApi
        Issues issuesApi = new Issues(Settings.BASE_API_URL, Settings.REPO, Settings.USER, Settings.TOKEN);

        // Define issue details
        IssueDetails details = new IssueDetails();
        details.title = "Found a bug";
        details.body = "Critical bug found in module X of system Z.";

        // Create issue via API
        IssueDetails result = issuesApi.createIssue(details);
        assertEquals(result.title, details.title);
        assertEquals(result.user.login, Settings.USER);
        int issueNumber = result.number;

        // Open issue details web page and verify it looks OK
        IssueDetailsPage issueDetailsPage = new IssueDetailsPage(browser, issueNumber);
        assertEquals(issueDetailsPage.getTitle(), result.title);
        assertEquals(issueDetailsPage.getBody(), result.body);
    }
}
