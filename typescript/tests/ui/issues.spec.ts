require("chromedriver");

import { IssuesApi } from "../../github/api/IssuesApi";
import { Builder, By, WebDriver } from "selenium-webdriver";
import { Settings } from "../../github/Settings";

let driver: WebDriver;
let issueNumber: number;

beforeAll(async () => {
    const payload = {
        "title": "found a bug",
        "body": "Steps to reproduce"
    };

    const api = new IssuesApi("ws-test-user", "test");
    const result = await api.createIssue(payload);
    expect(result.statusCode).toEqual(201);
    issueNumber = result.body["number"];

    driver = await new Builder().forBrowser("chrome").build();
});

afterAll(async () => {
    await driver.quit();
});

test("get issue details", async () => {
    await driver.get(`${Settings.WEB_URL}/ws-test-user/test/issues/${issueNumber}`);
    const title = await driver.findElement(By.css("h1.gh-header-title .js-issue-title"));
    expect(await title.getText()).toEqual("found a bug");
});
