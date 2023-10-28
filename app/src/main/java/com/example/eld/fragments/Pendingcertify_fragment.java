package com.example.eld.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

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


public class Pendingcertify_fragment extends BaseFragment {

    ListView list_data;
    private ArrayList<PendingLog> modelArrayList;
    private Certifyadpter customAdapter;
    CardView certifydone;
    RecyclerView recycleview;
    CheckBox check_box;
    ArrayList<String> recordDates = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pendingcertify_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycleview = view.findViewById(R.id.recycleview);
        //recycleview.setLayoutManager(new LinearLayoutManager(getContext()));

        //  customAdapter = new Certifyadpter(getActivity(), modelArrayList);
        //recycleview.setAdapter(customAdapter);
        check_box = view.findViewById(R.id.check_box);
        callGetPendingLogs();

        //modelArrayList = getModel(false);
      /* certifydone.setOnClickListener((View.OnClickListener) v -> {
            String itemselect = "";
            for (int i = 0; i < recycleview.getChildCount(); i++) {
                if (list_data.isSelected()) {
                    itemselect += recycleview.getChildAt(i) + "\n";
                }
            }
            if (itemselect.equals("")) {
                Toast.makeText(getContext(), "Select report", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), itemselect, Toast.LENGTH_SHORT).show();
            }
        });*/
//        check_box.setOnClickListener(v -> {
//            if (check_box.isChecked()) {
//                modelArrayList = getModel(recordDates, true);
//                customAdapter = new Certifyadpter(getActivity(), modelArrayList);
//                recycleview.setAdapter(customAdapter);
//            }
//            if (!check_box.isChecked()) {
//                modelArrayList = getModel(recordDates, false);
//                customAdapter = new Certifyadpter(getActivity(), modelArrayList);
//                recycleview.setAdapter(customAdapter);
//            }
//
//        });
    }

    private ArrayList<PendingLog> getModel(ArrayList<String> recordDates, boolean isSelect) {
        ArrayList<PendingLog> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            PendingLog model = new PendingLog();
            model.setSelected(isSelect);
            model.setDate(recordDates.get(i));
            list.add(model);
        }
        return list;
    }

    private void callGetPendingLogs() {
        if (helperClass.getNetworkInfo()) {
            showLoader();
            Call<ResponseBody> getPendingLogCall = apiService
                    .getPendingLog("" + helperClass.getID());
            getPendingLogCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        hideLoader();
                        if (response.isSuccessful()) {
                            ResponseBody responseBody = response.body();
                            if (responseBody != null) {
                                try {
                                    String logsJson = responseBody.string();
                                    GetLogsModel getPendingLogsModel = new Gson().fromJson(logsJson, GetLogsModel.class);
                                    int statusCode = getPendingLogsModel.getStatusCode();
                                    String message = getPendingLogsModel.getMessage();
                                    //boolean status = getPendingLogsModel.isStatus();

                                    if (getPendingLogsModel.getData().isEmpty()) {
                                        // emptyView.setVisibility(View.VISIBLE);
                                        //recycleview.setVisibility(View.GONE);
                                    } else {
                                        for (AttendanceData attendanceData : getPendingLogsModel.getData()) {
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
//                                        recycleview.setAdapter(event_adpter);

                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                // customAdapter = new Certifyadpter(getActivity(), getModel(recordDates,false));
                                //RecyclerView recyclerView = findViewById(R.id.your_recycler_view_id); // Replace with your RecyclerView ID

                                Certifyadpter customAdapter = new Certifyadpter(getContext(), getModel(recordDates, false)); // replace yourDataList with your actual data
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

}