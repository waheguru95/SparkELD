package com.example.eld.network.dto.login.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record {
    @SerializedName("location")
    @Expose
    public String location;
    @SerializedName("odometer")
    @Expose
    public String odometer;
    @SerializedName("AttendenceType")
    @Expose
    public String attendenceType;
    @SerializedName("EngineHours")
    @Expose
    public String engineHours;
    @SerializedName("UserId")
    @Expose
    public int userId;
    @SerializedName("timeRecord")
    @Expose
    public String timeRecord;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("Origin")
    @Expose
    public String origin;


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOdometer() {
        return odometer;
    }

    public void setOdometer(String odometer) {
        this.odometer = odometer;
    }

    public String getAttendenceType() {
        return attendenceType;
    }

    public void setAttendenceType(String attendenceType) {
        this.attendenceType = attendenceType;
    }

    public String getEngineHours() {
        return engineHours;
    }

    public void setEngineHours(String engineHours) {
        this.engineHours = engineHours;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTimeRecord() {
        return timeRecord;
    }

    public void setTimeRecord(String timeRecord) {
        this.timeRecord = timeRecord;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}
