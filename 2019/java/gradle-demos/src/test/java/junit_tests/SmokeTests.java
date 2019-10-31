package junit_tests;

import org.junit.Assert;
import org.junit.Test;

public class SmokeTests {
    @Test
    public void smokeTests1() {
        String str1 = "ok";
        String str2 = "ok";
        Assert.assertEquals("Strings are not equal.", str1, str2);
    }

    @Test
    public void smokeTests2() {
        String str1 = "ok";
        String str2 = "ko";
        Assert.assertEquals("Strings are not equal.", str1, str2);
    }
}
