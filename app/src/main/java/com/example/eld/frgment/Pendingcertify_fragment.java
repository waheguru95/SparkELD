package com.example.eld.frgment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eld.Certifyadpter;
import com.example.eld.Modelmy;
import com.example.eld.R;
import com.example.eld.activity.Deshboard_screen;
import com.iosix.eldblelib.EldBroadcast;
import com.iosix.eldblelib.EldBroadcastTypes;

import java.util.ArrayList;


public class Pendingcertify_fragment extends Fragment {

//ListView list_data;
//    private ArrayList<Modelmy> modelArrayList;
//    private Certifyadpter customAdapter;
//CardView certifydone;
CheckBox check_box;
    public static String[] array=new String[]{"November 08 2022","November 09 2022","November 10 2022","November 11 2022","November 12 2022","November 13 2022","November 14 2022"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pendingcertify_fragment, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        list_data=view.findViewById(R.id.list_data);
//        certifydone=view.findViewById(R.id.certifydone);
        check_box=view.findViewById(R.id.check_box);


//        modelArrayList = getModel(false);
//        customAdapter = new Certifyadpter(getActivity(), modelArrayList);
//        list_data.setAdapter(customAdapter);


//        certifydone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//        String itemselect="";
//                for (int i=0;i<list_data.getCount();i++){
//                    if (list_data.isSelected()){
//                        itemselect+=list_data.getItemAtPosition(i)+"\n";
//                    }
//                }
//        if (itemselect.equals("")){
//            Toast.makeText(getContext(), "Select report", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(getContext(), itemselect, Toast.LENGTH_SHORT).show();
//        }
//    }
//        });
//        check_box.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                if(check_box.isChecked())
//                {
//                    modelArrayList = getModel(true);
//                    customAdapter = new Certifyadpter(getActivity(), modelArrayList);
//                    list_data.setAdapter(customAdapter);
//                }
//                if(!check_box.isChecked())
//                {
//                    modelArrayList = getModel(false);
//                    customAdapter = new Certifyadpter(getActivity(), modelArrayList);
//                    list_data.setAdapter(customAdapter);
//                }
//
//            }
//        });
    }
    private ArrayList<Modelmy> getModel(boolean isSelect) {
        ArrayList<Modelmy> list = new ArrayList<>();
        for (int i = 0; i <7; i++) {
            Modelmy model = new Modelmy();
            model.setSelected(isSelect);
            model.setPlayer(array[i]);
            list.add(model);
        }
        return list;
    }



}