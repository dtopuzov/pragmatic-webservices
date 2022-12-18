using GitHubTests.Api.Endpoints;
using GitHubTests.Api.Objects;
using NUnit.Framework;
using OpenQA.Selenium;
using OpenQA.Selenium.Chrome;
using WebDriverManager;
using WebDriverManager.DriverConfigs.Impl;

namespace GitHubTests
{
    [TestFixture]
    public class IntegrationTests
    {
        private IWebDriver driver;

        [OneTimeSetUp]
        public void OneTimeSetUp()
        {
            new DriverManager().SetUpDriver(new ChromeConfig());
            driver = new ChromeDriver();
        }

        [SetUp]
        public void Setup()
        {
        }

        [TearDown]
        public void TearDown()
        {
        }

        [OneTimeTearDown]
        public void OneTimeTearDown()
        {
            driver?.Quit();
        }

        [Test]
        public void CreateIssueViaAPIAndVerifyTheUI()
        {
            var issueDetails = new IssueDetails
            {
                Title = "Found a bug",
                Body = "Critical bug found!"
            };

            var result = IssuesApi.CreateIssue(issueDetails);

            var issuePageUrl = $"{Settings.WebUrl}/{Settings.User}/{Settings.Repo}/issues/{result.Number}";
            driver.Navigate().GoToUrl(issuePageUrl);

            var titleLocator = By.CssSelector("h1.gh-header-title .js-issue-title");
            var title = driver.FindElement(titleLocator).Text;

            Assert.AreEqual("Found a bug", title);
        }
    }
}
