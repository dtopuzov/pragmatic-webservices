using System;
using System.Configuration;
using GitHubTests.GitHub.API;
using GitHubTests.GitHub.UI;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using OpenQA.Selenium;
using OpenQA.Selenium.Chrome;

namespace GitHubTests
{
    [TestClass]
    public class GitHubUITests
    {
        private readonly static string User = ConfigurationManager.AppSettings["User"];
        private readonly static string Repo = ConfigurationManager.AppSettings["Repo"];

        IWebDriver driver;
        IssuesPage issuesPage;

        [TestInitialize]
        public void TestSetup()
        {
            driver = new ChromeDriver();
            driver.Manage().Timeouts().PageLoad = TimeSpan.FromSeconds(30);
            issuesPage = new IssuesPage(driver);
        }

        [TestCleanup]
        public void Cleanup() => driver.Quit();

        [TestCategory("UITest"), TestMethod]
        public void OpenIssuesAreLessThenClosed()
        {
            int open = issuesPage.GetOpenIssuesCount();
            int closed = issuesPage.GetClosedIssuesCount();
            Assert.IsTrue(open < closed, "Open issues are more than opened!");
        }

        [TestCategory("UITest"), TestMethod]
        public void OpenIssuesCountIsCorrect()
        {
            IssuesAPI issuesAPI = new IssuesAPI();
            int openUI = issuesPage.GetOpenIssuesCount();
            int openAPI = issuesAPI.GetAllIssues(organization: User, repository: Repo, options: "state=open").Count;
            Assert.AreEqual(openUI, openAPI, "API and UI report different issues count!");
        }
    }
}
