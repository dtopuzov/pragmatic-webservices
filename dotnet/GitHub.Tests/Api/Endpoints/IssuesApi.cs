using GitHubTests.Api.Objects;
using Newtonsoft.Json;
using NUnit.Framework;
using System;
using System.Net.Http;
using System.Text;

namespace GitHubTests.Api.Endpoints
{
    public static class IssuesApi
    {
        public static IssueDetails GetIssueDetails(int number)
        {
            var uri = $"{Settings.ApiUrl}/repos/{Settings.User}/{Settings.Repo}/issues/{number}";
            var message = new HttpRequestMessage(HttpMethod.Get, new Uri(uri));
            var response = Client.SendRequest(message);
            var content = response.Content.ReadAsStringAsync().Result;
            return JsonConvert.DeserializeObject<IssueDetails>(content);
        }

        public static IssueDetails CreateIssue(IssueDetails issue)
        {
            var uri = $"{Settings.ApiUrl}/repos/{Settings.User}/{Settings.Repo}/issues";
            var message = new HttpRequestMessage(HttpMethod.Post, new Uri(uri))
            {
                Content = new StringContent(ConvertToString(issue), Encoding.UTF8, "application/json")
            };

            var response = Client.SendRequest(message);

            Assert.That(response.StatusCode, Is.EqualTo(System.Net.HttpStatusCode.Created));
            var result = response.Content.ReadAsStringAsync().Result;
            return JsonConvert.DeserializeObject<IssueDetails>(result);
        }

        private static string ConvertToString(Object details)
        {
            return JsonConvert.SerializeObject(details,
                new JsonSerializerSettings
                {
                    NullValueHandling = NullValueHandling.Ignore
                });
        }
    }
}
