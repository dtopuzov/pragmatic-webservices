using OpenQA.Selenium;
using OpenQA.Selenium.Support.PageObjects;
using System;
using System.Configuration;

namespace GitHubTests.GitHub.UI
{
    class IssuesPage
    {
        private IWebDriver _driver;

        [FindsBy(How = How.XPath, Using = "//*[@id=\"js-issues-toolbar\"]/div[1]/div[1]/a[2]")]
        private IWebElement closedIssues { get; set; }

        [FindsBy(How = How.XPath, Using = "//*[@id=\"js-issues-toolbar\"]/div[1]/div[1]/a[1]")]
        private IWebElement openIssues { get; set; }

        public IssuesPage(IWebDriver driver)
        {
            _driver = driver;
            _driver.Manage().Timeouts().ImplicitWait = TimeSpan.FromSeconds(10);
            PageFactory.InitElements(_driver, this);
            var url = string.Format("{0}/{1}/{2}/issues",
                ConfigurationManager.AppSettings["GitHubWebUrl"],
                ConfigurationManager.AppSettings["User"],
                ConfigurationManager.AppSettings["Repo"]);
            _driver.Navigate().GoToUrl(url);
        }

        public int GetClosedIssuesCount() => int.Parse(closedIssues.Text.Split(' ')[0]);

        public int GetOpenIssuesCount() => int.Parse(openIssues.Text.Split(' ')[0]);
    }
}
