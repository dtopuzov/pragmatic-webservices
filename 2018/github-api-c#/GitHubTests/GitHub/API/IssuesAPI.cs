using System;
using System.Collections.Generic;
using System.Configuration;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using GitHubTests.Helpers;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Newtonsoft.Json;

namespace GitHubTests.GitHub.API
{
    class IssuesAPI
    {
        private readonly string GitHubAPIBaseURL = ConfigurationManager.AppSettings["GitHubApiUrl"];

        private string ConvertToString(Object details)
        {
            return JsonConvert.SerializeObject(details,
                new JsonSerializerSettings
                {
                    NullValueHandling = NullValueHandling.Ignore
                });
        }

        public List<IssueDetails> GetAllIssues(string organization, string repository, string options = null)
        {
            var uri = string.Format("{0}/repos/{1}/{2}/issues", GitHubAPIBaseURL, organization, repository);
            if (options != null)
            {
                uri = uri + "?" + options;
            }
            var message = new HttpRequestMessage(HttpMethod.Get, new Uri(uri));
            var response = HttpHelper.SendRequest(message);
            var content = response.Content.ReadAsStringAsync().Result;
            return JsonConvert.DeserializeObject<List<IssueDetails>>(content);
        }

        public IssueDetails GetIssueDetails(string organization, string repository, int issueNumber)
        {
            var uri = string.Format("{0}/repos/{1}/{2}/issues/{3}", GitHubAPIBaseURL, organization, repository, issueNumber.ToString());
            var message = new HttpRequestMessage(HttpMethod.Get, new Uri(uri));
            var response = HttpHelper.SendRequest(message);
            var content = response.Content.ReadAsStringAsync().Result;
            return JsonConvert.DeserializeObject<IssueDetails>(content);
        }

        public IssueDetails CreateIssue(string organization, string repository, CreateIssueDetails issue)
        {
            var uri = string.Format("{0}/repos/{1}/{2}/issues", GitHubAPIBaseURL, organization, repository);
            var message = new HttpRequestMessage(HttpMethod.Post, new Uri(uri));
            var content = this.ConvertToString(issue);
            message.Content = new StringContent(content, Encoding.UTF8, "application/json");
            var response = HttpHelper.SendRequest(message);
            Assert.AreEqual(System.Net.HttpStatusCode.Created, response.StatusCode);
            var result = response.Content.ReadAsStringAsync().Result;
            return JsonConvert.DeserializeObject<IssueDetails>(result);
        }

        public IssueDetails UpdateIssue(string organization, string repository, int number, IssueDetails details)
        {
            var uri = string.Format("{0}/repos/{1}/{2}/issues/{3}", GitHubAPIBaseURL, organization, repository, number.ToString());
            var message = new HttpRequestMessage(new HttpMethod("PATCH"), new Uri(uri));
            var content = this.ConvertToString(details);
            message.Content = new StringContent(content, Encoding.UTF8, "application/json");
            var response = HttpHelper.SendRequest(message);
            var result = response.Content.ReadAsStringAsync().Result;
            return JsonConvert.DeserializeObject<IssueDetails>(result);
        }

        public IssueDetails CloseIssue(string organization, string repository, int number)
        {
            var details = new IssueDetails();
            details.state = "closed";
            return this.UpdateIssue(organization: organization, repository: repository, number: number, details: details);
        }
    }
}
