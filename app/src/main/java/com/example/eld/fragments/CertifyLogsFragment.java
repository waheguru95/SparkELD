package com.example.eld.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eld.Certifyadpter;
import com.example.eld.PendingLog;
import com.example.eld.R;
import com.example.eld.network.dto.login.request.UpdateCertifiedLogRequest;
import com.example.eld.network.dto.login.response.AttendanceData;
import com.example.eld.network.dto.login.response.GetLogsModel;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CertifyLogsFragment extends BaseFragment implements Certifyadpter.OnItemSelectedListener {
    CardView card1back, card1front, card2back, card2front;
    private ArrayList<PendingLog> modelArrayList;
    TextView card1text, card2text;
    CheckBox selectAllCheckBox;
    RecyclerView pendingRecycleview;
    RecyclerView certifiedRecycleview;
    private Certifyadpter customAdapter;
    Button certifydone;

    ArrayList<String> recordDates = new ArrayList<>();
    ArrayList<String> recordIds = new ArrayList<>();
    ArrayList<String> selectedItems = new ArrayList<>();
    boolean isAllSelected = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_certify_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        certifiedRecycleview = view.findViewById(R.id.certified_recycleview);
        pendingRecycleview = view.findViewById(R.id.pending_recycleview);
        customAdapter = new Certifyadpter(getActivity(), modelArrayList, recordIds);
        certifiedRecycleview.setAdapter(customAdapter);
        pendingRecycleview.setAdapter(customAdapter);

        card1back = view.findViewById(R.id.card1back);
        card1front = view.findViewById(R.id.card1front);
        card1text = view.findViewById(R.id.card1text);
        card2back = view.findViewById(R.id.card2back);
        card2front = view.findViewById(R.id.card2front);
        card2text = view.findViewById(R.id.card2text);

        selectAllCheckBox = view.findViewById(R.id.check_box);
        certifydone = view.findViewById(R.id.certify_done);

        callGetPendingLogs();


        // controlfunction(new Pendingcertify_fragment());

        card1front.setOnClickListener(v -> {

            callGetPendingLogs();
//                card1front.setCardBackgroundColor(Color.rgb(237,102,59));
//                card1back.setCardBackgroundColor(Color.rgb(237,102,59));
//                card1text.setTextColor(Color.rgb(255,255,255));
//                card2back.setCardBackgroundColor(Color.rgb(237,102,59));
//                card2front.setCardBackgroundColor(Color.rgb(255,255,255));
//                card2text.setTextColor(Color.rgb(0,0,0));

//                controlfunction(new Pendingcertify_fragment());

        });
        card2front.setOnClickListener(v -> {
//                card1front.setCardBackgroundColor(Color.rgb(255,255,255));
//                card1back.setCardBackgroundColor(Color.rgb(237,102,59));
//                card1text.setTextColor(Color.rgb(0,0,0));
//                card2back.setCardBackgroundColor(Color.rgb(237,102,59));
//                card2front.setCardBackgroundColor(Color.rgb(237,102,59));
//                card2text.setTextColor(Color.rgb(255,255,255));
            // controlfunction(new Certify_certification_fragment());
            callCertifiedLogs();
        });

        certifydone.setOnClickListener((View.OnClickListener) v -> {
                  Log.d("cer",""+selectedItems)  ;
         //   selectedItems = customAdapter.getSelectedItemIds();
            if (selectedItems.size() == 0) {
                Toast.makeText(getContext(), "Please Select report", Toast.LENGTH_SHORT).show();
            } else {
                // Toast.makeText(getContext(), itemselect, Toast.LENGTH_SHORT).show();
                // certifyPendingLogs(customAdapter.getSelectedItems());
                certifyPendingLogs(selectedItems);
            }
        });

        selectAllCheckBox.setOnClickListener(v -> {
            // selectedItems=customAdapter.getSelectedItems();
            customAdapter.selectAllItems(isAllSelected);
            isAllSelected = !isAllSelected;
        });
    }

    private void callCertifiedLogs() {
        if (helperClass.getNetworkInfo()) {
            showLoader();
            Call<ResponseBody> getCertifiedLogs = apiService
                    .getCertifiedLog("" + helperClass.getID());
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
                                Certifyadpter customAdapter = new Certifyadpter(getContext(), getModel(recordDates, false), recordIds); // replace yourDataList with your actual data
                                certifiedRecycleview.setLayoutManager(new LinearLayoutManager(getContext()));
                                certifiedRecycleview.setVisibility(View.VISIBLE);
                                pendingRecycleview.setVisibility(View.GONE);
                                certifiedRecycleview.setAdapter(customAdapter);
                                     customAdapter.setOnItemSelectedListener(CertifyLogsFragment.this);
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
                                            recordIds.add("" + attendanceData.getId());
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

                                Certifyadpter customAdapter = new Certifyadpter(getContext(), getModel(recordDates, false), recordIds); // replace yourDataList with your actual data
                                pendingRecycleview.setLayoutManager(new LinearLayoutManager(getContext()));
                                pendingRecycleview.setVisibility(View.VISIBLE);
                                certifiedRecycleview.setVisibility(View.GONE);
                                pendingRecycleview.setAdapter(customAdapter);
                                  customAdapter.setOnItemSelectedListener(CertifyLogsFragment.this); 
                                customAdapter.notifyDataSetChanged();
                                //selectedItems= customAdapter.getSelectedItems();

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

    private void certifyPendingLogs(List<String> ids) {
        if (helperClass.getNetworkInfo()) {
            showLoader();
            UpdateCertifiedLogRequest requestBody = new UpdateCertifiedLogRequest(ids);
            Call<ResponseBody> updateCertifiedLog = apiService.updateCertifiedLog(requestBody);

            updateCertifiedLog.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        hideLoader();
                        if (response.isSuccessful()) {
                            ResponseBody responseBody = response.body();
                            if (responseBody != null) {
                                Log.d("certify response",""+responseBody)    ;
                                       //    String logsJson = responseBody.string();
                                         //  Log.d("logsJSon",logsJson);
                                // customAdapter = new Certifyadpter(getActivity(), getModel(recordDates,false));
                                //RecyclerView recyclerView = findViewById(R.id.your_recycler_view_id); // Replace with your RecyclerView ID

                               // Certifyadpter customAdapter = new Certifyadpter(getContext(), getModel(recordDates, false), recordIds); // replace yourDataList with your actual data
                                //certifiedRecycleview.setVisibility(View.VISIBLE);
                                //certifiedRecycleview.setAdapter(customAdapter);
                                //customAdapter.notifyDataSetChanged();
                                //selectedItems= customAdapter.getSelectedItems();

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

    @Override
    public void onItemSelected(int position, boolean isSelected) {
        if (isSelected) {
            selectedItems.add(recordIds.get(position));

        } else {
            selectedItems.remove(recordIds.remove(position));
        }
        Log.d("selectedItems",""+selectedItems)  ;
    }
}