package com.example.eld.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eld.EventLogsAdapter;
import com.example.eld.Eventmodel;
import com.example.eld.R;
import com.example.eld.activity.BaseActivity;
import com.example.eld.activity.DashboardScreen;
import com.example.eld.network.dto.login.response.GetAttendanceResponseModel;
import com.example.eld.utils.CommonUtils;
import com.example.eld.utils.TimestampConverter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LogsFragment extends Fragment {

    CardView filterrr, privios, next;
    String formattedDatee;
    Dialog dialog;
    TextView showdate, emptyView;
    String querydate;
    RecyclerView recycleview;
    ArrayList<Eventmodel> list;
    EventLogsAdapter event_adpter;
    FirebaseFirestore firebaseFirestore;
    Eventmodel eventmodel;
    List<Entry> lineEntries;
    private Handler handler = new Handler();
    private Runnable runnable;
    Query query;
    ListenerRegistration graphlistenerRegistration;
    ListenerRegistration loglistenerRegistration;
    LineChart lineChart;
    LineDataSet lineDataSet = new LineDataSet(null, null);
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    LineData lineData;
    Timestamp stimestamp, ltimestamp;
    private BaseActivity baseActivity;
    private String formattedDate1;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        baseActivity = ((DashboardScreen) context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logs_fragment, container, false);
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //
        lineEntries = new ArrayList<>();

        filterrr = view.findViewById(R.id.filterrr);
        privios = view.findViewById(R.id.privios);
        next = view.findViewById(R.id.next);
        emptyView = view.findViewById(R.id.emptyView);
        showdate = view.findViewById(R.id.showdate);
        recycleview = view.findViewById(R.id.recycleview);
        lineChart = view.findViewById(R.id.lineCharttt);
        firebaseFirestore = FirebaseFirestore.getInstance();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy");
        SimpleDateFormat senddf = new SimpleDateFormat("yyyy-MM-dd");
        String senddate = senddf.format(c.getTime());
        formattedDatee = df.format(c.getTime());
        callGetAttendanceRecord(CommonUtils.getCurrentDate(CommonUtils.dateFormat), CommonUtils.getCurrentDate(CommonUtils.dateFormat));
        showdate.setText(formattedDatee);
        Calendar t = Calendar.getInstance();
        t.add(Calendar.DATE, -7);
        String old = df.format(t.getTime());
        list = new ArrayList<>();


        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setDrawValues(true);
        lineDataSet.setColor(Color.GREEN);
        lineDataSet.setCircleRadius(2);
        lineDataSet.setCircleHoleRadius(1);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setValueTextSize(12);
        lineDataSet.setValueTextColor(Color.GREEN);
        lineDataSet.setMode(LineDataSet.Mode.STEPPED);


        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.setTouchEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.setDoubleTapToZoomEnabled(true);


        // Setup X Axis
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.TOP);
        lineChart.getXAxis().setGranularityEnabled(true);
        lineChart.getXAxis().setGranularity(1.0f); // Set the granularity to 60 minutes
        lineChart.getXAxis().setXOffset(0.5f);
        lineChart.getXAxis().setLabelCount(25);
        lineChart.getXAxis().setAxisMinimum(0);
        lineChart.getXAxis().setAxisMaximum(24);


        // Setup Y Axis
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMinimum(0);
        yAxis.setAxisMaximum(5);
        yAxis.setGranularity(1f);

        ArrayList<String> yAxisLabel = new ArrayList<>();
        yAxisLabel.add("");
        yAxisLabel.add("OND");
        yAxisLabel.add("D");
        yAxisLabel.add("SB");
        yAxisLabel.add("OFFD");
        yAxisLabel.add("");

//        lineChart.getAxisLeft().setCenterAxisLabels(true);
        lineChart.getAxisLeft().setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                if (value == -1 || value >= yAxisLabel.size()) return "";
                return yAxisLabel.get((int) value);
            }
        });
        lineChart.getAxisRight().setEnabled(false);
        lineChart.invalidate();

//        event_adpter = new Event_adpter(getContext(), list);
//        recycleview.setAdapter(event_adpter);
        recycleview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//        CollectionReference graph = firebaseFirestore.collection(Helperclass.getid(getActivity()));
        //CollectionReference graph = firebaseFirestore.collection("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOm51bGwsImF1ZCI6bnVsbCwiaWF0IjoxNjc5NTczMDcwLCJuYmYiOjE2Nzk1NzMwODAsImV4cCI6MTY3OTY1OTQ3MCwiZGF0YSI6eyJpZCI6IjYiLCJkcml2ZXJfaWQiOiJ0ZXN0MDEifX0.86RCwx4C1FRfsrbS7kF42MGlD3S0uWmObHrt_DDtSZQ");
// Use the UTC time to query the Firestore database
//        query = graph.whereGreaterThanOrEqualTo("time", TimestampConverter.getTimestampFromDate(senddate, true)).whereLessThanOrEqualTo("time", TimestampConverter.getTimestampFromDate(senddate, false)).orderBy("time", Query.Direction.ASCENDING);


        // Define the EventListener outside of the button click
        EventListener<QuerySnapshot> graphListener = (snapshots, e) -> {
            if (e != null) {
                Log.w("TAG", "Listen failed.", e);
                return;
            }

            List<DocumentSnapshot> listt = snapshots.getDocuments();

            Eventmodel em;
            Log.d("TAG", " =========GSize=========> " + snapshots.size());
            for (QueryDocumentSnapshot doc : snapshots) {
                if (doc.exists()) {
                    lineEntries.clear();
                    for (DocumentSnapshot d : listt) {
                        em = d.toObject(Eventmodel.class);
                        // Log.d("TAG", " =========YYYYYY=========> " + em.getY());
                        float x = Float.parseFloat(TimestampConverter.convertTimestampforx(em.getX()));
                        float y = Float.parseFloat(em.getY());
                        lineEntries.add(new Entry(x, y));

                    }
                    //   Log.d("TAG", "======lineEntries======" + lineEntries.size());
                    showchart(lineEntries);
                } else {
                    Log.d("TAG", "Current data: null");

                    lineChart.clear();
                    lineChart.invalidate();
                }
            }
        };
        EventListener<QuerySnapshot> logListener = (snapshots, e) -> {
            if (e != null) {
                Log.w("TAG", "Listen failed.", e);
                return;
            }

            List<Eventmodel> eventList = new ArrayList<>();
            for (QueryDocumentSnapshot doc : snapshots) {
                Eventmodel eventmodel = doc.toObject(Eventmodel.class);
                if (eventmodel.getGraph() != null && eventmodel.getGraph().equalsIgnoreCase("logs"))
                    eventList.add(eventmodel);
            }
            list.clear();
            list.addAll(eventList);
            event_adpter.notifyDataSetChanged();
        };


// Assign the EventListener to the query and store the ListenerRegistration object
//        graphlistenerRegistration = query.addSnapshotListener(graphListener);

//        loglistenerRegistration = query.addSnapshotListener(logListener);


        // Create a handler to update the chart periodically
        /*runnable = new Runnable() {
            @Override
            public void run() {
                // Update the chart data
                Log.d("TAG", " =========lineEntries=========> " + lineEntries.size());
                String value = TimestampConverter.generateTimestamp();
                float x = Float.parseFloat(TimestampConverter.convertTimestampforx(value));
                if (lineEntries.size() > 0)
                    lineEntries.add(new Entry(x, lineEntries.get(lineEntries.size() - 1).getY()));
                handler.postDelayed(this, 60000);
                lineChart.notifyDataSetChanged();
                // Move the chart view to the right to show the latest data point


            }
        };*/

// Start the updates


        next.setOnClickListener(v -> {
            String showingdatt = showdate.getText().toString();
            if (showingdatt.equals(formattedDatee)) {
                Toast.makeText(getContext(), "Nobody predicts future", Toast.LENGTH_SHORT).show();
            } else {
                c.add(Calendar.DATE, 1);
                formattedDate1 = df.format(c.getTime());
                String senddate1 = senddf.format(c.getTime());
                showdate.setText(formattedDate1);
                lineChart.clear();
                list.clear();
                lineEntries.clear();
                event_adpter.notifyDataSetChanged();

                callGetAttendanceRecord(senddate1, senddate1);
//                    Query newquery = graph.whereGreaterThanOrEqualTo("time", TimestampConverter.getTimestampFromDate(senddate, true)).whereLessThanOrEqualTo("time", TimestampConverter.getTimestampFromDate(senddate, false)).orderBy("time", Query.Direction.ASCENDING);

//                    graphlistenerRegistration.remove();
//                    loglistenerRegistration.remove();

                // Assign the new EventListener to the new query
//                    graphlistenerRegistration = newquery.addSnapshotListener(graphListener);
//                    loglistenerRegistration = newquery.addSnapshotListener(logListener);
//
//
//                    // Update the query variable with the new query
//                    query = newquery;
            }
        });
        privios.setOnClickListener(v -> {
            String showingdatt = showdate.getText().toString();

            if (showingdatt.equals(old)) {
                Toast.makeText(getContext(), "Cannot be viewed before 8 days", Toast.LENGTH_SHORT).show();
            } else {
                c.add(Calendar.DATE, -1);
                String formattedDate2 = df.format(c.getTime());
                String senddate12 = senddf.format(c.getTime());
                showdate.setText(formattedDate2);
                lineEntries.clear();
                lineChart.clear();
                list.clear();
                event_adpter.notifyDataSetChanged();
                callGetAttendanceRecord(senddate12, senddate12);
//
//                    Query newquery = graph.whereGreaterThanOrEqualTo("time", TimestampConverter.getTimestampFromDate(senddate, true)).whereLessThanOrEqualTo("time", TimestampConverter.getTimestampFromDate(senddate, false)).orderBy("time", Query.Direction.ASCENDING);


                // Remove the previous listener
//                    graphlistenerRegistration.remove();
//                    loglistenerRegistration.remove();
                // Assign the new EventListener to the new query
//                    graphlistenerRegistration = newquery.addSnapshotListener(graphListener);
//                    loglistenerRegistration = newquery.addSnapshotListener(logListener);
//
//                    // Update the query variable with the new query
//                    query = newquery;
            }
        });

        dialog = new Dialog(getContext());

        filterrr.setOnClickListener(v -> {
            dialog.setContentView(R.layout.filter_layout);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            TextView cancel = dialog.findViewById(R.id.cannn);
            TextView apply = dialog.findViewById(R.id.apply);

            cancel.setOnClickListener(v1 -> dialog.dismiss());
            apply.setOnClickListener(v12 -> dialog.dismiss());

            dialog.setCancelable(false);
            dialog.show();
        });

    }

    private void showchart(List<Entry> lineEntries) {
        lineDataSet.setValues(lineEntries);
        iLineDataSets.clear();
        iLineDataSets.add(lineDataSet);
        lineData = new LineData(iLineDataSets);
        lineChart.clear();
        lineChart.setData(lineData);
        lineChart.invalidate();
        String showingdatt = showdate.getText().toString();
        if (showingdatt.equals(formattedDatee)) {
            // Toast.makeText(getContext(), "Graph runing", Toast.LENGTH_SHORT).show();
            handler.post(runnable);
        }
    }

    private void callGetAttendanceRecord(String startDate, String endDate) {
        if (baseActivity.helperClass.getNetworkInfo()) {
            baseActivity.showLoader();
            Call<ResponseBody> getAttendanceRecordCall = baseActivity.apiService
                    .getAttendenceRecord(String.valueOf(baseActivity.helperClass.getID()), startDate);
            getAttendanceRecordCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        baseActivity.hideLoader();
                        if (response.isSuccessful()) {
                            ResponseBody responseBody = response.body();
                            if (responseBody != null) {
                                try {
                                    String logsJson = responseBody.string();
                                    GetAttendanceResponseModel getAttendanceResponseModel = new Gson().fromJson(logsJson, GetAttendanceResponseModel.class);
                                    int statusCode = getAttendanceResponseModel.getStatusCode();
                                    String message = getAttendanceResponseModel.getMessage();
                                    boolean status = getAttendanceResponseModel.isStatus();

                                    if (getAttendanceResponseModel.getData().isEmpty()) {
                                        emptyView.setVisibility(View.VISIBLE);
                                        recycleview.setVisibility(View.GONE);
                                    } else {
                                        emptyView.setVisibility(View.GONE);
                                        recycleview.setVisibility(View.VISIBLE);

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
                                        event_adpter = new EventLogsAdapter(getContext(), getAttendanceResponseModel.getData());
                                        recycleview.setAdapter(event_adpter);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            baseActivity.onAPIErrorMessageReceived(response.errorBody().toString());
                        }
                    } catch (Exception e) {
                        baseActivity.hideLoader();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    baseActivity.hideLoader();
                    Toast.makeText(baseActivity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            baseActivity.showNoInternetConnectionError();
        }
    }


    public static String convertDateFormat(String inputDate) {
        try {
            // Decode the URL-encoded date
            String decodedDate = URLDecoder.decode(inputDate, "UTF-8");
            // Parse the decoded date using the original format
            SimpleDateFormat originalFormat = new SimpleDateFormat("MMM dd yyyy");
            Date date = originalFormat.parse(decodedDate);
            // Convert the date to the desired format
            SimpleDateFormat desiredFormat = new SimpleDateFormat("yyyy-MM-dd");
            return desiredFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Unable to convert date";
        }
    }


}