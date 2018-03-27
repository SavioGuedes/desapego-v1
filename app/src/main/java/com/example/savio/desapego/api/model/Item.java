package com.example.savio.desapego.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Item {

    @SerializedName("_id")
    @Expose
    private MongoId id;
    @SerializedName("_keywords")
    @Expose
    private List<String> keywords = null;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("fotinhas")
    @Expose
    private List<Fotinha> fotinhas = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("profile_id")
    @Expose
    private MongoId profileId;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public String getId() {
        return id.get$oid();
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<Fotinha> getFotinhas() {
        return fotinhas;
    }

    public void setFotinhas(List<Fotinha> fotinhas) {
        this.fotinhas = fotinhas;
    }

    public String getProfileId() {
        return profileId.get$oid();
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

}