package com.example.eld.custumclass;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Helperclass {
    Activity activity;


    public static void setAuthenticToken(String name, Activity sActivity) {
        SharedPreferences pref = sActivity.getSharedPreferences(Constants.MY_PREFRENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("app_key", name);
        editor.apply();
    }

    public static String getAuthenticToken(Context sActivity) {
        SharedPreferences pref = sActivity.getSharedPreferences(Constants.MY_PREFRENCES, Context.MODE_PRIVATE);
        return pref.getString("app_key", "");
    }

    public static void setStartlattitube(String late, Activity sActivity) {
        SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("lattitube", late);
        editor.apply();
    }

    public static String getStartlattitube(Activity sActivity) {
        SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        return pref.getString("lattitube", "");
    }

    public static void setStartlogitude(String longi, Activity sActivity) {
        SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("logitude", longi);
        editor.apply();
    }

    public static String getStartlogitude(Activity sActivity) {
        SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        return pref.getString("logitude", "");
    }

    public static void setStartOdometer(String odo, Activity sActivity) {
        SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("Odometer", odo);
        editor.apply();
    }

    public static String getStartOdometer(Activity sActivity) {
        SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        return pref.getString("Odometer", "");
    }


    public static void setEmail(String driverid, Activity sActivity) {
        SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("driverid", driverid);
        editor.apply();
    }

    public static String getEmail(Activity sActivity) {
        SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        return pref.getString("driverid", "");
    }

    public static void setFirstLogin(boolean firstlogin, Activity sActivity) {
        SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("firstlogin", firstlogin);
        editor.apply();
    }

    public static boolean getFirstLogin(Activity sActivity) {
        SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        return pref.getBoolean("firstlogin", false);
    }


    public static void setDeshBoard(boolean DeshBoard, Activity sActivity) {
        SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("DeshBoard", DeshBoard);
        editor.apply();
    }

    public static boolean getDeshBoard(Activity sActivity) {
        SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        return pref.getBoolean("DeshBoard", false);
    }


    public static void setisDriving(boolean isDriving, Activity sActivity) {
        SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isDriving", isDriving);
        editor.apply();
    }

    public static boolean getisDriving(Activity sActivity) {
        SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        return pref.getBoolean("isDriving", false);
    }

    public static void setlastIsDriving(boolean isDriving, Activity sActivity) {
        SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("lastIsDriving", isDriving);
        editor.apply();
    }

    public static boolean getlastIsDriving(Activity sActivity) {
        SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        return pref.getBoolean("lastIsDriving", false);
    }

    public static void setSaveStatus(String typestatus, Activity sActivity) {
        SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("typestatus", typestatus);
        editor.apply();
    }

    public static String getSaveStatus(Activity sActivity) {
        SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        return pref.getString("typestatus", "");
    }


    public static void setSpeedStartTime(Long time, Activity sActivity) {
        SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong("zeroSpeedStartTime", time);
        editor.apply();
    }

    public static Long getSpeedStartTime(Activity sActivity) {
        SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        return pref.getLong("zeroSpeedStartTime", 0);
    }


    public static void setStatusChange(boolean status, Activity sActivity) {
        SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("status", status);
        editor.apply();
    }

    public static boolean getStatusChange(Activity sActivity) {
        SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        return pref.getBoolean("status", false);
    }

    public static void setid(int id, Activity sActivity) {
        SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("id", id);
        editor.apply();
    }

    public static String getid(Context sActivity) {
        SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        return pref.getString("id", "");
    }

    public static boolean getNetworkInfo(Context activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }



}

