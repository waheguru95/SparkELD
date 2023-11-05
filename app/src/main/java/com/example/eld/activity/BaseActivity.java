package com.example.eld.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eld.R;
import com.example.eld.network.ApiService;
import com.example.eld.network.RetrofitClient;
import com.example.eld.utils.Helperclass;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

public class BaseActivity extends AppCompatActivity {

    public Helperclass helperClass;
    public ApiService apiService;
    private Dialog apiLoaderDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helperClass = new Helperclass(getSharedPref(this));
        apiService = RetrofitClient.apiService(this).create(ApiService.class);
        initiateApiLoader();
    }

    private void initiateApiLoader() {
        apiLoaderDialog = new Dialog(this);
        apiLoaderDialog.setContentView(R.layout.loadingpopup);
        apiLoaderDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        apiLoaderDialog.setCancelable(false);
    }

    private SharedPreferences getSharedPref(Context context) {
        return context.getSharedPreferences("ELD", Context.MODE_PRIVATE);
    }

    public void onAPIErrorMessageReceived(String errorBody) {
        try {
            JSONObject body = new JSONObject(errorBody);
            Log.e("API Error", ""+body);
            if (body.has("message")) {
                Toast.makeText(getApplicationContext(), "" + body.getString("message"), Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("API Error", e.getMessage().toString());
        }
    }

    public void showLoader() {
        if (apiLoaderDialog != null && !apiLoaderDialog.isShowing()) {
            apiLoaderDialog.show();
        }
    }

    public void hideLoader() {
        if (apiLoaderDialog != null) {
            apiLoaderDialog.dismiss();
        }
    }

    public void showNoInternetConnectionError() {
        Toast.makeText(this, "Check network connection", Toast.LENGTH_SHORT).show();
    }

    public void showToast(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
        //Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();

    }

}
