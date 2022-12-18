package org.pragmatic.github.api;

import io.restassured.RestAssured;
import org.pragmatic.github.dto.IssueDetails;

import static io.restassured.RestAssured.*;

public class Issues {
    public Issues(String baseUrl, String repo, String user, String token) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.authentication = oauth2(token);
        RestAssured.baseURI = baseUrl;
        RestAssured.basePath = String.format("/repos/%s/%s/issues", user, repo);
    }

    public IssueDetails getIssueDetail(int number) {
        return when().
                get("{id}", number).
                then().
                statusCode(200).
                extract().
                response().getBody().as(IssueDetails.class);

    }

    public IssueDetails createIssue(IssueDetails details) {
        return given().
                contentType("application/json; charset=UTF-8").
                body(details).
                when().
                post().
                then().
                statusCode(201).extract().
                response().getBody().as(IssueDetails.class);
    }

    public IssueDetails updateIssue(int number, IssueDetails details) {
        return given().
                body(details).
                when().
                patch("{id}", number).
                then().
                statusCode(200).extract().
                response().getBody().as(IssueDetails.class);
    }
}
