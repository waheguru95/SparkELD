package com.example.eld.network.dto.login.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetLogsModel {


    @SerializedName("statusCode")
    private int statusCode;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<AttendanceData> data;
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AttendanceData> getData() {
        return data;
    }

    public void setData(List<AttendanceData> data) {
        this.data = data;
    }
}

