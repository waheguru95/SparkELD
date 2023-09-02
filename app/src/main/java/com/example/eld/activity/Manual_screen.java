package com.example.eld.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.eld.Manual_adpter;
import com.example.eld.Modelclass;
import com.example.eld.R;

import java.util.ArrayList;

public class Manual_screen extends AppCompatActivity {
ImageView manual_back;
RecyclerView rewcycleview;
    ArrayList<Modelclass> list;
    Manual_adpter manual_adpter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_screen);
        manual_back=findViewById(R.id.manual_back);
        rewcycleview=findViewById(R.id.rewcycleview);

        manual_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        list=new ArrayList<>();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rewcycleview.setLayoutManager(linearLayoutManager);
        manual_adpter=new Manual_adpter(this,list);
        rewcycleview.setAdapter(manual_adpter);
        manual_adpter.notifyDataSetChanged();
        insertdata();
    }

    private void insertdata() {
        list.add(new Modelclass("Recording HOS","From the Dashboard of the app tap on the duty status you intend to switch to.\n"+"\n"+"If you start driving the device and application will detect this and automatically switch your status to drive.",R.drawable.manualfirst));
        list.add(new Modelclass("Certifying HOS","In order to certify your HOS, navigate to the certification page by clicking the Certify button at the bottom of your screen. Afterwards select the days you would like to certify from the pending list, then click the CERTIFY button located below the list.",R.drawable.manualtwo));
        list.add(new Modelclass("Viewing HOS","Your HOS history can be viewed from the dashboard by clicking the Logs button at the bottom of the\n" + "\n" +"screen.\n" + "\n" +"In order to switch to previous days just swipe the graph left or right. Your available hours can be viewed\n" + "\n" + "by clicking on the violation markers at the top of your screen. The time elapsed since the last duty status can be viewed below the VIN Number.",R.drawable.manualthree));
        list.add(new Modelclass("eRODS Transfer","In order to create an eRODS Transfer click the Reports button at the bottom of your screen. Select the appropriate transfer method (Web/Email) and fill the comment field with the code provided by the officer conducting the inspection.",R.drawable.manualfour));
        list.add(new Modelclass("Violation Tracking & Warnings","The application will automatically detect HOS violations ahead of time and warn the user as such through a notification and popup on the dashboard.\n\nIf the user is in violation the appropriate status icons will be flashing, and the user will be warned of the active violation(s) when switching to another duty status.",R.drawable.manualfive));
    }
}