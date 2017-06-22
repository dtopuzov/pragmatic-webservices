using GitHubTests.GitHub.API;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace GitHubTests
{
    [TestClass]
    public class GitHubAPITests
    {
        IssuesAPI issuesAPI;

        [TestInitialize]
        public void TestSetup()
        {
            this.issuesAPI = new IssuesAPI();
        }

        [TestMethod]
        public void GetAllIssues()
        {
            var issues = this.issuesAPI.GetAllIssues(organization:"dtopuzov", repository:"test");
            Assert.IsTrue(issues.Count > 1, "Issues count is less then expected!");
        }

        [TestMethod]
        public void GetAllIssueDetails()
        {
            var issueDetails = this.issuesAPI.GetIssueDetails(organization: "dtopuzov", repository: "test", issueNumber:1);
            Assert.AreEqual(1, issueDetails.number);
            Assert.AreEqual("Closed issue", issueDetails.title);
        }

        [TestMethod]
        public void CreateIssue()
        {
            CreateIssueDetails issue = new CreateIssueDetails();
            issue.title = "Issues reported with C#";
            issue.body = "This issue is logged via C# code.";
            var issueDetails = this.issuesAPI.CreateIssue(organization: "dtopuzov", repository: "test", issue: issue);
            Assert.AreEqual("Issues reported with C#", issueDetails.title);
            Assert.AreEqual("This issue is logged via C# code.", issueDetails.body);
        }

        [TestMethod]
        public void UpdateIssue()
        {
        }
    }
}
