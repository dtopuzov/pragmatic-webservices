package pages;

import base.WebPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import settings.Settings;

import java.util.List;

@SuppressWarnings("unused")
public class IssuesPage extends WebPage {

    @FindBy(id = "js-issues-search")
    private WebElement searchBox;

    @FindBy(xpath = "//*[@id=\"js-issues-toolbar\"]/div[1]/div[1]/a[2]")
    private WebElement closedIssues;

    @FindBy(xpath = "//*[@id=\"js-issues-toolbar\"]/div[1]/div[1]/a[1]")
    private WebElement openIssues;

    public IssuesPage(WebDriver driver) {
        super(driver);
        Settings settings = new Settings();
        String url = String.format("%s/%s/%s/issues",
                Settings.BASE_WEB_URL, Settings.USER, Settings.REPO);
        driver.navigate().to(url);
    }

    public int getClosedIssuesCount() {
        return Integer.parseInt(closedIssues.getText().split(" ")[0]);
    }

    public int getOpenIssuesCount() {
        return Integer.parseInt(openIssues.getText().split(" ")[0]);
    }

    public IssuesPage navigateToOpenTab() {
        openIssues.click();
        return this;
    }

    public IssuesPage navigateToClosedTab() {
        closedIssues.click();
        return this;
    }

    public IssuesPage searchFor(String text) {
        searchBox.sendKeys(text);
        searchBox.sendKeys(Keys.ENTER);
        return this;
    }

    public List<WebElement> getIssues() {
        return driver.findElements(By.xpath("//*[contains(@id, 'issue_')]/div/div[2]/a"));
    }
}
