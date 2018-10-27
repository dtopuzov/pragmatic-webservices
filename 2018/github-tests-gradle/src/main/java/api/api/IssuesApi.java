package api.api;

import api.objects.IssueDetails;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;

public class IssuesApi {

    private String user;
    private String pass;

    public IssuesApi(String baseUrl, String repo, String user, String pass) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.baseURI = baseUrl;
        RestAssured.basePath = String.format("/repos/%s/%s", user, repo);
        this.user = user;
        this.pass = pass;
    }

    public IssueDetails getIssueDetail(int number) {
        return given().
                auth().preemptive().
                basic(this.user, this.pass).
                when().
                get("/issues/{id}", number).
                then().
                statusCode(200).
                extract().
                response().getBody().as(IssueDetails.class);

    }

    public IssueDetails[] getIssues() {
        return given().
                auth().preemptive().
                basic(this.user, this.pass).
                param("state", "open").
                when().
                get("/issues").
                then().
                statusCode(200).extract().
                response().getBody().as(IssueDetails[].class);
    }

    public IssueDetails createIssue(IssueDetails details) {
        return given().
                auth().preemptive().
                basic(this.user, this.pass).
                contentType("application/json; charset=UTF-8").
                body(details).
                when().
                post("/issues").
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
                patch("/issues/{id}", number).
                then().
                statusCode(200).extract().
                response().getBody().as(IssueDetails.class);
    }

    public IssueDetails closeIssue(int number) {
        IssueDetails issue = new IssueDetails();
        issue.state = "closed";
        return this.updateIssue(number, issue);
    }
}
