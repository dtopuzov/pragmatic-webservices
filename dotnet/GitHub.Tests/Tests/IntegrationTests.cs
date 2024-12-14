using GitHubTests.Api.Endpoints;
using GitHubTests.Api.Objects;
using NUnit.Framework;
using OpenQA.Selenium;
using OpenQA.Selenium.Chrome;

namespace GitHubTests
{
    [TestFixture]
    public class IntegrationTests
    {
        private IWebDriver driver;

        [OneTimeSetUp]
        public void OneTimeSetUp()
        {
            var options = new ChromeOptions();
            options.AddArgument("--headless=new");
            options.AddArgument("--window-size=1366,768");

            driver = new ChromeDriver(options);
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

            Assert.That(title, Is.EqualTo("Found a bug"));
        }
    }
}
