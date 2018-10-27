package GitHub.UI;

import Base.UI.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

public class IssuesPage extends BasePage {

    @FindBy(xpath = "//*[@id=\"js-issues-toolbar\"]/div[1]/div[1]/a[2]")
    @CacheLookup
    private WebElement closedIssues;

    @FindBy(xpath = "//*[@id=\"js-issues-toolbar\"]/div[1]/div[1]/a[1]")
    @CacheLookup
    private WebElement openIssues;

    public IssuesPage(WebDriver driver) {
        super(driver);
        driver.navigate().to("https://github.com/dtopuzov/test/issues");
    }

    public int getClosedIssuesCount() {
        return Integer.parseInt(closedIssues.getText().split(" ")[0]);
    }

    public int getOpenIssuesCount() {
        return Integer.parseInt(openIssues.getText().split(" ")[0]);
    }
}