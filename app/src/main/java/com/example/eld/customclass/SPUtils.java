package com.example.eld.customclass;

import android.content.SharedPreferences;
import com.google.gson.Gson;

public class SPUtils {

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

    private final Gson gson = new Gson();

    public SPUtils(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        SharedPreferences.Editor editor = sharedPreferences.edit();
    }

    public String getAUTHENTIC_TOKEN() {
        return sharedPreferences.getString(AUTHENTIC_TOKEN, "") ;
    }

    public void setAUTHENTIC_TOKEN(String AUTHENTIC_TOKEN) {
        sharedPreferences.edit().putString(AUTHENTIC_TOKEN,AUTHENTIC_TOKEN ).apply();
    }

    public String getLATTITUDE() {
        return sharedPreferences.getString(LATTITUDE, "") ;

    }

    public void setLATTITUDE(String LATTITUDE) {
        sharedPreferences.edit().putString(LATTITUDE,LATTITUDE ).apply();
    }

    public String getLONGITUDE() {
        return sharedPreferences.getString(LONGITUDE, "") ;
    }

    public void setLONGITUDE(String LONGITUDE) {
        sharedPreferences.edit().putString(LONGITUDE,LONGITUDE ).apply();
    }

    public String getODOMETER() {
        return sharedPreferences.getString(ODOMETER, "") ;
    }

    public void setODOMETER(String ODOMETER) {
        sharedPreferences.edit().putString(ODOMETER,ODOMETER).apply();
    }

    public String getEMAIL() {
        return sharedPreferences.getString(EMAIL, "") ;
    }

    public void setEMAIL(String EMAIL) {
        sharedPreferences.edit().putString(EMAIL,EMAIL).apply();
    }

    public String getFIRST_LOGIN() {
        return sharedPreferences.getString(FIRST_LOGIN, "") ;
    }

    public void setFIRST_LOGIN(String FIRST_LOGIN) {
        sharedPreferences.edit().putString(FIRST_LOGIN,FIRST_LOGIN).apply();
    }

    public String getDASHBOARD() {
        return sharedPreferences.getString(DASHBOARD, "") ;
    }

    public void setDASHBOARD(String DASHBOARD) {
        sharedPreferences.edit().putString(DASHBOARD,DASHBOARD).apply();
    }

    public String getIS_DRIVING() {
        return sharedPreferences.getString(IS_DRIVING, "") ;
    }

    public void setIS_DRIVING(String IS_DRIVING) {
        sharedPreferences.edit().putString(IS_DRIVING,IS_DRIVING).apply();
    }

    public String getSAVE_STATUS() {
        return sharedPreferences.getString(SAVE_STATUS, "") ;
    }

    public void setSAVE_STATUS(String SAVE_STATUS) {
        sharedPreferences.edit().putString(SAVE_STATUS,SAVE_STATUS).apply();
    }

    public String getSTATUS_CHANGE() {
        return sharedPreferences.getString(STATUS_CHANGE, "") ;
    }

    public void setSTATUS_CHANGE(String STATUS_CHANGE) {
        sharedPreferences.edit().putString(STATUS_CHANGE,STATUS_CHANGE).apply();
    }

    public String getID() {
        return sharedPreferences.getString(ID, "") ;
    }

    public void setID(String ID) {
        sharedPreferences.edit().putString(ID,ID).apply();
    }

    public String getNETWORK_INFO() {
        return sharedPreferences.getString(NETWORK_INFO, "") ;
    }

    public void setNETWORK_INFO(String NETWORK_INFO) {
        sharedPreferences.edit().putString(NETWORK_INFO,NETWORK_INFO).apply();
    }

    public Gson getGson() {
        return gson;
    }
}
