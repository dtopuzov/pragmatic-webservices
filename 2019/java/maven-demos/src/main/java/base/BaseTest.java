package base;

import org.testng.annotations.AfterMethod;

public class BaseTest {
    @AfterMethod
    public void afterMethod() {
        System.out.println("Take screenshot.");
    }
}
