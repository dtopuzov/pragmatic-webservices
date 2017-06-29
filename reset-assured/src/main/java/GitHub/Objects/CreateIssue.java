package GitHub.Objects;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
        "title",
        "user",
        "labels",
        "state",
        "milestone",
        "body"
})
public class CreateIssue {
    @JsonProperty("title")
    public String title;
    @JsonProperty("user")
    public User user;
    @JsonProperty("labels")
    public List<Object> labels = null;
    @JsonProperty("state")
    public String state;
    @JsonProperty("milestone")
    public Object milestone;
    @JsonProperty("body")
    public String body;
}