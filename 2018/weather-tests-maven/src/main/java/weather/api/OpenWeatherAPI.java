package weather.api;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;

public class OpenWeatherAPI {

    public static int getTemperature(String location) {

        // If this key do not work, please register at https://openweathermap.org/ and get new.
        String apiKey = "94dbcaa25f947f5c7abfc0faa6f3dcca";

        // Set base url for all RestAssured requests in this class
        RestAssured.baseURI = "http://api.openweathermap.org/data/2.5/weather";

        // Enable logging on request/response failure
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        // Get temperature in Sofia via rest api

        // Actual request is:
        // http://api.openweathermap.org/data/2.5/weather?q=<location>&appid=<api_key>&units=metric
        // queryParam are used to specify query params like appid and units
        return given().
                queryParam("q", location).
                queryParam("units", "metric").
                queryParam("appid", apiKey).
                when().
                get().
                then().
                statusCode(200). // Assert Status code is 200
                extract().
                path("main.temp"); // Extract temperature from response body
    }
}