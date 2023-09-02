package com.example.eld.frgment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eld.R;
import com.example.eld.activity.Deshboard_screen;
import com.iosix.eldblelib.EldBroadcast;
import com.iosix.eldblelib.EldBroadcastTypes;


public class Certify_fragment extends Fragment {
    CardView card1back,card1front,card2back,card2front;
    TextView card1text,card2text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_certify_fragment, container, false);
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
        controlfunction(new Pendingcertify_fragment());

        card1front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card1front.setCardBackgroundColor(Color.rgb(237,102,59));
                card1back.setCardBackgroundColor(Color.rgb(237,102,59));
                card1text.setTextColor(Color.rgb(255,255,255));
                card2back.setCardBackgroundColor(Color.rgb(237,102,59));
                card2front.setCardBackgroundColor(Color.rgb(255,255,255));
                card2text.setTextColor(Color.rgb(0,0,0));

                controlfunction(new Pendingcertify_fragment());

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
                controlfunction(new Certify_certification_fragment());
            }
        });
    }
    public  void controlfunction(Fragment fragment){
        FragmentManager fragmentManager =getChildFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.certificationframe,fragment).commit();


    }


}