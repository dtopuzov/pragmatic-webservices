using log4net;
using RestSharp;
using System.Reflection;

namespace GitHubTests.Helpers
{
    /// <summary>
    /// Extension for RestSharp rest client
    /// </summary>
    public static class RestClientExtensions
    {
        private static readonly ILog log = LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);

        /// <summary>
        /// Sends HTTP request and retrun the response.
        /// In addition it logs details for sended request.
        /// </summary>
        /// <param name="client">RestClient object.</param>
        /// <param name="request">Request object.</param>
        /// <returns>Response object.</returns>
        public static IRestResponse SendRequest(this RestClient client, IRestRequest request)
        {
            #region Log details for HTTP Request
            log.Info("======================= Request =======================");
            log.Info("Url: " + client.BaseUrl + request.Resource);
            log.Info("Method: " + request.Method);
            if (client.DefaultParameters.Count > 0)
            {
                log.Info("Client Params:");
                foreach (var param in client.DefaultParameters)
                {
                    log.Info(param.Name + ":" + param.Value);
                }
            }
            if (request.Parameters.Count > 0)
            {
                log.Info("Request Params:");
                foreach (var param in request.Parameters)
                {
                    log.Info(param.Name + ":" + param.Value);
                }
            }
            #endregion

            var response = client.Execute(request);

            #region Log details for HTTP Response
            log.Info("======================= Response ======================");
            log.Info("Status Code: " + response.StatusCode);
            log.Info("Body: " + response.Content);
            #endregion

            return response;
        }
    }
}
