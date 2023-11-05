package com.example.eld.network.dto.login.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class  UserModel {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("userName")
    @Expose
    private String userName;

    @SerializedName("role")
    @Expose
    private String role;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("coDriver")
    @Expose
    private String coDriver;

    @SerializedName("tripNo")
    @Expose
    private String tripNo;

    @SerializedName("shippingAddress")
    @Expose
    private String shippingAddress;

    // Getters and Setters for all the fields
    // ...

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}