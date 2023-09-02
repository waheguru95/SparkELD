package com.example.eld.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.eld.R;

public class DVIR_screen extends AppCompatActivity {
ImageView dvir_back;
CardView submit;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dvir_screen);
        dvir_back=findViewById(R.id.dvir_back);
        submit=findViewById(R.id.submit);

        dvir_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(DVIR_screen.this,Dvir2_screen.class));
            }
        });
    }
}