package com.example.eld.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eld.R;
import com.example.eld.network.ApiService;
import com.example.eld.network.RetrofitClient;
import com.example.eld.utils.Helperclass;

import org.json.JSONException;
import org.json.JSONObject;

public class BaseFragment extends Fragment {


    public Helperclass helperClass;
    public ApiService apiService;
    private Dialog apiLoaderDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        helperClass = new Helperclass(getSharedPref(getContext()));
        apiService = RetrofitClient.apiService(getContext()).create(ApiService.class);
        initiateApiLoader();
    }

    private SharedPreferences getSharedPref(Context context) {
        return context.getSharedPreferences("ELD", Context.MODE_PRIVATE);
    }

    private void initiateApiLoader() {
        apiLoaderDialog = new Dialog(getContext());
        apiLoaderDialog.setContentView(R.layout.loadingpopup);
        apiLoaderDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        apiLoaderDialog.setCancelable(false);
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
        Toast.makeText(getContext(), "Check network connection", Toast.LENGTH_SHORT).show();
    }
    public void onAPIErrorMessageReceived(String errorBody) {
        try {
            JSONObject body = new JSONObject(errorBody);
            if (body.has("message")) {
                Toast.makeText(getActivity(), "" + body.getString("message"), Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("API Error", e.getMessage().toString());
        }
    }
}
