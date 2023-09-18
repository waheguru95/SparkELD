package com.example.eld.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class Helperclass {
    Activity activity;
    private final SharedPreferences sharedPreferences;
    private String AUTHENTIC_TOKEN = "authentic_token";
    private String LATTITUDE = "lattitude";
    private String LONGITUDE = "longitude";
    private String ODOMETER = "odometer";
    private String EMAIL = "email";
    private String FIRST_LOGIN = "first_login";
    private String DASHBOARD = "dashboard";
    private String IS_DRIVING = "is_driving";
    private String SAVE_STATUS = "save_status";
    private String STATUS_CHANGE = "status_Change";
    private String ID = "id";
    private String NETWORK_INFO = "network_info";


    private String SPEED_START_TIME = "speed_start_time";
    private final Gson gson = new Gson();

    public Helperclass(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        SharedPreferences.Editor editor = sharedPreferences.edit();
    }

    public String getAuthenticToken() {
        return sharedPreferences.getString(AUTHENTIC_TOKEN, "");
    }

    public void setAUTHENTIC_TOKEN(String AUTHENTIC_TOKEN) {
        sharedPreferences.edit().putString(AUTHENTIC_TOKEN, AUTHENTIC_TOKEN).apply();
    }

    public String getLATTITUDE() {
        return sharedPreferences.getString(LATTITUDE, "");

    }

    public void setLATTITUDE(String LATTITUDE) {
        sharedPreferences.edit().putString(LATTITUDE, LATTITUDE).apply();
    }

    public String getLONGITUDE() {
        return sharedPreferences.getString(LONGITUDE, "");
    }

    public void setLONGITUDE(String LONGITUDE) {
        sharedPreferences.edit().putString(LONGITUDE, LONGITUDE).apply();
    }

    public String getODOMETER() {
        return sharedPreferences.getString(ODOMETER, "");
    }

    public void setODOMETER(String ODOMETER) {
        sharedPreferences.edit().putString(ODOMETER, ODOMETER).apply();
    }

    public String getEmail() {
        return sharedPreferences.getString(EMAIL, "");
    }

    public void setEmail(String EMAIL) {
        sharedPreferences.edit().putString(EMAIL, EMAIL).apply();
    }

    public Boolean getFirstLogin() {
        return sharedPreferences.getBoolean(FIRST_LOGIN, false);
    }

    public void setFirstLogin(boolean firstLogin) {
        sharedPreferences.edit().putBoolean(FIRST_LOGIN, firstLogin).apply();
    }

    public boolean getDASHBOARD() {
        return sharedPreferences.getBoolean(DASHBOARD, false);
    }

    public void setDASHBOARD(boolean dashboard) {
        sharedPreferences.edit().putBoolean(DASHBOARD, dashboard).apply();
    }

    public boolean getIS_DRIVING() {
        return sharedPreferences.getBoolean(IS_DRIVING, false);
    }

    public void setIS_DRIVING(boolean is_driving) {
        sharedPreferences.edit().putBoolean(IS_DRIVING, is_driving).apply();
    }

    public String getSaveStatus() {
        return sharedPreferences.getString(SAVE_STATUS, "");
    }

    public void setSaveStatus(String SAVE_STATUS) {
        sharedPreferences.edit().putString(SAVE_STATUS, SAVE_STATUS).apply();
    }

    public String getStatusChange() {
        return sharedPreferences.getString(STATUS_CHANGE, "");
    }

    public void setStatusChange(boolean statusChange) {
        sharedPreferences.edit().putBoolean(STATUS_CHANGE, statusChange).apply();
    }

    public String getID() {
        return sharedPreferences.getString(ID, "");
    }

    public void setId(String ID) {
        sharedPreferences.edit().putString(ID, ID).apply();
    }

    public boolean getNetworkInfo() {
        return sharedPreferences.getBoolean(NETWORK_INFO, true);
    }

    public void setNetworkInfo(boolean NETWORK_INFO) {
        sharedPreferences.edit().putBoolean("NETWORK_INFO", NETWORK_INFO).apply();
    }

    public Long getSPEED_START_TIME() {
        return sharedPreferences.getLong(SPEED_START_TIME, 0);
    }

    public void setSPEED_START_TIME(Long speed_start_time) {
        sharedPreferences.edit().putLong(SPEED_START_TIME, speed_start_time).apply();
    }

    public Gson getGson() {
        return gson;
    }
}

