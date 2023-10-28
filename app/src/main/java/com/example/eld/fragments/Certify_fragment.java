package com.example.eld.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eld.Certifyadpter;
import com.example.eld.PendingLog;
import com.example.eld.R;
import com.example.eld.network.dto.login.response.AttendanceData;
import com.example.eld.network.dto.login.response.GetLogsModel;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Certify_fragment extends BaseFragment {
    CardView card1back,card1front,card2back,card2front;
    private ArrayList<PendingLog> modelArrayList;
    TextView card1text,card2text;
    RecyclerView recycleview;
    private Certifyadpter customAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_certify_fragment, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycleview = view.findViewById(R.id.recycleview);
        customAdapter = new Certifyadpter(getActivity(), modelArrayList);
        recycleview.setAdapter(customAdapter);

        card1back=view.findViewById(R.id.card1back);
        card1front=view.findViewById(R.id.card1front);
        card1text=view.findViewById(R.id.card1text);
        card2back=view.findViewById(R.id.card2back);
        card2front=view.findViewById(R.id.card2front);
        card2text=view.findViewById(R.id.card2text);
      //  controlfunction(new Pendingcertify_fragment());

        card1front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card1front.setCardBackgroundColor(Color.rgb(237,102,59));
                card1back.setCardBackgroundColor(Color.rgb(237,102,59));
                card1text.setTextColor(Color.rgb(255,255,255));
                card2back.setCardBackgroundColor(Color.rgb(237,102,59));
                card2front.setCardBackgroundColor(Color.rgb(255,255,255));
                card2text.setTextColor(Color.rgb(0,0,0));

               // controlfunction(new Pendingcertify_fragment());

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
               // controlfunction(new Certify_certification_fragment());
                callCertifiedLogs();
            }
        });

    }
//    public  void controlfunction(Fragment fragment){
//        FragmentManager fragmentManager =getChildFragmentManager();
//        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.certificationframe,fragment).commit();
//
//
//    }

    private void callCertifiedLogs() {
        if (helperClass.getNetworkInfo()) {
            showLoader();
            Call<ResponseBody> getCertifiedLogs = apiService
                    .getCertifiedLog(""+helperClass.getID());
            getCertifiedLogs.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        hideLoader();
                        if (response.isSuccessful()) {
                            ResponseBody responseBody = response.body();
                            ArrayList<String> recordDates = new ArrayList<>();
                            if (responseBody != null) {
                                try {
                                    String logsJson = responseBody.string();
                                    GetLogsModel getCertifiedLogsModel = new Gson().fromJson(logsJson, GetLogsModel.class);
                                    int statusCode = getCertifiedLogsModel.getStatusCode();
                                    String message = getCertifiedLogsModel.getMessage();
                                    //boolean status = getPendingLogsModel.isStatus();

                                    if (getCertifiedLogsModel.getData().isEmpty()) {
                                        // emptyView.setVisibility(View.VISIBLE);
                                        //recycleview.setVisibility(View.GONE);
                                    } else {
                                        for (AttendanceData attendanceData : getCertifiedLogsModel.getData()) {
                                            recordDates.add(attendanceData.getRecordDate());
                                        }
//                                        emptyView.setVisibility(View.GONE);
//                                        recycleview.setVisibility(View.VISIBLE);

//                                        List<Record> records = getAttendanceResponseModel.getData();
//                                        for (Record record : records) {
//                                            String VIN = record.getVIN();
//                                            String latitude = record.getLatitude();
//                                            String longitude = record.getLongitude();
//                                            String attendenceType = record.getAttendenceType();
//                                            int userId = record.getUserId();
//                                            String date = record.getDATE();
//
//                                            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//                                            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm");
//                                            inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//
//
//                                            Date fdate = inputFormat.parse(date);
//                                            String formattedDate = outputFormat.format(fdate);
//
//                                            System.out.println("Formatted Date and Time: " + formattedDate);
//
//
//                                            Eventmodel logEvent = new Eventmodel();
//                                            logEvent.setStatus(attendenceType);
//
//
//                                            logEvent.setTime(formattedDate);
//                                            list.add(logEvent);
//
//                          /*      this.x = x;
//                                this.y = y;
//                                this.time = time;
//                                this.graph = graph;
//                                this.status = status;
//                                this.location = location;
//                                this.odometer = odometer;
//                                this.eh = eh;
//                                this.orign = orign;*/
//
//
//                                            System.out.println("VIN: " + VIN);
//                                            System.out.println("Latitude: " + latitude);
//                                            System.out.println("Longitude: " + longitude);
//                                            System.out.println("Attendence Type: " + attendenceType);
//                                            System.out.println("User ID: " + userId);
//                                            System.out.println("Date: " + date);
//                                        }
//                                        event_adpter = new Event_adpter(getContext(), list);
//                                        recycleview.setAdapter(event_adpter);
//                                        event_adpter.notifyDataSetChanged();
//                                        event_adpter = new EventLogsAdapter(getContext(), getAttendanceResponseModel.getData());
//                                        recycleview.setAdapter(event_adpter)
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Certifyadpter customAdapter = new Certifyadpter(getContext(), getModel(recordDates,false)); // replace yourDataList with your actual data
                                recycleview.setLayoutManager(new LinearLayoutManager(getContext()));
                                recycleview.setVisibility(View.VISIBLE);
                                recycleview.setAdapter(customAdapter);
                                customAdapter.notifyDataSetChanged();

                                //   recycleview.setAdapter(customAdapter);
                            }
                        } else {
                            onAPIErrorMessageReceived(response.errorBody().toString());
                        }
                    } catch (Exception e) {
                        hideLoader();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    hideLoader();
                    // Toast.makeText(baseActivity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            showNoInternetConnectionError();
        }
    }

    private ArrayList<PendingLog> getModel(  ArrayList<String> recordDates,boolean isSelect) {
        ArrayList<PendingLog> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            PendingLog model = new PendingLog();
            model.setSelected(isSelect);
            model.setDate(recordDates.get(i));
            list.add(model);
        }
        return list;
    }

}