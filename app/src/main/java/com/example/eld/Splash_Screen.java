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
import com.example.eld.activity.DashBoardScreen;


public class Splash_Screen extends BaseActivity {
    private static int splash=2000;
    ImageView inneranimation;
    TextView first;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splesh_screen);

        final BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();
//        bAdapter.enable();

        inneranimation=findViewById(R.id.imageg);
        first=findViewById(R.id.first);

        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.blink);
        inneranimation.startAnimation(animation);
        first.startAnimation(animation);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(helperClass.getAuthenticToken().isEmpty()){
                    startActivity(new Intent(Splash_Screen.this, LoginActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(Splash_Screen.this, DashBoardScreen.class));
                    finish();
                }
            }
        },splash);
    }
}