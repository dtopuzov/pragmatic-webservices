package GitHub.API;

import GitHub.Objects.CreateIssue;

import java.util.List;

import static io.restassured.RestAssured.given;

public class IssuesApi {
    private String personalToken;
    private String baseURL;

    /**
     * Initialize IssuesApi object.
     *
     * @param organization GitHub organization.
     * @param repository   GitHub repository.
     */
    public IssuesApi(String organization, String repository) {
        this.personalToken = System.getenv("PERSONAL_TOKEN");
        this.baseURL = String.format("https://api.github.com/repos/%s/%s/issues", organization, repository);
    }

    /**
     * Get all open issues in repo
     *
     * @return List of numbers of open issues.
     */
    public List<Integer> getAllIssues() {
        List<Integer> issueIds =
                given().
                        param("state", "open").
                        when().
                        get(this.baseURL).
                        then().
                        statusCode(200).
                        extract().
                        response().path("number");
        return issueIds;
    }

    /**
     * Close all open issues in repository.
     */
    public void closeAllOpenIssues() {
        for (int id : this.getAllIssues()) {
            System.out.println("Closing issue #" + String.valueOf(id));
            CreateIssue issue = new CreateIssue();
            issue.state = "closed";

            given().
                    auth().preemptive().oauth2(this.personalToken).
                    contentType("application/json; charset=UTF-8").
                    body(issue).
                    when().
                    patch(this.baseURL + "/" + String.valueOf(id)).
                    then().
                    statusCode(200);
        }
    }
}