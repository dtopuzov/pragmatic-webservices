using System;
using System.Net;
using System.Net.Http;

namespace GitHubTests.Api
{
    internal class Client
    {
        public static HttpResponseMessage SendRequest(HttpRequestMessage message)
        {
            ServicePointManager.SecurityProtocol = SecurityProtocolType.Tls12;
            message.Headers.UserAgent.ParseAdd(".NET HTTP Client");
            using (var httpClient = new HttpClient(new HttpLoggingHandler(new HttpClientHandler())))
            {
                httpClient.DefaultRequestHeaders.Add("Authorization", GetToken());
                httpClient.Timeout = new TimeSpan(0, 20, 0);
                return httpClient.SendAsync(message, HttpCompletionOption.ResponseContentRead).Result;
            }
        }

        private static string GetToken()
        {
            var token = Environment.GetEnvironmentVariable("GITHUB_TOKEN");
            return token ?? throw new Exception("Please set GITHUB_TOKEN environment variable with GitHub OAuth access token.");
        }
    }
}
