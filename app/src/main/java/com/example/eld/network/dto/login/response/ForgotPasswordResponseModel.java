package com.example.eld.network.dto.login.response;

import com.example.eld.network.dto.common.BaseModel;
import com.google.gson.annotations.SerializedName;

public class ForgotPasswordResponseModel extends BaseModel {
        @SerializedName("data")
        private OtpData otpData;

        @SerializedName("status")
        private boolean status;


        public String getMessage() {
            return message;
        }

        public OtpData getOtpData() {
            return otpData;
        }


        public static class OtpData {
            @SerializedName("otp")
            private String otp;

            @SerializedName("user_Id")
            private int userId;

            @SerializedName("email")
            private String email;

            public String getOtp() {
                return otp;
            }

            public int getUserId() {
                return userId;
            }

            public String getEmail() {
                return email;
            }
        }
}
