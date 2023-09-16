package com.example.eld;

import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.eld.activity.BaseActivity;
import com.example.eld.activity.DashBoardScreen;
import com.example.eld.activity.ForgotPasswordActivity;
import com.example.eld.models.DriverProfileModel;
import com.example.eld.network.dto.login.request.LoginRequestModel;
import com.example.eld.network.dto.login.response.LoginResponseModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {
    TextView text_forgot;
    MaterialCheckBox cbRememberMe;
    MaterialButton btnLogin;
    private long backpresstime;
    EditText paswordedit, etDriverId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        text_forgot = findViewById(R.id.tvforgotpassword);
        cbRememberMe = findViewById(R.id.cb_remember_me);
        btnLogin = findViewById(R.id.loginbutton);
        paswordedit = findViewById(R.id.paswordedit);
        etDriverId = findViewById(R.id.driverid);

        setListener();
    }

    private void setListener() {
        btnLogin.setOnClickListener(v -> {
            String driverId = etDriverId.getText().toString().trim();
            String password = paswordedit.getText().toString();

            if (driverId.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Enter Driver ID", Toast.LENGTH_SHORT).show();
            } else if (password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
            } else if (password.length() < 6) {
                Toast.makeText(LoginActivity.this, "Password must be at least of 6 characters", Toast.LENGTH_SHORT).show();
            } else {
                callLoginUserApi(driverId, password);
            }
        });

        text_forgot.setOnClickListener(v -> {
            Intent forgotPasswordIntent = new Intent(this, ForgotPasswordActivity.class);
            this.startActivity(forgotPasswordIntent);
        });
    }

    private void setStatusBarTransparent() {
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    private void callLoginUserApi(String driverId, String password) {
        if (helperClass.getNetworkInfo()) {
            showLoader();

            Call<JsonElement> call = apiService.loginUser(new LoginRequestModel(driverId, password));
            call.enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(@NonNull Call<JsonElement> call, @NonNull Response<JsonElement> response) {
                    try {
                        hideLoader();
                        if (response.code() == HttpURLConnection.HTTP_OK) {
                            LoginResponseModel loginRequestModel = new Gson().fromJson(response.body().toString(), LoginResponseModel.class);
                            helperClass.setEmail(loginRequestModel.getData().getEmail());
                            helperClass.setId("" + loginRequestModel.getData().getId());
                            callGetDriverDetailsApi(helperClass.getID());
                            helperClass.setFirstLogin(true);
                            startActivity(new Intent(LoginActivity.this, DashBoardScreen.class));
                            finish();
                        } else {
                            onAPIErrorMessageReceived(response.errorBody().string().toString());
                        }
                    } catch (Exception e) {
                        hideLoader();
                        Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    hideLoader();
                    Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            showNoInternetConnectionError();
        }
    }

    private void callGetDriverDetailsApi(String driverId) {
        if (helperClass.getNetworkInfo()) {
            showLoader();
            Call<ResponseBody> driverProfileCall = apiService.getDriverProfile(driverId);
           // Call<ResponseBody> driverProfileCall = apiService.getDriverProfile(1);
            driverProfileCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if (response.isSuccessful()) {
                            ResponseBody responseBody = response.body();
                            if (responseBody != null) {
                                try {
                                    String profileJson = responseBody.string();
                                    Type modelType = new TypeToken<DriverProfileModel>() {}.getType();
                                    Gson gson = new Gson();
                                     DriverProfileModel driverProfileModel = gson.fromJson(profileJson, modelType);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        } else {
                            onAPIErrorMessageReceived(response.errorBody().toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    hideLoader();
                    Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            showNoInternetConnectionError();
        }

    }

    @Override
    public void onBackPressed() {
        if (backpresstime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(this, "Please press back again to exit", Toast.LENGTH_SHORT).show();
        }
        backpresstime = System.currentTimeMillis();

    }


}