package com.example.eld.network.dto.login.request;

public class UpdateTripNoModel {
    private String id;
    private String tripNo;

    public UpdateTripNoModel(String id, String tripNo) {
        this.id = id;
        this.tripNo = tripNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTripNo() {
        return tripNo;
    }

    public void setTripNo(String tripNo) {
        this.tripNo = tripNo;
    }
}