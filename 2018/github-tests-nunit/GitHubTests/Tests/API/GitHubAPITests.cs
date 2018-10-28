using GitHubTests.GitHub.API;
using NUnit.Framework;
using System.Configuration;
using static GitHubTests.GitHub.API.Objects;

namespace GitHubTests
{
    [TestFixture]
    public class GitHubAPITests
    {
        private readonly static string User = ConfigurationManager.AppSettings["User"];
        private readonly static string Repo = ConfigurationManager.AppSettings["Repo"];

        static IssuesAPI issuesAPI;

        [OneTimeSetUp]
        public static void ClassSetup()
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

        [SetUp]
        public void TestSetup()
        {
            // It looks we have nothing to do here.
        }

        [Test]
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

        [Test]
        public void GetAllIssues()
        {
            var issues = issuesAPI.GetAllIssues(organization: User, repository: Repo);
            Assert.IsTrue(issues.Count > 0, "Issues count is less then expected!");
        }

        [Test]
        public void GetAllIssueDetails()
        {
            var issueDetails = issuesAPI.GetIssueDetails(organization: User, repository: Repo, issueNumber: 1);
            Assert.AreEqual(1, issueDetails.number);
            Assert.AreEqual("First issue", issueDetails.title);
        }

        [Test]
        public void UpdateIssue()
        {
        }
    }
}
