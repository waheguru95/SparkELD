package com.example.eld.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.eld.LoginActivity;
import com.example.eld.R;
import com.example.eld.activity.About_screen;
import com.example.eld.activity.Connect_with_bluetooth;
import com.example.eld.activity.DashBoardScreen;
import com.example.eld.activity.Dvir2_screen;
import com.example.eld.activity.Manual_screen;
import com.example.eld.activity.Setting_screen;
import com.example.eld.utils.Constants;
import com.example.eld.utils.Helperclass;
import com.google.android.material.button.MaterialButton;


public class SideNavigationFragment extends Fragment {
    CardView dashboard, dvir, connectivity, setting, manual, about, layLogout, exitapp;
    Dialog logoutDialog;
    TextView deshboardtext, logouttext, exittext;
    ImageView deshboardedit, logoutedit, exitedit;
    TextView driveerid, drivername;
    Helperclass helperClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_side_menu_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dashboard = view.findViewById(R.id.dashboard);
        dvir = view.findViewById(R.id.dvir);
        connectivity = view.findViewById(R.id.conectivity);
        setting = view.findViewById(R.id.setting);
        manual = view.findViewById(R.id.manual);
        about = view.findViewById(R.id.about);
        layLogout = view.findViewById(R.id.logout);
        exitapp = view.findViewById(R.id.exitapp);
        deshboardtext = view.findViewById(R.id.dashboardtext);
        logouttext = view.findViewById(R.id.logouttext);
        exittext = view.findViewById(R.id.exittext);
        deshboardedit = view.findViewById(R.id.deshboardedit);
        logoutedit = view.findViewById(R.id.logoutedit);
        exitedit = view.findViewById(R.id.exitedit);
        driveerid = view.findViewById(R.id.driveerid);
        drivername = view.findViewById(R.id.drivername);
        helperClass = new Helperclass(getSharedPref(requireActivity()));
        driveerid.setText(helperClass.getEmail());

        setListeners();
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DashBoardScreen.class));
                dashboard.setCardBackgroundColor(Color.rgb(237, 102, 59));
                layLogout.setCardBackgroundColor(Color.rgb(255, 255, 255));
                exitapp.setCardBackgroundColor(Color.rgb(255, 255, 255));
                deshboardtext.setTextColor(Color.rgb(255, 255, 255));
                exittext.setTextColor(Color.rgb(0, 0, 0));
                logouttext.setTextColor(Color.rgb(0, 0, 0));
                deshboardedit.setColorFilter(Color.rgb(255, 255, 255));
                logoutedit.setColorFilter(Color.rgb(237, 102, 59));
                exitedit.setColorFilter(Color.rgb(237, 102, 59));
            }
        });
        dvir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Dvir2_screen.class));
            }
        });
        connectivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Connect_with_bluetooth.class));
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Setting_screen.class));
            }
        });
        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Manual_screen.class));
            }
        });

    }

    private void setListeners() {
        layLogout.setOnClickListener(v -> {
            openLogoutDialog(true);
        });

        about.setOnClickListener(v -> {
           moveToAboutUs();
        });

        exitapp.setOnClickListener(v -> {
            openLogoutDialog(false);
        });

    }

    private void moveToAboutUs() {
        startActivity(new Intent(getActivity(), About_screen.class));
    }
    private void openLogoutDialog(boolean isLogout) {
        logoutDialog = new Dialog(requireActivity());
        logoutDialog.setContentView(R.layout.dialog_logout);
        logoutDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Get the screen width
        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int desiredWidth = (int) (screenWidth * 0.9); // Adjust the percentage as needed

        MaterialButton btnLogout = logoutDialog.findViewById(R.id.btnLogout);
        MaterialButton btnCancel = logoutDialog.findViewById(R.id.btnCancel);
        TextView text = logoutDialog.findViewById(R.id.logoputtext);
        if (isLogout) {
            text.setText(R.string.are_you_sure_you_want_to_logout);
        } else {
            text.setText(R.string.do_you_want_to_exit_this_app);
        }

        btnLogout.setOnClickListener(v1 -> {
            if (isLogout) {
                moveToLogin();
            } else {
                requireActivity().finish();
                System.exit(0);
            }
        });
        btnCancel.setOnClickListener(v12 -> {
            logoutDialog.dismiss();
        });
        logoutDialog.setCancelable(false);
        logoutDialog.show();

        Window window = logoutDialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());
            layoutParams.width = desiredWidth;
            window.setAttributes(layoutParams);
        }

    }
    private void moveToLogin() {
        SharedPreferences.Editor sharedPreferences = requireActivity().getSharedPreferences(Constants.MY_PREFRENCES, Context.MODE_PRIVATE).edit();
        sharedPreferences.clear();
        sharedPreferences.apply();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        requireActivity().startActivity(intent);
        logoutDialog.dismiss();
        requireActivity().finish();
    }

    private SharedPreferences getSharedPref(Context context) {
        return context.getSharedPreferences("ELD", Context.MODE_PRIVATE);
    }

}