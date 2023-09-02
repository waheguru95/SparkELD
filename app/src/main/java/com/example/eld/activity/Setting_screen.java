package com.example.eld.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.eld.R;

import kotlin.jvm.internal.Intrinsics;

public class Setting_screen extends AppCompatActivity {
ImageView setting_back;
CardView rate,share,privacy,faq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_screen);

        setting_back=findViewById(R.id.setting_back);
        rate=findViewById(R.id.rate);
        share=findViewById(R.id.share);
        privacy=findViewById(R.id.privacypolicy);
        faq=findViewById(R.id.faq);

        setting_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri var10000 = Uri.parse("market://details?id=com.kpsm.solutions.ujjvalacademyadmin");
                Intrinsics.checkNotNullExpressionValue(var10000, "Uri.parse(\"market://deta…ions.ujjvalacademyadmin\")");
                Uri uri = var10000;
                Intent goToMarket = new Intent("android.intent.action.VIEW", uri);
                try {
                    Setting_screen.this.startActivity(goToMarket);
                } catch (ActivityNotFoundException var5) {
                    Setting_screen.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=com.kpsm.solutions.ujjvalacademyadmin")));
                }
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction("android.intent.action.SEND");
                sendIntent.putExtra("android.intent.extra.TEXT", "Hey check out my app at:");
                sendIntent.setType("text/plain");
                Setting_screen.this.startActivity(sendIntent);
            }
        });
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
startActivity(new Intent(Setting_screen.this,Privacy_policy_screen.class));
            }
        });
        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Setting_screen.this,Faq_screen.class));
            }
        });
    }
}