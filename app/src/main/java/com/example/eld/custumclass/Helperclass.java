package com.example.eld.custumclass;

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

    public String getEmail() {
        return sharedPreferences.getString(EMAIL, "") ;
    }

    public void setEmail(String EMAIL) {
        sharedPreferences.edit().putString(EMAIL,EMAIL).apply();
    }

    public Boolean getFirstLogin() {
        return sharedPreferences.getBoolean(FIRST_LOGIN, false) ;
    }

    public void setFirstLogin(boolean firstLogin) {
        sharedPreferences.edit().putBoolean(FIRST_LOGIN,firstLogin).apply();
    }

    public boolean getDASHBOARD() {
        return sharedPreferences.getBoolean(DASHBOARD, false) ;
    }

    public void setDASHBOARD(boolean dashboard) {
        sharedPreferences.edit().putBoolean(DASHBOARD,dashboard).apply();
    }

    public boolean getIS_DRIVING() {
        return sharedPreferences.getBoolean(IS_DRIVING, false) ;
    }

    public void setIS_DRIVING(boolean is_driving) {
        sharedPreferences.edit().putBoolean(IS_DRIVING,is_driving).apply();
    }

    public String getSaveStatus() {
        return sharedPreferences.getString(SAVE_STATUS, "") ;
    }

    public void setSaveStatus(String SAVE_STATUS) {
        sharedPreferences.edit().putString(SAVE_STATUS,SAVE_STATUS).apply();
    }

    public String getStatusChange() {
        return sharedPreferences.getString(STATUS_CHANGE, "") ;
    }

    public void setStatusChange(boolean statusChange) {
        sharedPreferences.edit().putBoolean(STATUS_CHANGE,statusChange).apply();
    }

    public String getID() {
        return sharedPreferences.getString(ID, "") ;
    }

    public void setId(String ID) {
        sharedPreferences.edit().putString(ID,ID).apply();
    }

    public boolean getNetworkInfo() {
        return sharedPreferences.getBoolean(NETWORK_INFO, true) ;
    }

    public void setNetworkInfo(boolean NETWORK_INFO) {
        sharedPreferences.edit().putBoolean("NETWORK_INFO",NETWORK_INFO).apply();
    }

    public Long getSPEED_START_TIME() {
        return sharedPreferences.getLong(SPEED_START_TIME, 0) ;
    }

    public void setSPEED_START_TIME(Long speed_start_time) {
        sharedPreferences.edit().putLong(SPEED_START_TIME,speed_start_time).apply();
    }
    public Gson getGson() {
        return gson;
    }

    /**
     * package com.example.eld.custumclass;
     *
     * import android.app.Activity;
     * import android.content.Context;
     * import android.content.SharedPreferences;
     * import android.net.ConnectivityManager;
     * import android.net.NetworkInfo;
     *
     * public class Helperclass {
     *     Activity activity;
     *
     *
     *     public static void setAuthenticToken(String name, Activity sActivity) {
     *         SharedPreferences pref = sActivity.getSharedPreferences(Constants.MY_PREFRENCES, Context.MODE_PRIVATE);
     *         SharedPreferences.Editor editor = pref.edit();
     *         editor.putString("app_key", name);
     *         editor.apply();
     *     }
     *
     *     public static String getAuthenticToken(Context sActivity) {
     *         SharedPreferences pref = sActivity.getSharedPreferences(Constants.MY_PREFRENCES, Context.MODE_PRIVATE);
     *         return pref.getString("app_key", "");
     *     }
     *
     *     public static void setStartlattitube(String late, Activity sActivity) {
     *         SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
     *         SharedPreferences.Editor editor = pref.edit();
     *         editor.putString("lattitube", late);
     *         editor.apply();
     *     }
     *
     *     public static String getStartlattitube(Activity sActivity) {
     *         SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
     *         return pref.getString("lattitube", "");
     *     }
     *
     *     public static void setStartlogitude(String longi, Activity sActivity) {
     *         SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
     *         SharedPreferences.Editor editor = pref.edit();
     *         editor.putString("logitude", longi);
     *         editor.apply();
     *     }
     *
     *     public static String getStartlogitude(Activity sActivity) {
     *         SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
     *         return pref.getString("logitude", "");
     *     }
     *
     *     public static void setStartOdometer(String odo, Activity sActivity) {
     *         SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
     *         SharedPreferences.Editor editor = pref.edit();
     *         editor.putString("Odometer", odo);
     *         editor.apply();
     *     }
     *
     *     public static String getStartOdometer(Activity sActivity) {
     *         SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
     *         return pref.getString("Odometer", "");
     *     }
     *
     *
     *     public static void setEmail(String driverid, Activity sActivity) {
     *         SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
     *         SharedPreferences.Editor editor = pref.edit();
     *         editor.putString("driverid", driverid);
     *         editor.apply();
     *     }
     *
     *     public static String getEmail(Activity sActivity) {
     *         SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
     *         return pref.getString("driverid", "");
     *     }
     *
     *     public static void setFirstLogin(boolean firstlogin, Activity sActivity) {
     *         SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
     *         SharedPreferences.Editor editor = pref.edit();
     *         editor.putBoolean("firstlogin", firstlogin);
     *         editor.apply();
     *     }
     *
     *     public static boolean getFirstLogin(Activity sActivity) {
     *         SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
     *         return pref.getBoolean("firstlogin", false);
     *     }
     *
     *
     *     public static void setDeshBoard(boolean DeshBoard, Activity sActivity) {
     *         SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
     *         SharedPreferences.Editor editor = pref.edit();
     *         editor.putBoolean("DeshBoard", DeshBoard);
     *         editor.apply();
     *     }
     *
     *     public static boolean getDeshBoard(Activity sActivity) {
     *         SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
     *         return pref.getBoolean("DeshBoard", false);
     *     }
     *
     *
     *     public static void setisDriving(boolean isDriving, Activity sActivity) {
     *         SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
     *         SharedPreferences.Editor editor = pref.edit();
     *         editor.putBoolean("isDriving", isDriving);
     *         editor.apply();
     *     }
     *
     *     public static boolean getisDriving(Activity sActivity) {
     *         SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
     *         return pref.getBoolean("isDriving", false);
     *     }
     *
     *     public static void setlastIsDriving(boolean isDriving, Activity sActivity) {
     *         SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
     *         SharedPreferences.Editor editor = pref.edit();
     *         editor.putBoolean("lastIsDriving", isDriving);
     *         editor.apply();
     *     }
     *
     *     public static boolean getlastIsDriving(Activity sActivity) {
     *         SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
     *         return pref.getBoolean("lastIsDriving", false);
     *     }
     *
     *     public static void setSaveStatus(String typestatus, Activity sActivity) {
     *         SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
     *         SharedPreferences.Editor editor = pref.edit();
     *         editor.putString("typestatus", typestatus);
     *         editor.apply();
     *     }
     *
     *     public static String getSaveStatus(Activity sActivity) {
     *         SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
     *         return pref.getString("typestatus", "");
     *     }
     *
     *
     *     public static void setSpeedStartTime(Long time, Activity sActivity) {
     *         SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
     *         SharedPreferences.Editor editor = pref.edit();
     *         editor.putLong("zeroSpeedStartTime", time);
     *         editor.apply();
     *     }
     *
     *     public static Long getSpeedStartTime(Activity sActivity) {
     *         SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
     *         return pref.getLong("zeroSpeedStartTime", 0);
     *     }
     *
     *
     *     public static void setStatusChange(boolean status, Activity sActivity) {
     *         SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
     *         SharedPreferences.Editor editor = pref.edit();
     *         editor.putBoolean("status", status);
     *         editor.apply();
     *     }
     *
     *     public static boolean getStatusChange(Activity sActivity) {
     *         SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
     *         return pref.getBoolean("status", false);
     *     }
     *
     *     public static void setid(int id, Activity sActivity) {
     *         SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
     *         SharedPreferences.Editor editor = pref.edit();
     *         editor.putInt("id", id);
     *         editor.apply();
     *     }
     *
     *     public static String getid(Context sActivity) {
     *         SharedPreferences pref = sActivity.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
     *         return pref.getString("id", "");
     *     }
     *
     *     public static boolean getNetworkInfo(Context activity) {
     *         ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
     *         NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
     *         return networkInfo != null && networkInfo.isConnected();
     *     }
     *
     *
     *
     * }
     */


}

