package com.example.eld.network.dto.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseModel {
    @SerializedName("statusCode")
    @Expose
    public int statusCode;
    @SerializedName("message")
    @Expose
    public String message;
}
