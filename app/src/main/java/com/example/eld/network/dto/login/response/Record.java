package com.example.eld.network.dto.login.response;

import com.google.gson.annotations.SerializedName;

public class Record {
    @SerializedName("VIN")
    private String VIN;

    @SerializedName("Latitude")
    private String Latitude;

    @SerializedName("Longitude")
    private String Longitude;

    @SerializedName("AttendenceType")
    private String AttendenceType;

    @SerializedName("UserId")
    private int UserId;

    @SerializedName("DATE(RecordDate)")
    private String DATE;

    public String getVIN() {
        return VIN;
    }

    public String getLatitude() {
        return Latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public String getAttendenceType() {
        return AttendenceType;
    }

    public int getUserId() {
        return UserId;
    }

    public String getDATE() {
        return DATE;
    }
}
