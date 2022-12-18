using Newtonsoft.Json;
using System;

namespace GitHubTests.Api.Objects
{
    public partial class Reactions
    {
        [JsonProperty("url")]
        public Uri Url { get; set; }

        [JsonProperty("total_count")]
        public long TotalCount { get; set; }

        [JsonProperty("+1")]
        public long The1 { get; set; }

        [JsonProperty("-1")]
        public long Reactions1 { get; set; }

        [JsonProperty("laugh")]
        public long Laugh { get; set; }

        [JsonProperty("hooray")]
        public long Hooray { get; set; }

        [JsonProperty("confused")]
        public long Confused { get; set; }

        [JsonProperty("heart")]
        public long Heart { get; set; }

        [JsonProperty("rocket")]
        public long Rocket { get; set; }

        [JsonProperty("eyes")]
        public long Eyes { get; set; }
    }
}
