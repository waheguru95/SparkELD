package com.example.eld.network.dto.login.request;

public class ChangePasswordRequestModel {
    private String email;
    private String otp;
    private String newPassword;


    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public ChangePasswordRequestModel(String email, String otp, String newPassword) {
        this.email = email;
        this.otp = otp;
        this.newPassword=newPassword;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
