package com.example.eld.network.dto.login.response;

import com.example.eld.network.dto.common.BaseModel;
import com.example.eld.network.dto.user.UserModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponseModel extends BaseModel {

    @SerializedName("data")
    @Expose
    private UserModel data;
    @SerializedName("status")
    @Expose
    private boolean status;

    public UserModel getData() {
        return data;
    }

    public void setData(UserModel data) {
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
