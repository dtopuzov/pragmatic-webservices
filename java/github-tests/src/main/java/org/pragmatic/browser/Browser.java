package org.pragmatic.browser;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Browser {
    private WebDriver driver;

    public Browser() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        chromeOptions.addArguments("--window-size=1366,768");
        driver = new ChromeDriver(chromeOptions);
    }

    public void close() {
        driver.quit();
    }

    public void navigateTo(String url) {
        driver.navigate().to(url);
    }

    public WebElement find(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(timeout));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public WebElement find(By locator) {
        return find(locator, 10000);
    }
}
