package api.objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@SuppressWarnings("unused")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "node_id",
        "url",
        "name",
        "color",
        "default"
})
public class Label {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("node_id")
    public String nodeId;
    @JsonProperty("url")
    public String url;
    @JsonProperty("name")
    public String name;
    @JsonProperty("color")
    public String color;
    @JsonProperty("default")
    public Boolean isDefault;

}
