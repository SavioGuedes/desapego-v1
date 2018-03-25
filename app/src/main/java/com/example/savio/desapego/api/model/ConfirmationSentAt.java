package com.example.savio.desapego.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class ConfirmationSentAt {

    @SerializedName("$date")
    @Expose
    private String $date;

    public String get$date() {
        return $date;
    }

    public void set$date(String $date) {
        this.$date = $date;
    }
}
