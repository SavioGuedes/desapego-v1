package com.example.savio.desapego.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by supanonymous on 26/03/18.
 */

public class Fotinha {

    @SerializedName("_id")
    @Expose
    private MongoId id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public String getId() {
        return id.get$oid();
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
