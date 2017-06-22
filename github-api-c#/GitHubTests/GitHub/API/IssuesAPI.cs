using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using GitHubTests.Helpers;
using Newtonsoft.Json;

namespace GitHubTests.GitHub.API
{
    class IssuesAPI
    {
        private static string GitHubAPIBaseURL = "https://api.github.com";

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
            var content = JsonConvert.SerializeObject(issue,
                            new JsonSerializerSettings
                            {
                                NullValueHandling = NullValueHandling.Ignore
                            });
            message.Headers.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
            message.Content = new StringContent(content, Encoding.UTF8, "application/json");
            var token = Environment.GetEnvironmentVariable("GITHUB_TOKEN");
            message.Headers.Authorization = new AuthenticationHeaderValue("Token", token);
            var response = HttpHelper.SendRequest(message);
            var result = response.Content.ReadAsStringAsync().Result;
            return JsonConvert.DeserializeObject<IssueDetails>(result);
        }
    }
}
