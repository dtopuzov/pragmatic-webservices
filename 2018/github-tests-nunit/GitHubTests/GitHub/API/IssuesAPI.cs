using GitHubTests.Helpers;
using Newtonsoft.Json;
using RestSharp;
using RestSharp.Authenticators;
using System;
using System.Collections.Generic;
using System.Configuration;
using static GitHubTests.GitHub.API.Objects;

namespace GitHubTests.GitHub.API
{
    class IssuesAPI
    {
        private readonly string baseUrl = ConfigurationManager.AppSettings["GitHubApiUrl"];
        private readonly string user = ConfigurationManager.AppSettings["User"];
        private readonly string password = ConfigurationManager.AppSettings["Password"];

        private RestClient client;

        public IssuesAPI()
        {
            client = new RestClient
            {
                BaseUrl = new Uri(baseUrl),
                UserAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36",
                Authenticator = new HttpBasicAuthenticator(user, password)
            };
        }

        public IssueDetails GetIssueDetails(string organization, string repository, int issueNumber)
        {
            var uri = string.Format("{0}/repos/{1}/{2}/issues/{3}", baseUrl, organization, repository, issueNumber.ToString());
            var request = new RestRequest(uri, Method.GET);
            return client.Execute<IssueDetails>(request).Data;
        }

        public List<IssueDetails> GetAllIssues(string organization, string repository, string options = null)
        {
            var uri = string.Format("{0}/repos/{1}/{2}/issues?state=open", baseUrl, organization, repository);
            var request = new RestRequest(uri, Method.GET);
            var issues = client.Execute<List<IssueDetails>>(request);
            return client.Execute<List<IssueDetails>>(request).Data;
        }

        public IssueDetails CreateIssue(string organization, string repository, CreateIssueDetails issue)
        {
            var uri = string.Format("{0}/repos/{1}/{2}/issues", baseUrl, organization, repository);
            var request = new RestRequest(uri, Method.POST);
            request.AddParameter("application/json", ConvertToString(issue), ParameterType.RequestBody);
            return client.Execute<IssueDetails>(request).Data;
        }

        public IssueDetails UpdateIssue(string organization, string repository, int number, IssueDetails details)
        {
            var uri = string.Format("{0}/repos/{1}/{2}/issues/{3}", baseUrl, organization, repository, number.ToString());
            var request = new RestRequest(uri, Method.PATCH);
            request.AddParameter("application/json", ConvertToString(details), ParameterType.RequestBody);
            return client.Execute<IssueDetails>(request).Data;
        }

        public IssueDetails CloseIssue(string organization, string repository, int number)
        {
            var details = new IssueDetails();
            details.state = "closed";
            return this.UpdateIssue(organization: organization, repository: repository, number: number, details: details);
        }

        private string ConvertToString(Object details)
        {
            var str = JsonConvert.SerializeObject(details,
                new JsonSerializerSettings
                {
                    NullValueHandling = NullValueHandling.Ignore,
                    DefaultValueHandling = DefaultValueHandling.Ignore
                });
            return str;
        }

        private T ConvertToObject<T>(String details)
        {
            T result = JsonConvert.DeserializeObject<T>(details,
                new JsonSerializerSettings
                {
                    NullValueHandling = NullValueHandling.Ignore,
                    DefaultValueHandling = DefaultValueHandling.Ignore
                });
            return result;
        }
    }
}
