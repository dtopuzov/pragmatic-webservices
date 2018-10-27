using GitHubTests.GitHub.API;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System.Configuration;

namespace GitHubTests
{
    [TestClass]
    public class GitHubAPITests
    {
        private readonly static string User = ConfigurationManager.AppSettings["User"];
        private readonly static string Repo = ConfigurationManager.AppSettings["Repo"];

        static IssuesAPI issuesAPI;

        [ClassInitialize]
        public static void ClassSetup(TestContext testContext)
        {
            issuesAPI = new IssuesAPI();

            // Make sure all issues are closed before test run
            var openIssues = issuesAPI.GetAllIssues(organization: User, repository: Repo, options: "state=open");
            foreach (var issue in openIssues)
            {
                var updatedIssue = issuesAPI.CloseIssue(organization: User, repository: Repo, number: issue.number);
                Assert.AreEqual("closed", updatedIssue.state, "Failed to close issue " + issue.number.ToString());
            }
        }

        [TestInitialize]
        public void TestSetup()
        {
            // It looks we have nothing to do here.
        }

        [TestCategory("APITest"), TestMethod]
        public void CreateIssue()
        {
            CreateIssueDetails issue = new CreateIssueDetails();
            issue.title = "Issues reported with C#";
            issue.body = "This issue is logged via C# code.";
            var issueDetails = issuesAPI.CreateIssue(organization: User, repository: Repo, issue: issue);
            Assert.AreEqual("Issues reported with C#", issueDetails.title);
            Assert.AreEqual("This issue is logged via C# code.", issueDetails.body);
            Assert.IsTrue(issueDetails.number > 1, "Issue number is valid!");
        }

        [TestCategory("APITest"), TestMethod]
        public void GetAllIssues()
        {
            var issues = issuesAPI.GetAllIssues(organization: User, repository: Repo);
            Assert.IsTrue(issues.Count > 0, "Issues count is less then expected!");
        }

        [TestCategory("APITest"), TestMethod]
        public void GetAllIssueDetails()
        {
            var issueDetails = issuesAPI.GetIssueDetails(organization: User, repository: Repo, issueNumber: 1);
            Assert.AreEqual(1, issueDetails.number);
            Assert.AreEqual("First issue", issueDetails.title);
        }

        [TestCategory("APITest"), TestMethod]
        public void UpdateIssue()
        {
        }
    }
}
