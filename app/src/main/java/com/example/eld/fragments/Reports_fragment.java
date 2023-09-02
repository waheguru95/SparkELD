package com.example.eld.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eld.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Reports_fragment extends Fragment  {
CardView card1back,card1front,card2back,card2front;
TextView card1text,card2text,dateandtime;
    private Handler handler = new Handler();
    private Runnable runnable;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reports_fragment, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        card1back=view.findViewById(R.id.card1back);
        card1front=view.findViewById(R.id.card1front);
        card1text=view.findViewById(R.id.card1text);
        card2back=view.findViewById(R.id.card2back);
        card2front=view.findViewById(R.id.card2front);
        card2text=view.findViewById(R.id.card2text);
        dateandtime=view.findViewById(R.id.dateandtime);

runnable=new Runnable() {
    @Override
    public void run() {
        try {
            handler.postDelayed(this, 1000);
            dateandtime.setText(getDateTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
};
        handler.postDelayed(runnable, 0);

        card1front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card1front.setCardBackgroundColor(Color.rgb(237,102,59));
                card1back.setCardBackgroundColor(Color.rgb(237,102,59));
                card1text.setTextColor(Color.rgb(255,255,255));
                card2back.setCardBackgroundColor(Color.rgb(237,102,59));
                card2front.setCardBackgroundColor(Color.rgb(255,255,255));
                card2text.setTextColor(Color.rgb(0,0,0));
            }
        });
        card2front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card1front.setCardBackgroundColor(Color.rgb(255,255,255));
                card1back.setCardBackgroundColor(Color.rgb(237,102,59));
                card1text.setTextColor(Color.rgb(0,0,0));
                card2back.setCardBackgroundColor(Color.rgb(237,102,59));
                card2front.setCardBackgroundColor(Color.rgb(237,102,59));
                card2text.setTextColor(Color.rgb(255,255,255));
            }
        });
    }
    private String getDateTime() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-05:00"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aa");

        date.setTimeZone(TimeZone.getTimeZone("GMT-05:00"));
        return date.format(currentLocalTime);
    }


}