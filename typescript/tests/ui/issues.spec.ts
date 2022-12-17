import { IssuesApi } from "../../github/api/IssuesApi";
import { Browser, Builder, By, WebDriver } from "selenium-webdriver";
import { Settings } from "../../github/Settings";
import { Options } from "selenium-webdriver/chrome";

let driver: WebDriver;
let issueNumber: number;

beforeAll(async () => {
    const payload = {
        title: "found a bug",
        body: "Steps to reproduce",
    };

    const api = new IssuesApi(
        Settings.OWNER,
        Settings.REPO,
        Settings.API_TOKEN
    );

    const result = await api.createIssue(payload);
    expect(result.statusCode).toEqual(201);
    issueNumber = result.body["number"];

    const options = new Options();
    options.headless();
    options.addArguments('--window-size=1366,768');

    driver = new Builder()
        .forBrowser(Browser.CHROME)
        .setChromeOptions(options)
        .build();
});

afterAll(async () => {
    await driver.quit();
});

test("get issue details", async () => {
    await driver.get(
        `${Settings.WEB_URL}/${Settings.OWNER}/${Settings.REPO}/issues/${issueNumber}`
    );
    const title = await driver.findElement(
        By.css("h1.gh-header-title .js-issue-title")
    );
    expect(await title.getText()).toEqual("found a bug");
});
