package com.example.savio.desapego.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ProfileAttributes {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
