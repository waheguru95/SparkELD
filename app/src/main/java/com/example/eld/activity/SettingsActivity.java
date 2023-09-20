package com.example.eld.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.eld.R;
import com.google.android.material.appbar.MaterialToolbar;

import kotlin.jvm.internal.Intrinsics;

public class SettingsActivity extends AppCompatActivity {
    MaterialToolbar toolbar;
    CardView rate, share, privacy, faq;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_screen);

        toolbar = findViewById(R.id.toolbar);
        rate = findViewById(R.id.rate);
        share = findViewById(R.id.share);
        privacy = findViewById(R.id.privacypolicy);
        faq = findViewById(R.id.faq);

        setAppBarTitle();
        setListeners();

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri var10000 = Uri.parse("market://details?id=com.kpsm.solutions.ujjvalacademyadmin");
                Intrinsics.checkNotNullExpressionValue(var10000, "Uri.parse(\"market://deta…ions.ujjvalacademyadmin\")");
                Uri uri = var10000;
                Intent goToMarket = new Intent("android.intent.action.VIEW", uri);
                try {
                    SettingsActivity.this.startActivity(goToMarket);
                } catch (ActivityNotFoundException var5) {
                    SettingsActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=com.kpsm.solutions.ujjvalacademyadmin")));
                }
            }
        });
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, Privacy_policy_screen.class));
            }
        });

    }

    private void setListeners() {
        faq.setOnClickListener(v -> startActivity(new Intent(SettingsActivity.this, Faq_screen.class)));

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openAppShareDialog();
            }
        });

    }

    private void openAppShareDialog() {
        Intent sendIntent = new Intent();
        sendIntent.setAction("android.intent.action.SEND");
        sendIntent.putExtra("android.intent.extra.TEXT", "Hey check out my app at:");
        sendIntent.setType("text/plain");
        SettingsActivity.this.startActivity(sendIntent);
    }

    private void setAppBarTitle() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

}