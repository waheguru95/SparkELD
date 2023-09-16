package com.example.eld.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DriverProfileModel {

    @SerializedName("statusCode")
    private int statusCode;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<VehicleDataModel> data;

    @SerializedName("status")
    private boolean status;

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public List<VehicleDataModel> getData() {
        return data;
    }

    public boolean isStatus() {
        return status;
    }
}
