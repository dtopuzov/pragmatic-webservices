package org.pragmatic.github.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Label {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("node_id")
    @Expose
    public String nodeId;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("color")
    @Expose
    public String color;
}
