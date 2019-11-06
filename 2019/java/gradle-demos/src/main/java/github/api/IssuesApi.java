package github.api;

import github.objects.IssueDetails;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.preemptive;

public class IssuesApi {

    private String user;
    private String pass;

    public IssuesApi(String baseUrl, String repo, String user, String pass) {
        this.user = user;
        this.pass = pass;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.authentication = preemptive().basic(this.user, this.pass);
        RestAssured.baseURI = baseUrl;
        RestAssured.basePath = String.format("/repos/%s/%s/issues", user, repo);
    }

    public IssueDetails getIssueDetail(int number) {
        return given().
                auth().preemptive().
                basic(this.user, this.pass).
                when().
                get("{id}", number).
                then().
                statusCode(200).
                extract().
                response().getBody().as(IssueDetails.class);

    }

    public IssueDetails createIssue(IssueDetails details) {
        return given().
                auth().preemptive().
                basic(this.user, this.pass).
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
                auth().preemptive().
                basic(this.user, this.pass).
                body(details).
                when().
                patch("{id}", number).
                then().
                statusCode(200).extract().
                response().getBody().as(IssueDetails.class);
    }
}
