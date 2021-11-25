import { IssuesApi } from '../../github/api/IssuesApi';

let api: IssuesApi;
let issueNumber: number;

beforeEach(() => {
    api = new IssuesApi("ws-test-user", "test");
});

test('create issue', async () => {
    const payload = {
        "title": "found a bug",
        "body": "Steps to reproduce"
    };
    
    const result = await api.createIssue(payload);
    expect(result.statusCode).toEqual(201);

    issueNumber = result.body["number"];
});

test('get issue', async () => {
    const result = await api.getIssueDetails(issueNumber);
    expect(result.statusCode).toEqual(200);
    expect(result.body["title"]).toEqual("found a bug");
});
