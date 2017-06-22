using OpenQA.Selenium;
using OpenQA.Selenium.Support.PageObjects;

namespace GitHubTests.GitHub.UI
{
    class IssuesPage
    {
        private string url = "https://github.com/dtopuzov/test/issues";
        private IWebDriver _driver;

        [FindsBy(How = How.XPath, Using = "//*[@id=\"js-issues-toolbar\"]/div[1]/div[1]/a[2]")]
        private IWebElement closedIssues { get; set; }

        [FindsBy(How = How.XPath, Using = "//*[@id=\"js-issues-toolbar\"]/div[1]/div[1]/a[1]")]
        private IWebElement openIssues { get; set; }

        public IssuesPage(IWebDriver driver)
        {
            this._driver = driver;
            PageFactory.InitElements(_driver, this);
        }

        public void NavigateTo()
        {
            this._driver.Navigate().GoToUrl(url);
        }

        public int GetClosedIssuesCount()
        {
            return int.Parse(this.closedIssues.Text.Split(' ')[0]);
        }

        public int GetOpenIssuesCount()
        {
            return int.Parse(this.openIssues.Text.Split(' ')[0]);
        }
    }
}
