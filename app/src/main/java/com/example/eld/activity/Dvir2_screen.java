package com.example.eld.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.eld.R;

public class Dvir2_screen extends AppCompatActivity {
ImageView dvirrr_back;
CardView adddefectes;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dvir2_screen);
        dvirrr_back=findViewById(R.id.dvirrr_back);
        adddefectes=findViewById(R.id.adddefectes);

        dvirrr_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adddefectes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dvir2_screen.this,DVIR_screen.class));
            }
        });
    }
}