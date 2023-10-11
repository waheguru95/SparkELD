package com.example.eld.network.dto.login.request;

public class UpdateCoDriverModel {

    private String id;
    private String driverName;

    public UpdateCoDriverModel(String id, String driverName) {
        this.id = id;
        this.driverName = driverName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
}
