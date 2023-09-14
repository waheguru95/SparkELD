package com.example.eld;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.eld.activity.BaseActivity;
import com.example.eld.activity.DashBoardScreen;
import com.example.eld.activity.ForgotPasswordActivity;
import com.example.eld.network.ApiService;
import com.example.eld.network.RetrofitClient;
import com.example.eld.network.dto.login.request.LoginRequestModel;
import com.example.eld.network.dto.login.response.LoginResponseModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Login_screen extends BaseActivity {
    TextView text_forgot, rememerme;

    boolean passwordvisible;
    RelativeLayout loginbutton;
    private long backpresstime;
    EditText paswordedit, etDriverId;
    ApiService apiinterface = RetrofitClient.apiService(this).create(ApiService.class);


    Dialog popupdolig;
    private static OkHttpClient.Builder httpClientBuilder = null;
    private static Retrofit retrofit = null;
    public static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        text_forgot = findViewById(R.id.tvforgotpassword);
        rememerme = findViewById(R.id.rememberme);
        loginbutton = findViewById(R.id.loginbutton);
        paswordedit = findViewById(R.id.paswordedit);
        etDriverId = findViewById(R.id.driverid);

        popupdolig = new Dialog(this);


//        setcolorinrember(rememerme, getResources().getColor(R.color.skyblue),
//                getResources().getColor(R.color.lightskyblue));
//
//        setTextViewColor(text_forgot, getResources().getColor(R.color.skyblue),
//                getResources().getColor(R.color.lightskyblue));
        loginbutton.setOnClickListener(v -> {
            String driverId = etDriverId.getText().toString().trim();
            String paswordeditt = paswordedit.getText().toString();

            if (driverId.isEmpty()) {
                Toast.makeText(Login_screen.this, "Enter Driver ID", Toast.LENGTH_SHORT).show();
            } else if (paswordeditt.isEmpty()) {
                Toast.makeText(Login_screen.this, "Enter Password", Toast.LENGTH_SHORT).show();
            } else if (paswordeditt.length() < 6) {
                Toast.makeText(Login_screen.this, "Password must be at least of 6 characters", Toast.LENGTH_SHORT).show();
            } else {
                callLoginUserApi(driverId, paswordeditt);
            }

        });

        text_forgot.setOnClickListener(v ->
        {
            Intent forgotPWIntent = new Intent(this, ForgotPasswordActivity.class);
            this.startActivity(forgotPWIntent);
        });
        paswordedit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= paswordedit.getRight() - paswordedit.getCompoundDrawables()[right].getBounds().width()) {
                        int selection = paswordedit.getSelectionEnd();
                        if (passwordvisible) {
                            paswordedit.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);

                            paswordedit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordvisible = false;
                        } else {
                            paswordedit.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0);
                            paswordedit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordvisible = true;
                        }
                        paswordedit.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void callLoginUserApi(String driverId, String password) {
        if (helperClass.getNetworkInfo()) {
            popupdolig.setContentView(R.layout.loadingpopup);
            popupdolig.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            popupdolig.setCancelable(false);
            popupdolig.show();

            Call<JsonElement> call = apiinterface.loginUser(new LoginRequestModel(driverId, password));
            call.enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(@NonNull Call<JsonElement> call, @NonNull Response<JsonElement> response) {
                    try {
                        popupdolig.dismiss();
                        if (response.code() == HttpURLConnection.HTTP_OK) {
                            LoginResponseModel loginRequestModel = new Gson().fromJson(response.body().toString(), LoginResponseModel.class);

                            helperClass.setEmail(loginRequestModel.getData().getEmail());
                            // Helperclass.setAuthenticToken(getToken, Login_screen.this);
                            helperClass.setId("" + loginRequestModel.getData().getId());
                            Call<ResponseBody> driverProfileCall = apiinterface.getDriverProfile(1);
                            try {
                                Response<ResponseBody> driverProfileResponse = driverProfileCall.execute();
                                if (response.isSuccessful()) {
                                    ResponseBody responseBody = driverProfileResponse.body();
                                } else {
                                    // Handle the error response here
                                    // You can get the error message from response.errorBody()
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            helperClass.setFirstLogin(true);
                            startActivity(new Intent(Login_screen.this, DashBoardScreen.class));
                            finish();
                        } else {
                            JSONObject body = new JSONObject(response.errorBody().string().toString());
                            if (body.has("message")) {
                                Toast.makeText(getApplicationContext(), "" + body.getString("message").toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e) {
                        popupdolig.dismiss();
                        Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    popupdolig.dismiss();
                    Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        } else {
            popupdolig.dismiss();
            Toast.makeText(this, "Check network connection", Toast.LENGTH_SHORT).show();
        }
    }


    private void setcolorinrember(TextView rememerme, int... color) {
        TextPaint textPaint = rememerme.getPaint();


        float width = textPaint.measureText(rememerme.getText().toString());
        Shader shader = new LinearGradient(0, 0, width, rememerme.getTextSize(), color, null, Shader.TileMode.CLAMP);
        rememerme.getPaint().setShader(shader);
        rememerme.setTextColor(color[0]);
    }

    private void setTextViewColor(TextView text_forgot, int... color) {
        TextPaint textPaint = text_forgot.getPaint();
        float width = textPaint.measureText(text_forgot.getText().toString());
        Shader shader = new LinearGradient(0, 0, width, text_forgot.getTextSize(), color, null, Shader.TileMode.CLAMP);
        text_forgot.getPaint().setShader(shader);
        text_forgot.setTextColor(color[0]);
    }

    @Override
    public void onBackPressed() {
        if (backpresstime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        backpresstime = System.currentTimeMillis();

    }


}