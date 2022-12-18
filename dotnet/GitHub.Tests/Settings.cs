using System;

namespace GitHubTests
{
    public static class Settings
    {
        public const string ApiUrl = "https://api.github.com";
        public const string WebUrl = "https://github.com/";
        public static readonly string User = GetEnvVariableValue("GITHUB_USER");
        public static readonly string Repo = GetEnvVariableValue("GITHUB_REPO");
        public static readonly string Token = $"Bearer {GetEnvVariableValue("GITHUB_TOKEN")}";

        private static string GetEnvVariableValue(string variableName)
        {
            var value = Environment.GetEnvironmentVariable(variableName);
            return value ?? throw new Exception($"Please set {variableName} environment variable.");
        }
    }
}
