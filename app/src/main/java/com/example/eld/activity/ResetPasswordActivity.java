package com.example.eld.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.eld.R;
import com.google.android.material.button.MaterialButton;

public class ResetPasswordActivity extends AppCompatActivity {
    Toolbar toolbar;
    MaterialButton btnResetPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        toolbar = findViewById(R.id.toolbar);
        btnResetPassword = findViewById(R.id.btn_reset_password);
        setAppBarTitle();
    }

    private void setAppBarTitle() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

}