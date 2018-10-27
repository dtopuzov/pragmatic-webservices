using System;
using System.Configuration;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;

namespace GitHubTests.Helpers
{
    class HttpHelper
    {
        private readonly static string User = ConfigurationManager.AppSettings["User"];
        private readonly static string Password = ConfigurationManager.AppSettings["Password"];

        private static string UserAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36";

        public static HttpResponseMessage SendRequest(HttpRequestMessage message)
        {
            message.Headers.UserAgent.ParseAdd(UserAgent);
            var byteArray = Encoding.ASCII.GetBytes(string.Format("{0}:{1}", User, Password));
            message.Headers.Authorization = new AuthenticationHeaderValue("Basic", Convert.ToBase64String(byteArray));
            if (message.Method != HttpMethod.Get)
            {
                message.Headers.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
            }
            using (var httpClient = new HttpClient(new LoggingHandler(new HttpClientHandler())))
            {
                httpClient.Timeout = new TimeSpan(0, 10, 0);
                return httpClient.SendAsync(message, HttpCompletionOption.ResponseContentRead).Result;
            }
        }
    }
}
