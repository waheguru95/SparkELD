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

public class Heplper {
    private SharedPreferences sharedPref;
    private SimpleDateFormat dateFormat;
    private boolean timerCounting;
    private Date startTimeee;
    private Date stopTimeee;

    @NotNull
    public static final Heplper.Companionn Companion = new Heplper.Companionn((DefaultConstructorMarker)null);

    @Nullable
    public final Date startTimee() {
        return this.startTimeee;
    }
    public final void setStartTimee(@Nullable Date datee) {
        this.startTimeee = datee;
        SharedPreferences.Editor var2 = this.sharedPref.edit();
        boolean var4 = false;
        String stringDatee = datee == null ? null : this.dateFormat.format(datee);
        var2.putString("sleepstart", stringDatee);
        var2.apply();
    }
    @Nullable
    public final Date stopTimee() {
        return this.stopTimeee;
    }

    public final void setStopTimee(@Nullable Date datee) {
        this.stopTimeee = datee;
        SharedPreferences.Editor var2 = this.sharedPref.edit();
        boolean var4 = false;
        String stringDatee = datee == null ? null : this.dateFormat.format(datee);
        var2.putString("sleepstop", stringDatee);
        var2.apply();
    }

    public final boolean timerCountinge() {
        return this.timerCounting;
    }

    public final void setTimerCountinge(boolean valuee) {
        this.timerCounting = valuee;
        SharedPreferences.Editor var2 = this.sharedPref.edit();
        boolean var4 = false;
        var2.putBoolean("sleepcountinue", valuee);
        var2.apply();
    }
    public Heplper(@NotNull Context contextt) {
        Intrinsics.checkNotNullParameter(contextt, "contextt");
        SharedPreferences var10001 = contextt.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        Intrinsics.checkNotNullExpressionValue(var10001, "contextt.getSharedPrefere…ES, Context.MODE_PRIVATE)");
        this.sharedPref = var10001;
        this.dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault());
        this.timerCounting = this.sharedPref.getBoolean("sleepcountinue", false);
        String startString = this.sharedPref.getString("sleepstart", (String)null);
        if (startString != null) {
            try {
                this.startTimeee = this.dateFormat.parse(startString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        String stopString = this.sharedPref.getString("sleepstop", (String)null);
        if (stopString != null) {
            try {
                this.stopTimeee = this.dateFormat.parse(stopString);
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
