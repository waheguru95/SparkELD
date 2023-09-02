package com.example.eld;

import androidx.appcompat.app.AppCompatActivity;

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

import com.example.eld.activity.Deshboard_screen;
import com.example.eld.custumclass.Helperclass;


public class Splesh_screen extends AppCompatActivity {
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
                if(Helperclass.getAuthenticToken(Splesh_screen.this).isEmpty()){
                    startActivity(new Intent(Splesh_screen.this,Login_screen.class));
                    finish();
                }else {
                    startActivity(new Intent(Splesh_screen.this, Deshboard_screen.class));
                    finish();
                }
            }
        },splash);
    }
}