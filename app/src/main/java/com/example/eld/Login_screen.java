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

import androidx.appcompat.app.AppCompatActivity;

import com.example.eld.activity.Deshboard_screen;
import com.example.eld.custumclass.Apiservices;
import com.example.eld.custumclass.Helperclass;
import com.example.eld.interfaces.Apiinterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.net.HttpURLConnection;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Login_screen extends AppCompatActivity {
    TextView text_forgot, rememerme;

    boolean passwordvisible;
    RelativeLayout loginbutton;
    private long backpresstime;
    EditText paswordedit, driverid;

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
        text_forgot = findViewById(R.id.text_forgot);
        rememerme = findViewById(R.id.rememerme);
        loginbutton = findViewById(R.id.loginbutton);
        paswordedit = findViewById(R.id.paswordedit);
        driverid = findViewById(R.id.driverid);

        popupdolig = new Dialog(this);


        setcolorinrember(rememerme, getResources().getColor(R.color.second),
                getResources().getColor(R.color.first));

        setTextViewColor(text_forgot, getResources().getColor(R.color.first),
                getResources().getColor(R.color.second));

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String driveridd = String.valueOf(driverid.getText());
                String paswordeditt = paswordedit.getText().toString();

                if (driveridd.isEmpty()) {
                    Toast.makeText(Login_screen.this, "Enter Driver ID", Toast.LENGTH_SHORT).show();
                } else if (paswordeditt.isEmpty()) {
                    Toast.makeText(Login_screen.this, "Enter Password", Toast.LENGTH_SHORT).show();
                } else if (paswordeditt.length() < 6) {
                    Toast.makeText(Login_screen.this, "Password must be 6 characters", Toast.LENGTH_SHORT).show();
                } else {

                  loginuser(driveridd, paswordeditt);
                    //---------TO Set Flow---------//
                    startActivity(new Intent(Login_screen.this, Deshboard_screen.class));
                    finish();
                }

            }
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

    private void loginuser(String driveridd, String paswordeditt) {

        if (Helperclass.getNetworkInfo(this)) {
            popupdolig.setContentView(R.layout.loadingpopup);
            popupdolig.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            popupdolig.setCancelable(false);
            popupdolig.show();


            Apiinterface apiinterface = Apiservices.apiService(this).create(Apiinterface.class);

            Call<JsonObject> call = apiinterface.loginUser(driveridd.trim(), paswordeditt);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    try {
                        popupdolig.dismiss();
                        if (response.code() == HttpURLConnection.HTTP_OK && response.code() == 200) {
                            JSONObject body = new JSONObject(response.body().toString());

                            String getdriverid = body.getString("driver_id");
                            String getToken = body.getString("jwt");
                            String id = body.getString("id");

                            Helperclass.setEmail(getdriverid, Login_screen.this);
                            Helperclass.setFirstLogin(true, Login_screen.this);
                            Helperclass.setAuthenticToken(getToken, Login_screen.this);
                            Helperclass.setid(id, Login_screen.this);

                            startActivity(new Intent(Login_screen.this, Deshboard_screen.class));
                            finish();

                        } else {
                            JSONObject body = new JSONObject(response.errorBody().toString());
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
                public void onFailure(Call<JsonObject> call, Throwable t) {
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