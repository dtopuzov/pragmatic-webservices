package GitHub.API;

import GitHub.Objects.CreateIssue;
import GitHub.Objects.IssueDetails;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class IssuesApi {
    private static String personalToken;
    private static String baseURL;

    private static RequestSpecBuilder builder;
    private static RequestSpecification requestSpec;

    /**
     * Initialize IssuesApi object.
     *
     * @param organization GitHub organization.
     * @param repository   GitHub repository.
     */
    public IssuesApi(String organization, String repository) {
        this.personalToken = System.getenv("GITHUB_PERSONAL_TOKEN");
        this.baseURL = String.format("https://api.github.com/repos/%s/%s/issues", organization, repository);
    }

    public IssueDetails getIssue(int number) {
        ResponseBody body = given().
                auth().preemptive().oauth2(personalToken).
                when().
                get(this.baseURL + "/" + String.valueOf(number)).
                then().
                statusCode(200).
                extract().response().getBody();
        return body.as(IssueDetails.class);
    }

    public IssueDetails createIssue(CreateIssue details) {
        ResponseBody result = given().
                auth().preemptive().oauth2(personalToken).
                contentType("application/json; charset=UTF-8").
                body(details).
                when().
                post(this.baseURL).
                then().
                statusCode(201).extract().response().getBody();
        return result.as(IssueDetails.class);
    }

    public List<IssueDetails> getIssues(HashMap<String, String> params) {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            builder.addQueryParam(key, value);
        }
        requestSpec = builder.build();
        requestSpec.auth().preemptive().oauth2(personalToken);

        ResponseBody body = given().
                spec(requestSpec).
                when().
                get(this.baseURL).
                then().
                statusCode(200).
                extract().response().getBody();
        return Arrays.asList(body.as(IssueDetails[].class));
    }

    /**
     * Close all open issues in repository.
     */
    public void closeAllOpenIssues() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("state", "open");
        List<IssueDetails> issues = this.getIssues(params);
        for (IssueDetails issue : issues) {
            System.out.println("Closing issue #" + String.valueOf(issue.number));
            CreateIssue updateBody = new CreateIssue();
            updateBody.state = "closed";

            given().
                    auth().preemptive().oauth2(this.personalToken).
                    contentType("application/json; charset=UTF-8").
                    body(updateBody).
                    when().
                    patch(this.baseURL + "/" + String.valueOf(issue.number)).
                    then().
                    statusCode(200);
        }
    }
}