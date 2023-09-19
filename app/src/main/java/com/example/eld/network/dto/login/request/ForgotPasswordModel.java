package com.example.eld.network.dto.login.request;

public class ForgotPasswordModel {
    private String email;
    public ForgotPasswordModel(String email) {
        this.email = email;

    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String userName) {
        this.email = email;
    }

}

