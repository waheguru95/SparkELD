package com.example.eld.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public class Helper {
    private SharedPreferences sharedPref;
    private SimpleDateFormat dateFormat;
    private boolean timerCounting;
    private Date startTime;
    private Date stopTime;

    @NotNull
    public static final Helper.Companionn Companion = new Helper.Companionn((DefaultConstructorMarker)null);

    @Nullable
    public final Date startTime() {
        return this.startTime;
    }
    public final void setStartTime(@Nullable Date date) {
        this.startTime = date;
        SharedPreferences.Editor var2 = this.sharedPref.edit();
        boolean var4 = false;
        String stringDatee = date == null ? null : this.dateFormat.format(date);
        var2.putString("sleepstart", stringDatee);
        var2.apply();
    }
    @Nullable
    public final Date stopTime() {
        return this.stopTime;
    }

    public final void setStopTime(@Nullable Date date) {
        this.stopTime = date;
        SharedPreferences.Editor var2 = this.sharedPref.edit();
        boolean var4 = false;
        String stringDatee = date == null ? null : this.dateFormat.format(date);
        var2.putString("sleepstop", stringDatee);
        var2.apply();
    }

    public final boolean isTimerCounting() {
        return this.timerCounting;
    }

    public final void setTimerCountinge(boolean value) {
        this.timerCounting = value;
        SharedPreferences.Editor var2 = this.sharedPref.edit();
        boolean var4 = false;
        var2.putBoolean("sleepcountinue", value);
        var2.apply();
    }
    public Helper(@NotNull Context contextt) {
        Intrinsics.checkNotNullParameter(contextt, "contextt");
        SharedPreferences var10001 = contextt.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        Intrinsics.checkNotNullExpressionValue(var10001, "contextt.getSharedPrefere…ES, Context.MODE_PRIVATE)");
        this.sharedPref = var10001;
        this.dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault());
        this.timerCounting = this.sharedPref.getBoolean("sleepcountinue", false);
        String startString = this.sharedPref.getString("sleepstart", (String)null);
        if (startString != null) {
            try {
                this.startTime = this.dateFormat.parse(startString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        String stopString = this.sharedPref.getString("sleepstop", (String)null);
        if (stopString != null) {
            try {
                this.stopTime = this.dateFormat.parse(stopString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
    public static final class Companionn {
        private Companionn() {
        }
        // $FF: synthetic method
        public Companionn(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
