package com.example.eld.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eld.custumclass.Helperclass;

public class BaseActivity extends AppCompatActivity {

  public  Helperclass helperClass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         helperClass = new Helperclass(getSharedPref(this));
    }

    private SharedPreferences getSharedPref(Context context) {
        return context.getSharedPreferences("ELD", Context.MODE_PRIVATE);
    }

}
