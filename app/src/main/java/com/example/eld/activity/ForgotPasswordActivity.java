package com.example.eld.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.example.eld.R;
import com.example.eld.network.dto.login.request.ForgotPasswordModel;
import com.example.eld.network.dto.login.request.LoginRequestModel;
import com.example.eld.network.dto.login.response.ForgotPasswordResponseModel;
import com.example.eld.network.dto.login.response.LoginResponseModel;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends BaseActivity {

    EditText et_emailId;
    MaterialButton btnSubmit;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        toolbar = findViewById(R.id.toolbar);
        btnSubmit = findViewById(R.id.btn_submit);
        et_emailId = findViewById(R.id.emailid);
        setAppBarTitle();
        setListeners();
    }

    private void setListeners() {
        btnSubmit.setOnClickListener(v -> {
            if (TextUtils.isEmpty(et_emailId.getText().toString())) {
                showToast("Please enter registered Email Id!");
            } else {
                helperClass.setEmail(et_emailId.getText().toString());
                sendOtp(et_emailId.getText().toString());
            }
        });
    }

    private void setAppBarTitle() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void sendOtp(String email) {
        JsonObject requestBody = new JsonObject();
        Call<JsonObject> call = apiService.sendOtp(new ForgotPasswordModel(email.trim()));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                hideLoader();
                if (response.isSuccessful()) {
                     ForgotPasswordResponseModel forgotPasswordResponseModel = new Gson().fromJson(response.body().toString(), ForgotPasswordResponseModel.class);
                    showToast(forgotPasswordResponseModel.getMessage());
                    Intent resetPasswordIntent = new Intent(ForgotPasswordActivity.this, ResetPasswordActivity.class);
                    ForgotPasswordActivity.this.startActivity(resetPasswordIntent);
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
