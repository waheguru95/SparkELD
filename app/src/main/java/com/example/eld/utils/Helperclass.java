package com.example.eld.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.eld.models.DriverProfileModel;
import com.google.gson.Gson;

public class Helperclass {
    Activity activity;
    private final SharedPreferences sharedPreferences;
    private final String AUTHENTIC_TOKEN = "authentic_token";
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

    private String DRIVER_PROFILE = "driverProfile";
    private String PASSWORD = "password";
    private String REMEMBER_ME = "remember_me";
    private String DRIVER_USER_ID = "driver_user_id";
    private static final String LOGIN_STATUS = "login_status";
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

    public boolean getLoginStatus() {
        return sharedPreferences.getBoolean(LOGIN_STATUS, false);
    }

    public void setLoginStatus(boolean loginStatus) {
        sharedPreferences.edit().putBoolean(LOGIN_STATUS, loginStatus).apply();
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

    public void setEmail(String email) {
        sharedPreferences.edit().putString(EMAIL, email).apply();
    }

    public String getPASSWORD() {
        return sharedPreferences.getString(PASSWORD, "");
    }

    public void setPASSWORD(String password) {
        sharedPreferences.edit().putString(PASSWORD, password).apply();

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

    public int getID() {
        return sharedPreferences.getInt(ID, 0);
    }

    public void setId(int id) {
        sharedPreferences.edit().putInt(ID, id).apply();
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

    public DriverProfileModel getDriverProfile() {
        String data = sharedPreferences.getString(DRIVER_PROFILE, "");
        return new Gson().fromJson(data, DriverProfileModel.class);
    }

    public void setDriverProfile(String driverProfile) {
        sharedPreferences.edit().putString(DRIVER_PROFILE, driverProfile).apply();
    }

    public boolean getREMEMBER_ME() {
        return sharedPreferences.getBoolean(REMEMBER_ME, false);

    }

    public void setREMEMBER_ME(boolean remember_me) {
        sharedPreferences.edit().putBoolean(REMEMBER_ME, remember_me).apply();
    }

    public String getDRIVER_USER_ID() {
        return sharedPreferences.getString(DRIVER_USER_ID, "");

    }

    public void setDRIVER_USER_ID(String driver_user_id) {
        sharedPreferences.edit().putString(DRIVER_USER_ID, driver_user_id).apply();
    }

    public Gson getGson() {
        return gson;
    }
}

