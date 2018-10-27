package weather.web;

import base.WebPage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GoogleHomePage extends WebPage {

    @FindBy(id = "lst-ib")
    private WebElement searchBox;

    @FindBy(id = "wob_tm")
    private WebElement temperature;

    public GoogleHomePage(WebDriver driver) {
        super(driver);
        driver.navigate().to("https://google.com/ncr");
    }

    public GoogleHomePage searchForTemperature(String location) {
        this.searchBox.sendKeys("temperature " + location);
        this.searchBox.sendKeys(Keys.ENTER);
        return this;
    }

    public int getTemperatureValue() {
        return Integer.valueOf(temperature.getText());
    }
}
