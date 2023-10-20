package com.example.eld.network.dto.login.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAttendanceResponseModel {

        @SerializedName("statusCode")
        private int statusCode;

        @SerializedName("message")
        private String message;

        @SerializedName("data")
        private List<Record> data;

        @SerializedName("status")
        private boolean status;

        public int getStatusCode() {
            return statusCode;
        }

        public String getMessage() {
            return message;
        }

        public List<Record> getData() {
            return data;
        }

        public boolean isStatus() {
            return status;
        }
    }

