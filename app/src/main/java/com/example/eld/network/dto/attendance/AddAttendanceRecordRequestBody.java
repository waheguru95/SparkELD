package com.example.eld.network.dto.attendance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddAttendanceRecordRequestBody {
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("VIN")
    @Expose
    private String vin;
    @SerializedName("Speed")
    @Expose
    private String speed;
    @SerializedName("Odometer")
    @Expose
    private String odometer;
    @SerializedName("EngineHours")
    @Expose
    private String engineHours;
    @SerializedName("Latitude")
    @Expose
    private String latitude;
    @SerializedName("Longitude")
    @Expose
    private String longitude;
    @SerializedName("Location")
    @Expose
    private String location;
    @SerializedName("AttendenceType")
    @Expose
    private String attendenceType;
    @SerializedName("UserId")
    @Expose
    private int userId;
    @SerializedName("RecordDate")
    @Expose
    private String recordDate;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getOdometer() {
        return odometer;
    }

    public void setOdometer(String odometer) {
        this.odometer = odometer;
    }

    public String getEngineHours() {
        return engineHours;
    }

    public void setEngineHours(String engineHours) {
        this.engineHours = engineHours;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAttendenceType() {
        return attendenceType;
    }

    public void setAttendenceType(String attendenceType) {
        this.attendenceType = attendenceType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }
}
