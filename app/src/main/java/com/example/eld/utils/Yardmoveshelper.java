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

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public class Yardmoveshelper {
    private SharedPreferences sharedPref;
    private SimpleDateFormat dateFormat;
    private boolean ytimerCounting;
    private Date ystartTimeee;
    private Date ystopTimeee;

    @NotNull
    public static final Yardmoveshelper.Compaonnen Companion = new Yardmoveshelper.Compaonnen((DefaultConstructorMarker) null);

    @Nullable
    public final Date ystartTimee() {
        return this.ystartTimeee;
    }

    public final void ysetStartTimee(@Nullable Date datee) {
        this.ystartTimeee = datee;
        SharedPreferences.Editor var2 = this.sharedPref.edit();
        boolean var4 = false;
        String stringDatee = datee == null ? null : this.dateFormat.format(datee);
        var2.putString("yardstart", stringDatee);
        var2.apply();
    }

    @Nullable
    public final Date ystopTimee() {
        return this.ystopTimeee;
    }

    public final void ysetStopTimee(@Nullable Date datee) {
        this.ystopTimeee = datee;
        SharedPreferences.Editor var2 = this.sharedPref.edit();
        boolean var4 = false;
        String stringDatee = datee == null ? null : this.dateFormat.format(datee);
        var2.putString("yardstop", stringDatee);
        var2.apply();
    }

    public final boolean ytimerCountinge() {
        return this.ytimerCounting;
    }

    public final void ysetTimerCountinge(boolean valuee) {
        this.ytimerCounting = valuee;
        SharedPreferences.Editor var2 = this.sharedPref.edit();
        boolean var4 = false;
        var2.putBoolean("yardcontinue", valuee);
        var2.apply();
    }

    public Yardmoveshelper(@NotNull Context contextt) {
        Intrinsics.checkNotNullParameter(contextt, "contette");
        SharedPreferences var10001 = contextt.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        Intrinsics.checkNotNullExpressionValue(var10001, "contette.getSharedPrefere…ES, Context.MODE_PRIVATE)");
        this.sharedPref = var10001;
        this.dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault());
        this.ytimerCounting = this.sharedPref.getBoolean("yardcontinue", false);
        String startString = this.sharedPref.getString("yardstart", (String) null);
        if (startString != null) {
            try {
                this.ystartTimeee = this.dateFormat.parse(startString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        String stopString = this.sharedPref.getString("yardstop", (String) null);
        if (stopString != null) {
            try {
                this.ystopTimeee = this.dateFormat.parse(stopString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static final class Compaonnen {
        private Compaonnen() {
        }

        // $FF: synthetic method
        public Compaonnen(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
