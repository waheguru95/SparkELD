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

public class OffDutyHelper {

    private SharedPreferences sharedPref;
    private SimpleDateFormat dateFormat;
    private boolean offdutytimerCounting;
    private Date startoffdutyTime;
    private Date stopoffdutyTime;



    @Nullable
    public final Date startoffdutyTime() {
        return this.startoffdutyTime;
    }
    public final void setstartoffdutyTime(@Nullable Date datee) {
        this.startoffdutyTime = datee;
        SharedPreferences.Editor var2 = this.sharedPref.edit();
        boolean var4 = false;
        String stringDatee = datee == null ? null : this.dateFormat.format(datee);
        var2.putString("offdutystart", stringDatee);
        var2.apply();
    }
    @Nullable
    public final Date stopoffdutyTime() {
        return this.stopoffdutyTime;
    }
    public final void setstopoffdutyTime(@Nullable Date datee) {
        this.stopoffdutyTime = datee;
        SharedPreferences.Editor var2 = this.sharedPref.edit();
        boolean var4 = false;
        String stringDatee = datee == null ? null : this.dateFormat.format(datee);
        var2.putString("offdutystop", stringDatee);
        var2.apply();
    }
    public final boolean offdutytimerCounting() {
        return this.offdutytimerCounting;
    }
    public final void setoffdutytimerCounting(boolean valuee) {
        this.offdutytimerCounting = valuee;
        SharedPreferences.Editor var2 = this.sharedPref.edit();
        boolean var4 = false;
        var2.putBoolean("offdutycountinue", valuee);
        var2.apply();
    }
    public OffDutyHelper(@NotNull Context contextt) {
        Intrinsics.checkNotNullParameter(contextt, "contet");
        SharedPreferences var10001 = contextt.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        Intrinsics.checkNotNullExpressionValue(var10001, "contet.getSharedPrefere…ES, Context.MODE_PRIVATE)");
        this.sharedPref = var10001;
        this.dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault());
        this.offdutytimerCounting = this.sharedPref.getBoolean("offdutycountinue", false);
        String startString = this.sharedPref.getString("offdutystart", (String)null);
        if (startString != null) {
            try {
                this.startoffdutyTime = this.dateFormat.parse(startString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        String stopString = this.sharedPref.getString("offdutystop", (String)null);
        if (stopString != null) {
            try {
                this.stopoffdutyTime = this.dateFormat.parse(stopString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void resetBreakTime() {
        this.startoffdutyTime = null;
        this.stopoffdutyTime = null;
        //  this.offdutytimerCounting = false;
        SharedPreferences.Editor editor = this.sharedPref.edit();
        editor.remove("offdutystart");
        editor.remove("offdutystop");
        //  editor.putBoolean("offdutycountinue", false);
        editor.apply();
    }
}
