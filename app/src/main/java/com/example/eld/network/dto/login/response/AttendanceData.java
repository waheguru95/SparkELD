package com.example.eld.network.dto.login.response;

import com.google.gson.annotations.SerializedName;

public class AttendanceData {
    @SerializedName("id")
    private int id;

    @SerializedName("Status")
    private String status;

    @SerializedName("VIN")
    private String vin;

    @SerializedName("Speed")
    private String speed;

    @SerializedName("Odometer")
    private String odometer;

    @SerializedName("EngineHours")
    private String engineHours;

    @SerializedName("Latitude")
    private String latitude;

    @SerializedName("Longitude")
    private String longitude;

    @SerializedName("Location")
    private String location;

    @SerializedName("AttendenceType")
    private String attendanceType;

    @SerializedName("coDriver")
    private String coDriver;

    @SerializedName("tripNo")
    private String tripNo;

    @SerializedName("shippingAddress")
    private String shippingAddress;

    @SerializedName("UserId")
    private int userId;

    @SerializedName("RecordDate")
    private String recordDate;

    @SerializedName("timeRecord")
    private String timeRecord;

    @SerializedName("verified")
    private int verified;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getAttendanceType() {
        return attendanceType;
    }

    public void setAttendanceType(String attendanceType) {
        this.attendanceType = attendanceType;
    }

    public String getCoDriver() {
        return coDriver;
    }

    public void setCoDriver(String coDriver) {
        this.coDriver = coDriver;
    }

    public String getTripNo() {
        return tripNo;
    }

    public void setTripNo(String tripNo) {
        this.tripNo = tripNo;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
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

    public String getTimeRecord() {
        return timeRecord;
    }

    public void setTimeRecord(String timeRecord) {
        this.timeRecord = timeRecord;
    }

    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }


}
