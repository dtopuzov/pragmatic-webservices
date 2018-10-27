package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class WebPage {
    protected WebDriver driver;

    public WebPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}