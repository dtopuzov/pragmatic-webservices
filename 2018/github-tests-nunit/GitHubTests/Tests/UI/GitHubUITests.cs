using GitHubTests.Base;
using GitHubTests.GitHub.API;
using GitHubTests.GitHub.UI;
using NUnit.Framework;
using System.Configuration;

namespace GitHubTests
{
    [TestFixture]
    public class GitHubUITests : WebTest
    {
        private readonly static string User = ConfigurationManager.AppSettings["User"];
        private readonly static string Repo = ConfigurationManager.AppSettings["Repo"];

        private IssuesPage issuesPage;

        [SetUp]
        public void LoginTestInit() => issuesPage = new IssuesPage(Driver);

        [Test]
        public void OpenIssuesAreLessThenClosed()
        {
            int open = issuesPage.GetOpenIssuesCount();
            int closed = issuesPage.GetClosedIssuesCount();
            Assert.IsTrue(open < closed, "Open issues are more than opened!");
        }

        [Test]
        public void OpenIssuesCountIsCorrect()
        {
            IssuesAPI issuesAPI = new IssuesAPI();
            int openUI = issuesPage.GetOpenIssuesCount();
            int openAPI = issuesAPI.GetAllIssues(organization: User, repository: Repo, options: "state=open").Count;
            Assert.AreEqual(openUI, openAPI, "API and UI report different issues count!");
        }
    }
}
