package org.pragmatic.github.ui;

import org.openqa.selenium.By;
import org.pragmatic.browser.Browser;
import org.pragmatic.github.settings.Settings;

public class IssueDetailsPage {
    private final Browser browser;

    public IssueDetailsPage(Browser browser, int issueNumber) {
        this.browser = browser;

        String issueUrl = String.format("%s/%s/%s/issues/%s",
                Settings.BASE_WEB_URL,
                Settings.USER,
                Settings.REPO,
                issueNumber);

        this.browser.navigateTo(issueUrl);
    }

    public String getTitle() {
        By titleLocator = By.cssSelector("h1.gh-header-title .js-issue-title");
        return browser.find(titleLocator).getText();
    }

    public String getBody() {
        By bodyLocator = By.cssSelector(".js-command-palette-issue-body .edit-comment-hide .markdown-body");
        return browser.find(bodyLocator).getText();
    }
}
