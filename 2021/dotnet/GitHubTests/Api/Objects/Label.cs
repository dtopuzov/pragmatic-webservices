using Newtonsoft.Json;
using System;

namespace GitHubTests.Api.Objects
{
    public class Label
    {
        [JsonProperty("id")]
        public long Id { get; set; }

        [JsonProperty("node_id")]
        public string NodeId { get; set; }

        [JsonProperty("url")]
        public Uri Url { get; set; }

        [JsonProperty("name")]
        public string Name { get; set; }

        [JsonProperty("color")]
        public string Color { get; set; }

        [JsonProperty("default")]
        public bool Default { get; set; }

        [JsonProperty("description")]
        public string Description { get; set; }
    }
}
