package com.example.eld.helperclass;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.eld.custumclass.Constants;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public class Breakhelper {

    private SharedPreferences sharedPref;
    private SimpleDateFormat dateFormat;
    private boolean breaktimerCounting;
    private Date startbreakTime;
    private Date stopbreakTime;



    @Nullable
    public final Date startbreakTime() {
        return this.startbreakTime;
    }
    public final void setstartbreakTime(@Nullable Date datee) {
        this.startbreakTime = datee;
        SharedPreferences.Editor var2 = this.sharedPref.edit();
        boolean var4 = false;
        String stringDatee = datee == null ? null : this.dateFormat.format(datee);
        var2.putString("breakstart", stringDatee);
        var2.apply();
    }
    @Nullable
    public final Date stopbreakTime() {
        return this.stopbreakTime;
    }
    public final void setstopbreakTime(@Nullable Date datee) {
        this.stopbreakTime = datee;
        SharedPreferences.Editor var2 = this.sharedPref.edit();
        boolean var4 = false;
        String stringDatee = datee == null ? null : this.dateFormat.format(datee);
        var2.putString("breakstop", stringDatee);
        var2.apply();
    }
    public final boolean breaktimerCounting() {
        return this.breaktimerCounting;
    }
    public final void setbreaktimerCounting(boolean valuee) {
        this.breaktimerCounting = valuee;
        SharedPreferences.Editor var2 = this.sharedPref.edit();
        boolean var4 = false;
        var2.putBoolean("breakcountinue", valuee);
        var2.apply();
    }
    public Breakhelper(@NotNull Context contextt) {
        Intrinsics.checkNotNullParameter(contextt, "contet");
        SharedPreferences var10001 = contextt.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        Intrinsics.checkNotNullExpressionValue(var10001, "contet.getSharedPrefere…ES, Context.MODE_PRIVATE)");
        this.sharedPref = var10001;
        this.dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault());
        this.breaktimerCounting = this.sharedPref.getBoolean("breakcountinue", false);
        String startString = this.sharedPref.getString("breakstart", (String)null);
        if (startString != null) {
            try {
                this.startbreakTime = this.dateFormat.parse(startString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        String stopString = this.sharedPref.getString("breakstop", (String)null);
        if (stopString != null) {
            try {
                this.stopbreakTime = this.dateFormat.parse(stopString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void resetBreakTime() {
        this.startbreakTime = null;
        this.stopbreakTime = null;
      //  this.breaktimerCounting = false;
        SharedPreferences.Editor editor = this.sharedPref.edit();
        editor.remove("breakstart");
        editor.remove("breakstop");
      //  editor.putBoolean("breakcountinue", false);
        editor.apply();
    }
}
