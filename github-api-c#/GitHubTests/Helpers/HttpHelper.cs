using System;
using System.Net.Http;
using System.Net.Http.Headers;

namespace GitHubTests.Helpers
{
    class HttpHelper
    {
        private static string UserAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36";

        public static HttpResponseMessage SendRequest(HttpRequestMessage message)
        {
            message.Headers.UserAgent.ParseAdd(UserAgent);
            var token = Environment.GetEnvironmentVariable("GITHUB_TOKEN");
            message.Headers.Authorization = new AuthenticationHeaderValue("Token", token);
            if (message.Method != HttpMethod.Get)
            {
                message.Headers.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
            }
            using (var httpClient = new HttpClient())
            {
                httpClient.Timeout = new TimeSpan(0, 10, 0);
                return httpClient.SendAsync(message, HttpCompletionOption.ResponseContentRead).Result;
            }
        }
    }
}
