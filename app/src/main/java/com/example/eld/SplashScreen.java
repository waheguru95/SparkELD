package com.example.eld;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eld.activity.BaseActivity;
import com.example.eld.activity.DashboardScreen;


public class SplashScreen extends BaseActivity {
    private static final int splash=2000;
    ImageView inneranimation;
    TextView first;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //int a = 30/0;

        final BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();
//        bAdapter.enable();

        inneranimation=findViewById(R.id.image);
        first=findViewById(R.id.appname);

        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.blink);
        inneranimation.startAnimation(animation);
        first.startAnimation(animation);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(() -> {
            if(!helperClass.getLoginStatus()){
                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                finish();
            }else {
                startActivity(new Intent(SplashScreen.this, DashboardScreen.class));
                finish();
            }
        },splash);
    }
}