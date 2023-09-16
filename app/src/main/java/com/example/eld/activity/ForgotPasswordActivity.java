package com.example.eld.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.example.eld.R;
import com.google.android.material.button.MaterialButton;
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
                showError("Please enter registered Email Id!");
            } else {
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
        requestBody.addProperty("email", email);
        Call<JsonObject> call = apiService.sendOtp(requestBody);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject result = response.body();
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
