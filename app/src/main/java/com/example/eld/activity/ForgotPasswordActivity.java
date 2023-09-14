package com.example.eld.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.example.eld.R;
import com.example.eld.network.ApiService;
import com.example.eld.network.RetrofitClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends BaseActivity {

    EditText et_emailId;
    RelativeLayout rl_submitbutton;
    ApiService apiService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        rl_submitbutton = findViewById(R.id.rl_submitbutton);
        apiService = RetrofitClient.apiService(this).create(ApiService.class);
        rl_submitbutton.setOnClickListener(v -> {
            Intent resetPasswordIntent = new Intent(this, ResetPasswordActivity.class);
            this.startActivity(resetPasswordIntent);
        });
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

                } else {

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
