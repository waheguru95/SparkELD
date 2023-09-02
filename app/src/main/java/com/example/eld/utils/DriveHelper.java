package com.example.eld.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.eld.custumclass.Constants;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import kotlin.jvm.internal.Intrinsics;

public class DriveHelper {
    private SharedPreferences sharedPref;
    private SimpleDateFormat dateFormat;
    private boolean drivetimerCounting;
    private Date startdriveTime;
    private Date stopdriveTime;

    @Nullable
    public final Date startdriveTime() {
        return this.startdriveTime;
    }


    public final void setstartdriveTime(@Nullable Date date) {
        this.startdriveTime = date;
        SharedPreferences.Editor var2 = this.sharedPref.edit();
        boolean var4 = false;
        String stringDate = date == null ? null : this.dateFormat.format(date);
        var2.putString("startdrive", stringDate);
        var2.apply();
    }

    @Nullable
    public final Date stopdriveTime() {
        return this.stopdriveTime;
    }

    public final void setstopdriveTime(@Nullable Date date) {
        this.stopdriveTime = date;
        SharedPreferences.Editor var2 = this.sharedPref.edit();
        boolean var4 = false;
        String stringDate = date == null ? null : this.dateFormat.format(date);
        var2.putString("stopdrive", stringDate);
        var2.apply();
    }

    public final boolean drivetimerCounting() {
        return this.drivetimerCounting;
    }

    public final void setdrivetimerCounting(boolean value) {
        this.drivetimerCounting = value;
        SharedPreferences.Editor var2 = this.sharedPref.edit();
        boolean var4 = false;
        var2.putBoolean("countingdrive", value);
        var2.apply();
    }

    public DriveHelper(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        SharedPreferences var10001 = context.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        Intrinsics.checkNotNullExpressionValue(var10001, "context.getSharedPrefere…ES, Context.MODE_PRIVATE)");
        this.sharedPref = var10001;
        this.dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault());
        this.drivetimerCounting = this.sharedPref.getBoolean("countingdrive", false);
        String startString = this.sharedPref.getString("startdrive", (String) null);
        if (startString != null) {
            try {
                this.startdriveTime = this.dateFormat.parse(startString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        String stopString = this.sharedPref.getString("stopdrive", (String) null);
        if (stopString != null) {
            try {
                this.stopdriveTime = this.dateFormat.parse(stopString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
    public void resetDriveTime() {
        this.startdriveTime = null;
        this.stopdriveTime = null;
     //   this.drivetimerCounting = false;
        SharedPreferences.Editor editor = this.sharedPref.edit();
        editor.remove("startdrive");
        editor.remove("stopdrive");
    //    editor.putBoolean("countingdrive", false);
        editor.apply();
    }

}
