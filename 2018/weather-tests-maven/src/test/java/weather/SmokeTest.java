package weather;

import base.WebTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import weather.api.OpenWeatherAPI;
import weather.web.GoogleHomePage;

public class SmokeTest extends WebTest {

    @Test
    public void searchForTemperature() {
        GoogleHomePage google = new GoogleHomePage(driver);
        int webTemp = google.searchForTemperature("Sofia").getTemperatureValue();
        int apiTemp = OpenWeatherAPI.getTemperature("Sofia,bg");
        Assert.assertEquals(webTemp, apiTemp, "Temperature is not correct!");
    }
}
