package github.pages;

import base.WebPage;
import github.settings.Settings;
import org.openqa.selenium.WebDriver;

public class IssueDetailsPage extends WebPage {

    public IssueDetailsPage(WebDriver driver) {
        super(driver);
    }

    public IssueDetailsPage(WebDriver driver, int issueId) {
        super(driver);
        String issueUrl = String.format("%s/%s/%s/issues/%s",
                Settings.BASE_WEB_URL,
                Settings.USER,
                Settings.REPO,
                issueId);
        driver.navigate().to(issueUrl);
    }

    public String getTitle() {
        return "Found a bug";
    }

    public String getBody() {
        return "Critical bug found in module X of system Z.";
    }
}
