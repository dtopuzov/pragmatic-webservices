import { IssuesApi } from "../../github/api/IssuesApi";
import { Settings } from "../../github/Settings";

const api = new IssuesApi(Settings.OWNER, Settings.REPO, Settings.API_TOKEN);
let issueNumber: number;

test("create issue", async () => {
    const payload = {
        title: "found a bug",
        body: "Steps to reproduce",
    };

    const result = await api.createIssue(payload);
    expect(result.statusCode).toEqual(201);

    issueNumber = result.body["number"];
});

test("get issue", async () => {
    const result = await api.getIssueDetails(issueNumber);
    expect(result.statusCode).toEqual(200);
    expect(result.body["title"]).toEqual("found a bug");
});
