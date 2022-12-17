package testng;


import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestClass1 extends BaseTest {

    @Test
    public void test1() {
        Assert.assertTrue(false);
    }

    @Test
    public void test2() {
    }
}
