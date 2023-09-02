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

import kotlin.jvm.internal.Intrinsics;

public class Shifthelper {
    private SharedPreferences sharedPref;
    private SimpleDateFormat dateFormat;
    private boolean shifttimerCounting;
    private Date shiftstartTime;
    private Date shiftstopTime;


    @Nullable
    public final Date shiftstartTime() {
        return this.shiftstartTime;
    }

    public final void setshiftstartTime(@Nullable Date datee) {
        this.shiftstartTime = datee;
        SharedPreferences.Editor var2 = this.sharedPref.edit();
        boolean var4 = false;
        String stringDatee = datee == null ? null : this.dateFormat.format(datee);
        var2.putString("shiftstart", stringDatee);
        var2.apply();
    }

    @Nullable
    public final Date shiftstopTime() {
        return this.shiftstopTime;
    }

    public final void setshiftstopTime(@Nullable Date datee) {
        this.shiftstopTime = datee;
        SharedPreferences.Editor var2 = this.sharedPref.edit();
        boolean var4 = false;
        String stringDatee = datee == null ? null : this.dateFormat.format(datee);
        var2.putString("shiftstop", stringDatee);
        var2.apply();
    }

    public final boolean shifttimerCounting() {
        return this.shifttimerCounting;
    }

    public final void setshifttimerCounting(boolean valuee) {
        this.shifttimerCounting = valuee;
        SharedPreferences.Editor var2 = this.sharedPref.edit();
        boolean var4 = false;
        var2.putBoolean("shiftcontinue", valuee);
        var2.apply();
    }

    public Shifthelper(@NotNull Context contextt) {
        Intrinsics.checkNotNullParameter(contextt, "contete");
        SharedPreferences var10001 = contextt.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        Intrinsics.checkNotNullExpressionValue(var10001, "contete.getSharedPrefere…ES, Context.MODE_PRIVATE)");
        this.sharedPref = var10001;
        this.dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault());
        this.shifttimerCounting = this.sharedPref.getBoolean("shiftcontinue", false);
        String startString = this.sharedPref.getString("shiftstart", (String) null);
        if (startString != null) {
            try {
                this.shiftstartTime = this.dateFormat.parse(startString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        String stopString = this.sharedPref.getString("shiftstop", (String) null);
        if (stopString != null) {
            try {
                this.shiftstopTime = this.dateFormat.parse(stopString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void resetShiftTime() {
        this.shiftstartTime = null;
        this.shiftstopTime = null;
      //  this.shifttimerCounting = false;
        SharedPreferences.Editor editor = this.sharedPref.edit();
        editor.remove("shiftstart");
        editor.remove("shiftstop");
      //  editor.putBoolean("shiftcontinue", false);
        editor.apply();
    }
}
