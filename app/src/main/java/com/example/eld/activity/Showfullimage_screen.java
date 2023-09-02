package com.example.eld.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.eld.R;
import com.squareup.picasso.Picasso;

public class Showfullimage_screen extends AppCompatActivity {
ImageView imageeee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showfullimage_screen);
        imageeee=findViewById(R.id.imageeee);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            int picture = bundle.getInt("url");
            imageeee.setImageResource(picture);
        }
    }
}