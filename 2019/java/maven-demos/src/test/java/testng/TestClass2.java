package testng;


import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestClass2 extends BaseTest {

    @Test
    public void test1() {
        Assert.assertTrue(true);
    }

    @Test
    public void test2() {
    }
}
