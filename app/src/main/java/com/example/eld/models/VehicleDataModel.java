package com.example.eld.models;

import com.google.gson.annotations.SerializedName;

public class VehicleDataModel {
    @SerializedName("vehicleId")
    private int vehicleId;

    @SerializedName("truck_no")
    private String truckNo;

    @SerializedName("plate_no")
    private String plateNo;

    @SerializedName("VIN_no")
    private String vinNo;

    @SerializedName("model")
    private String model;

    @SerializedName("organizationName")
    private String organizationName;

    @SerializedName("contact_No")
    private String contactNo;

    @SerializedName("Address")
    private String address;

    @SerializedName("MCno")
    private String mcNo;

    @SerializedName("coworkerName")
    private String coworkerName;

    @SerializedName("coworkerId")
    private int coworkerId;

    public int getVehicleId() {
        return vehicleId;
    }

    public String getTruckNo() {
        return truckNo;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public String getVinNo() {
        return vinNo;
    }

    public String getModel() {
        return model;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public String getAddress() {
        return address;
    }

    public String getMcNo() {
        return mcNo;
    }

    public String getCoworkerName() {
        return coworkerName;
    }

    public int getCoworkerId() {
        return coworkerId;
    }
}
