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

public class PersonalHelper {
    private SharedPreferences sharedPref;
    private SimpleDateFormat dateFormat;
    private boolean ptimerCounting;
    private Date pstartTimeee;
    private Date pstopTimeee;


    @NotNull
    public static final PersonalHelper.Companionpnen Companion = new PersonalHelper.Companionpnen((DefaultConstructorMarker)null);

    @Nullable
    public final Date pstartTimee() {
        return this.pstartTimeee;
    }
    public final void psetStartTimee(@Nullable Date datee) {
        this.pstartTimeee = datee;
        SharedPreferences.Editor var2 = this.sharedPref.edit();
        boolean var4 = false;
        String stringDatee = datee == null ? null : this.dateFormat.format(datee);
        var2.putString("pstart", stringDatee);
        var2.apply();
    }
    @Nullable
    public final Date pstopTimee() {
        return this.pstopTimeee;
    }
    public final void psetStopTimee(@Nullable Date datee) {
        this.pstopTimeee = datee;
        SharedPreferences.Editor var2 = this.sharedPref.edit();
        boolean var4 = false;
        String stringDatee = datee == null ? null : this.dateFormat.format(datee);
        var2.putString("pstop", stringDatee);
        var2.apply();
    }
    public final boolean ptimerCountinge() {
        return this.ptimerCounting;
    }
    public final void psetTimerCountinge(boolean valuee) {
        this.ptimerCounting = valuee;
        SharedPreferences.Editor var2 = this.sharedPref.edit();
        boolean var4 = false;
        var2.putBoolean("pcontinue", valuee);
        var2.apply();
    }
    public PersonalHelper(@NotNull Context contextt) {
        Intrinsics.checkNotNullParameter(contextt, "contetep");
        SharedPreferences var10001 = contextt.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        Intrinsics.checkNotNullExpressionValue(var10001, "contetep.getSharedPrefere…ES, Context.MODE_PRIVATE)");
        this.sharedPref = var10001;
        this.dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault());
        this.ptimerCounting = this.sharedPref.getBoolean("pcontinue", false);
        String startString = this.sharedPref.getString("pstart", (String)null);
        if (startString != null) {
            try {
                this.pstartTimeee = this.dateFormat.parse(startString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        String stopString = this.sharedPref.getString("pstop", (String)null);
        if (stopString != null) {
            try {
                this.pstopTimeee = this.dateFormat.parse(stopString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
    public static final class Companionpnen {
        private Companionpnen() {
        }
        // $FF: synthetic method
        public Companionpnen(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
