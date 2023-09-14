package com.example.eld.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class Side_menu_fragment extends Fragment {

    CardView dashboard, dvir, connectivity, setting, manual, about, logout, exitapp;
    Dialog confirmation_logout;
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
        logout = view.findViewById(R.id.logout);
        exitapp = view.findViewById(R.id.exitapp);
        deshboardtext = view.findViewById(R.id.dashboardtext);
        logouttext = view.findViewById(R.id.logouttext);
        exittext = view.findViewById(R.id.exittext);
        deshboardedit = view.findViewById(R.id.deshboardedit);
        logoutedit = view.findViewById(R.id.logoutedit);
        exitedit = view.findViewById(R.id.exitedit);
        driveerid = view.findViewById(R.id.driveerid);
        drivername = view.findViewById(R.id.drivername);
        confirmation_logout = new Dialog(getContext());
        helperClass = new Helperclass(getSharedPref(getActivity()));
        driveerid.setText(helperClass.getEmail());

        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DashBoardScreen.class));
                dashboard.setCardBackgroundColor(Color.rgb(237, 102, 59));
                logout.setCardBackgroundColor(Color.rgb(255, 255, 255));
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
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), About_screen.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboard.setCardBackgroundColor(Color.rgb(255, 255, 255));
                logout.setCardBackgroundColor(Color.rgb(237, 102, 59));
                exitapp.setCardBackgroundColor(Color.rgb(255, 255, 255));
                deshboardtext.setTextColor(Color.rgb(0, 0, 0));
                exittext.setTextColor(Color.rgb(0, 0, 0));
                logouttext.setTextColor(Color.rgb(255, 255, 255));
                deshboardedit.setColorFilter(Color.rgb(237, 102, 59));
                logoutedit.setColorFilter(Color.rgb(255, 255, 255));
                exitedit.setColorFilter(Color.rgb(237, 102, 59));


                confirmation_logout.setContentView(R.layout.logout_dilog);
                confirmation_logout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                CardView yes = confirmation_logout.findViewById(R.id.yeslogout);
                CardView no = confirmation_logout.findViewById(R.id.no);
                TextView text = confirmation_logout.findViewById(R.id.logoputtext);
                text.setText("Do you want to logout?");

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        SharedPreferences.Editor sharedPreferences = getActivity().getSharedPreferences(Constants.MY_PREFRENCES, Context.MODE_PRIVATE).edit();
                        if (sharedPreferences != null) {
                            sharedPreferences.clear();
                            sharedPreferences.apply();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            getActivity().startActivity(intent);
                            confirmation_logout.dismiss();
                            getActivity().finish();
                        }
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmation_logout.dismiss();
                        dashboard.setCardBackgroundColor(Color.rgb(237, 102, 59));
                        logout.setCardBackgroundColor(Color.rgb(255, 255, 255));
                        deshboardtext.setTextColor(Color.rgb(255, 255, 255));
                        logouttext.setTextColor(Color.rgb(0, 0, 0));
                        deshboardedit.setColorFilter(Color.rgb(255, 255, 255));
                        logoutedit.setColorFilter(Color.rgb(237, 102, 59));
                    }
                });
                confirmation_logout.setCancelable(false);
                confirmation_logout.show();

            }
        });
        exitapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboard.setCardBackgroundColor(Color.rgb(255, 255, 255));
                logout.setCardBackgroundColor(Color.rgb(255, 255, 255));
                exitapp.setCardBackgroundColor(Color.rgb(237, 102, 59));
                deshboardtext.setTextColor(Color.rgb(0, 0, 0));
                exittext.setTextColor(Color.rgb(255, 255, 255));
                logouttext.setTextColor(Color.rgb(0, 0, 0));
                deshboardedit.setColorFilter(Color.rgb(237, 102, 59));
                logoutedit.setColorFilter(Color.rgb(237, 102, 59));
                exitedit.setColorFilter(Color.rgb(255, 255, 255));

                confirmation_logout.setContentView(R.layout.logout_dilog);
                confirmation_logout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                CardView yes = confirmation_logout.findViewById(R.id.yeslogout);
                CardView no = confirmation_logout.findViewById(R.id.no);
                TextView text = confirmation_logout.findViewById(R.id.logoputtext);
                text.setText("Do you want to Exit this app?");

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().finish();
                        System.exit(0);
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmation_logout.dismiss();
                        dashboard.setCardBackgroundColor(Color.rgb(237, 102, 59));
                        exitapp.setCardBackgroundColor(Color.rgb(255, 255, 255));
                        deshboardtext.setTextColor(Color.rgb(255, 255, 255));
                        exittext.setTextColor(Color.rgb(0, 0, 0));
                        deshboardedit.setColorFilter(Color.rgb(255, 255, 255));
                        exitedit.setColorFilter(Color.rgb(237, 102, 59));
                    }
                });
                confirmation_logout.setCancelable(false);
                confirmation_logout.show();

            }
        });

    }
    private SharedPreferences getSharedPref(Context context) {
        return context.getSharedPreferences("ELD", Context.MODE_PRIVATE);
    }

}