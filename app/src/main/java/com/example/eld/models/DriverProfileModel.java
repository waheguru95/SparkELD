package com.example.eld.models;

import com.example.eld.network.dto.common.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DriverProfileModel extends BaseModel {
    @SerializedName("data")
    private List<VehicleDataModel> data;
    @SerializedName("status")
    private boolean status;

    public List<VehicleDataModel> getData() {
        return data;
    }

    public boolean isStatus() {
        return status;
    }
}
