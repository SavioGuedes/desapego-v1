package com.example.savio.desapego.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


class MongoId {
    @SerializedName("$oid")
    @Expose
    private String $oid;

    public String get$oid() {
        return $oid;
    }

    public void set$oid(String $oid) {
        this.$oid = $oid;
    }

}
