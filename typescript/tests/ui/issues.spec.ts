import { IssuesApi } from "../../github/api/IssuesApi";
import { Settings } from "../../github/Settings";
import { Options } from "selenium-webdriver/chrome";
import { Browser } from "../../browser/browser";
import { By } from "selenium-webdriver";

let browser: Browser;
let issueNumber: number;

beforeAll(async () => {
    const payload = {
        title: "found a bug",
        body: "Steps to reproduce",
    };

    const api = new IssuesApi(Settings.OWNER, Settings.REPO, Settings.API_TOKEN);

    const result = await api.createIssue(payload);
    expect(result.statusCode).toEqual(201);
    issueNumber = result.body["number"];

    browser = new Browser();
});

afterAll(async () => {
    await browser.close();
});

test("issues details page renders correct title", async () => {
    const url = `${Settings.WEB_URL}/${Settings.OWNER}/${Settings.REPO}/issues/${issueNumber}`;
    await browser.navigateTo(url);

    const title = await browser.find(By.css("h1.gh-header-title .js-issue-title"));
    expect(await title.getText()).toEqual("found a bug");
});
