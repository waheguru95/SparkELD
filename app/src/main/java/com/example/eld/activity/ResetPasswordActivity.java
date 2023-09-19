package com.example.eld.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;

import com.example.eld.LoginActivity;
import com.example.eld.R;
import com.example.eld.network.dto.login.request.ChangePasswordRequestModel;
import com.example.eld.network.dto.login.response.ChangePasswordResponseModel;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends BaseActivity {
    Toolbar toolbar;
    EditText et_otp, et_newPassword, et_comfirmPassword;
    MaterialButton btnResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        toolbar = findViewById(R.id.toolbar);
        btnResetPassword = findViewById(R.id.btn_reset_password);
        et_otp = findViewById(R.id.et_otp);
        et_newPassword = findViewById(R.id.et_password);
        et_comfirmPassword = findViewById(R.id.et_confirm_password);
        setAppBarTitle();
        btnResetPassword.setOnClickListener(v -> {
            String otp = et_otp.getText().toString();
            String password = et_newPassword.getText().toString();
            String confirmPassword = et_comfirmPassword.getText().toString();
            if (TextUtils.isEmpty(otp)) {
                et_newPassword.setError("OTP is required");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                et_newPassword.setError("Password is required");
                return;
            }
            if (TextUtils.isEmpty(confirmPassword)) {
                et_comfirmPassword.setError("Confirm password is required");
                return;
            }
            if (password.equals(confirmPassword)) {
                changePassword(et_otp.getText().toString().trim(), helperClass.getEmail(), et_newPassword.getText().toString());
            } else {
                showToast("Passwords do not match");
            }

        });
    }

    private void setAppBarTitle() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    public void changePassword(String otp, String email, String newPassword) {
        Call<JsonObject> call = apiService.changePassword(new ChangePasswordRequestModel(email.trim(), otp, newPassword));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                hideLoader();
                if (response.isSuccessful()) {
                    ChangePasswordResponseModel changePasswordResponseModel = new Gson().fromJson(response.body().toString(), ChangePasswordResponseModel.class);
                    showToast(changePasswordResponseModel.getMessage());
                    Intent loginIntent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                    ResetPasswordActivity.this.startActivity(loginIntent);
                    finish();
                } else {
                    try {
                        onAPIErrorMessageReceived(response.errorBody().string().toString());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // Handle failure
                t.printStackTrace();
            }
        });
    }
}