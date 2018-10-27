package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

public class WebTest {

    protected static WebDriver driver;

    @BeforeClass
    public void beforeClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    public void beforeMethod() {
        if (driver == null) {
            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        }
    }

    @AfterMethod
    public void afterMethod() {

    }

    @AfterClass
    public static void afterClass() {
        if (driver != null) {
            driver.quit();
        }
    }
}
