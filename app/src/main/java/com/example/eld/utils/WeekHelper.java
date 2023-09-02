package com.example.eld.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

public class WeekHelper {
    private Context context;
    private SharedPreferences prefs;
    private Handler handler;
    private int timerValue;
    private boolean isRunning;

    public WeekHelper(Context context) {
        this.context = context;
        this.prefs = context.getSharedPreferences("timer_prefs", Context.MODE_PRIVATE);
        this.handler = new Handler();
        this.timerValue = prefs.getInt("timer_value", 0);
        this.isRunning = false;
    }

    public int getTimerValue() {
        return timerValue;
    }

    public void start() {
        isRunning = true;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isRunning) {
                    timerValue++;
                    prefs.edit().putInt("timer_value", timerValue).apply();
                    handler.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }

    public void stop() {
        isRunning = false;
    }
}