package com.example.eld.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.eld.Eventmodel;
import com.example.eld.R;
import com.example.eld.network.dto.attendance.AddAttendanceRecordRequestModel;
import com.example.eld.network.dto.login.request.UpdateCoDriverModel;
import com.example.eld.network.dto.login.request.UpdateShippingAddressModel;
import com.example.eld.network.dto.login.request.UpdateTripNoModel;
import com.example.eld.utils.Breakhelper;
import com.example.eld.utils.DriveHelper;
import com.example.eld.utils.PersonalHelper;
import com.example.eld.utils.Shifthelper;
import com.example.eld.utils.WeekHelper;
import com.example.eld.utils.Yardmoveshelper;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import kotlin.jvm.internal.Intrinsics;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressWarnings("deprecation")
public class DashboardFragment extends BaseFragment {

    private static final int DRIVE_THRESHOLD_SECONDS = 28800;
    private static final int VIBRATION_DURATION = 8000;
    private MediaPlayer mediaPlayer;

    private CountDownTimer countDownTimer;
    private Handler handler;
    private Runnable runnable;

    ImageView editdriver, trip, ship;
    Dialog confirmation_logout;
    TextView codriver, tripno, shipno;

    String drivername, tripnoo, shiping;
    ProgressBar driveprogress, shiftprogress, breakprogress, weekprogress;

    LottieAnimationView driveblionk;
    public DriveHelper driveHelper;
    //public Heplper heplper;
    public Breakhelper breakhelper;
    public Shifthelper shifthelper;
    public WeekHelper weekHelper;
    public Yardmoveshelper yardmoveshelper;
    public PersonalHelper personalHelper;

    private final Timer timedrive = new Timer();
    private final Timer sleeptimer = new Timer();
    private final Timer breaktimer = new Timer();
    private final Timer shifttimer = new Timer();
    private final Timer ytimer = new Timer();
    private final Timer ptimer = new Timer();

    long drivesec;
    long shiftsec;
    long breaksec;
    long weeksec;
    long yarmovsec;
    long psec;

    Vibrator vibrator;
    int secccc = 0;

    LineChart lineChart;
    LineDataSet lineDataSet = new LineDataSet(null, null);
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    LineData lineData;
    private Object HashMap;
    List<Entry> lineEntries;
    private Handler handler = new Handler();
    private Runnable runnable;
    MediaPlayer mp;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    Eventmodel eventmodel;
    TextView driveTimeTV, shifttime, breaktime, weektime;

    @NotNull
    public final DriveHelper getDriveHelper() {
        DriveHelper driveHelper = this.driveHelper;
        if (driveHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("driveHelper");
        }
        return driveHelper;
    }

    /*@NotNull
    public final Heplper getHeplper() {
        Heplper helperr = this.heplper;
        if (helperr == null) {
            Intrinsics.throwUninitializedPropertyAccessException("heplper");
        }
        return helperr;
    }*/

    @NotNull
    public final Breakhelper getBreakhelper() {
        Breakhelper breakhelper = this.breakhelper;
        if (breakhelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("breakhelper");
        }
        return breakhelper;
    }

    @NotNull
    public final Shifthelper getShifthelper() {
        Shifthelper shifthelper = this.shifthelper;
        if (shifthelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("shifthelper");
        }
        return shifthelper;
    }

    @NotNull
    public final Yardmoveshelper getyardhelper() {
        Yardmoveshelper yardmoveshelper = this.yardmoveshelper;
        if (yardmoveshelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("yardmoveshelper");
        }
        return yardmoveshelper;
    }

    @NotNull
    public final PersonalHelper getPersonalHelper() {
        PersonalHelper personalHelper = this.personalHelper;
        if (personalHelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("personalHelper");
        }
        return personalHelper;
    }

    private WeekHelper timer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_deshboard_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViews(view);
        setupListeners();
        initializeFirebase();
        initializeMediaResources();
        setupDialogs();
        setupTimers();
        setupProgressBars();
        setupLineChart();
        updateTextViews();
        observeFirebaseData();
    }

    private void initializeViews(View view) {
        lineEntries = new ArrayList<>();

        editdriver = view.findViewById(R.id.editcodriver);
        trip = view.findViewById(R.id.edittrip);
        ship = view.findViewById(R.id.editship);
        codriver = view.findViewById(R.id.cofrivername);
        tripno = view.findViewById(R.id.tripno);
        shipno = view.findViewById(R.id.shipno);
        driveTimeTV = view.findViewById(R.id.driveTimeTV);
        shifttime = view.findViewById(R.id.shifttime);
        breaktime = view.findViewById(R.id.breaktime);
        weektime = view.findViewById(R.id.weektime);
        driveprogress = view.findViewById(R.id.driveprogress);
        shiftprogress = view.findViewById(R.id.shiftprogress);
        breakprogress = view.findViewById(R.id.breakprogress);
        weekprogress = view.findViewById(R.id.weekprogress);
        driveblionk = view.findViewById(R.id.driveblionk);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        lineChart = view.findViewById(R.id.lineChart);
        mp = MediaPlayer.create(getContext(), R.raw.violationsound);
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        codriver.setText(helperClass.getCO_DRIVER());
        shipno.setText(helperClass.getSHIPPING_ADDRESS());
        tripno.setText(helperClass.getTRIP_NO());
        confirmation_logout = new Dialog(getContext());
    }

    private void setupListeners() {
        editdriver.setOnClickListener(v -> showEditDialog("Co driver", codriver, this::callUpdateCoDriverAPI));
        trip.setOnClickListener(v -> showEditDialog("Trip number", tripno, this::callUpdateTripNoAPI));
        ship.setOnClickListener(v -> showEditDialog("Shipping address", shipno, this::callUpdateShippingAddressAPI));
    }

    private void showEditDialog(String title, TextView textView, Consumer<String> apiCall) {
        confirmation_logout.setContentView(R.layout.edit_dialog);
        confirmation_logout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        CardView updateBtn = confirmation_logout.findViewById(R.id.update);
        CardView cancel = confirmation_logout.findViewById(R.id.cancel);
        TextView text = confirmation_logout.findViewById(R.id.dialog_title);
        EditText edittextedit = confirmation_logout.findViewById(R.id.edittextedit);
        text.setText(title);

        updateBtn.setOnClickListener(v1 -> {
            String value = edittextedit.getText().toString();
            if (TextUtils.isEmpty(value)) {
                Toast.makeText(getActivity(), "Enter something", Toast.LENGTH_SHORT).show();
            } else {
                textView.setText(value);
                confirmation_logout.dismiss();
                apiCall.accept(value);
            }
        });

        cancel.setOnClickListener(v12 -> confirmation_logout.dismiss());
        confirmation_logout.setCancelable(false);
        confirmation_logout.show();
    }

    private void initializeFirebase() {
        driveHelper = new DriveHelper(getContext());
        weekHelper = new WeekHelper(getContext());
        breakhelper = new Breakhelper(getContext());
        shifthelper = new Shifthelper(getContext());
        yardmoveshelper = new Yardmoveshelper(getContext());
        personalHelper = new PersonalHelper(getContext());
    }


    private void showchart(List<Entry> lineEntries) {
        lineDataSet.setValues(lineEntries);
        iLineDataSets.clear();
        iLineDataSets.add(lineDataSet);
        lineData = new LineData(iLineDataSets);
        lineChart.clear();
        lineChart.setData(lineData);
        lineChart.invalidate();
        Log.d("TAG", " =========Desh=========> " + lineEntries.size());
        handler.post(runnable);
    }


    private final void stopdriveTimer() {
        driveHelper.setdrivetimerCounting(false);
    }

    private final void startdriveTimer() {
        driveHelper.setdrivetimerCounting(true);
    }


    private final void stopsleep() {
     //   heplper.setTimerCountinge(false);
    }

    private final void startsleep() {
     //   heplper.setTimerCountinge(true);
    }


    private final void stopbreak() {
        breakhelper.setbreaktimerCounting(false);
    }

    private final void startbreak() {
        breakhelper.setbreaktimerCounting(true);
    }

    private final void stopshift() {
        shifthelper.setshifttimerCounting(false);
    }

    private final void startshift() {
        shifthelper.setshifttimerCounting(true);
    }

    private final void stopy() {
        yardmoveshelper.ysetTimerCountinge(false);
    }

    private final void starty() {
        yardmoveshelper.ysetTimerCountinge(true);
    }

    private final void stopp() {
        personalHelper.psetTimerCountinge(false);
    }

    private final void startp() {
        personalHelper.psetTimerCountinge(true);
    }

/*
    private final Date restarttime() {
        long diff = heplper.startTimee().getTime() - heplper.stopTimee().getTime();

        return new Date(System.currentTimeMillis() + diff);
    }
*/

    private final String timeFromLong(long ms) {
        long seconds = (ms / 1000) % 60;
        long minutes = (ms / (1000 * 60) % 60);
        long hours = (ms / (1000 * 60 * 60) % 24);
        weeksec = ms;
        return makeTimesleep(hours, minutes, seconds);
    }

    private final String makeTimesleep(long hours, long minutes, long seconds) {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

/*
    private final class TimeTask extends TimerTask {
        public void run() {
            if (heplper.timerCountinge()) {
                long datt = (new Date()).getTime();
                long time = datt - heplper.startTimee().getTime();
                timeFromLong(time);

            }
        }

        public TimeTask() {
        }
    }
*/

    private final Date calcRestartTime() {
        long diff = driveHelper.startdriveTime().getTime() - driveHelper.stopdriveTime().getTime();
        return new Date(System.currentTimeMillis() + diff);
    }

    private final String timeStringFromLong(long ms) {
        long seconds = (ms / 1000) % 60;
        long minutes = (ms / (1000 * 60) % 60);
        long hours = (ms / (1000 * 60 * 60) % 24);

        drivesec = ms;
        return makeTimeString(hours, minutes, seconds);
    }

    private final String makeTimeString(long hours, long minutes, long seconds) {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private final class TimeTas extends TimerTask {
        public void run() {
            if (driveHelper.drivetimerCounting()) {
                long datt = (new Date()).getTime();
                if (driveHelper.startdriveTime() != null) {

                    long time = datt - driveHelper.startdriveTime().getTime();
                    timeStringFromLong(time);

                }
            }
        }

        public TimeTas() {
        }
    }

    private final Date offrestarttime() {
        long diff = breakhelper.startbreakTime().getTime() - breakhelper.stopbreakTime().getTime();
        return new Date(System.currentTimeMillis() + diff);
    }

    private final String offtimeFromLong(long ms) {
        long seconds = (ms / 1000) % 60;
        long minutes = (ms / (1000 * 60) % 60);
        long hours = (ms / (1000 * 60 * 60) % 24);

        breaksec = ms;
        return makeTimeoff(hours, minutes, seconds);
    }

    private final String makeTimeoff(long hours, long minutes, long seconds) {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private final class TimeBreak extends TimerTask {
        public void run() {
            if (breakhelper.breaktimerCounting()) {
                long datt = (new Date()).getTime();
                if (breakhelper.startbreakTime() != null) {

                    long time = datt - breakhelper.startbreakTime().getTime();
                    offtimeFromLong(time);
                }
            }
        }

        public TimeBreak() {
        }
    }

    private final Date onrestarttime() {
        long diff = shifthelper.shiftstartTime().getTime() - shifthelper.shiftstopTime().getTime();
        return new Date(System.currentTimeMillis() + diff);
    }

    private final String ontimeFromLong(long ms) {
        long seconds = (ms / 1000) % 60;
        long minutes = (ms / (1000 * 60) % 60);
        long hours = (ms / (1000 * 60 * 60) % 24);

        shiftsec = ms;
        return makeTimeon(hours, minutes, seconds);
    }

    private final String makeTimeon(long hours, long minutes, long seconds) {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private final class TimeTaskshift extends TimerTask {
        public void run() {
            if (shifthelper.shifttimerCounting()) {
                long datt = (new Date()).getTime();
                if (shifthelper.shiftstartTime() != null) {

                    long time = datt - shifthelper.shiftstartTime().getTime();
                    ontimeFromLong(time);
                }
            }
        }

        public TimeTaskshift() {
        }
    }

    private final Date yrestarttime() {
        long diff = yardmoveshelper.ystartTimee().getTime() - yardmoveshelper.ystopTimee().getTime();
        return new Date(System.currentTimeMillis() + diff);
    }

    private final String ytimeFromLong(long ms) {
        long seconds = (ms / 1000) % 60;
        long minutes = (ms / (1000 * 60) % 60);
        long hours = (ms / (1000 * 60 * 60) % 24);
        yarmovsec = ms;
        return makeTimey(hours, minutes, seconds);
    }

    private final String makeTimey(long hours, long minutes, long seconds) {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private final class TimeTasky extends TimerTask {
        public void run() {
            if (yardmoveshelper.ytimerCountinge()) {
                long datt = (new Date()).getTime();
                long time = datt - yardmoveshelper.ystartTimee().getTime();
                ytimeFromLong(time);

            }
        }

        public TimeTasky() {
        }
    }

    private final Date prestarttime() {
        long diff = personalHelper.pstartTimee().getTime() - personalHelper.pstopTime().getTime();
        return new Date(System.currentTimeMillis() + diff);
    }

    private final String ptimeFromLong(long ms) {
        long seconds = (ms / 1000) % 60;
        long minutes = (ms / (1000 * 60) % 60);
        long hours = (ms / (1000 * 60 * 60) % 24);
        psec = ms;
        return makeTimey(hours, minutes, seconds);
    }

    private final String makeTimep(long hours, long minutes, long seconds) {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private final class TimeTaskp extends TimerTask {
        public void run() {
            if (personalHelper.ptimerCountinge()) {
                long datt = (new Date()).getTime();
                long time = datt - personalHelper.pstartTimee().getTime();
                ptimeFromLong(time);

            }
        }

        public TimeTaskp() {

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (breaksec == 0) {
            breakprogress.setProgress(10);
        }
        if (shiftsec == 0) {
            shiftprogress.setProgress(10);
        }
        if (weeksec == 0) {
            weekprogress.setProgress(10);
        }
        if (drivesec == 0) {
            driveprogress.setProgress(10);
        }

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @SuppressLint("NewApi")
            @Override
            public void run() {
                //driveprogress
                long driveprog = drivesec / 1000;
                int peogressss = (int) driveprog;
                int nextprogress = peogressss - 39600;
                int nepro = nextprogress - 39600;

                if (nextprogress > 39600) {
                    driveprogress.setProgress(nepro);

                } else {
                    driveprogress.setProgress(peogressss);

                }
                //weekprogress
                long weekse = weeksec / 1000;
                int weekproggg = (int) weekse;
                int nextprogres = weekproggg - 252000;
                int neprooo = nextprogres - 252000;
                if (nextprogres > 252000) {
                    weekprogress.setProgress(neprooo);


                } else {
                    weekprogress.setProgress(weekproggg);

                }
                //breakprogress
                long breakprogs = breaksec / 1000;
                int breakpro = (int) breakprogs;
                int nextprogre = breakpro - 28800;
                int nepr = nextprogre - 28800;
                if (nextprogre > 28800) {
                    breakprogress.setProgress(nepr);

                } else {
                    breakprogress.setProgress(breakpro);

                }
                //shiftprogress
                long shiftseccc = shiftsec / 1000;
                int shiftprogs = (int) shiftseccc;
                int nextprogr = shiftprogs - 50400;
                int nepre = nextprogr - 50400;

                if (shiftprogs <= 50400) {
                    shiftprogress.setProgress(shiftprogs);

                } else {
                    shiftprogress.setProgress(nepre);

                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    private void callUpdateCoDriverAPI(String coDriver, String id) {
        UpdateCoDriverModel requestBody = new UpdateCoDriverModel(id,coDriver);
        if (helperClass.getNetworkInfo()) {
            Call<ResponseBody> updateCoDrivercall = apiService.updateCoDriver(requestBody);
            updateCoDrivercall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        hideLoader();
                        if (response.isSuccessful()) {
                            ResponseBody responseBody = response.body();
                            if (responseBody != null) {
                                try {
                                    String profileJson = responseBody.string();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
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
                    Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            showNoInternetConnectionError();
        }
    }
    private void callUpdateTripNoAPI(String tripNo, String id) {
        UpdateTripNoModel requestBody = new UpdateTripNoModel(id,tripNo);
        if (helperClass.getNetworkInfo()) {
            Call<ResponseBody> updateTripNoCall = apiService.updateTripNo(requestBody);
            updateTripNoCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        hideLoader();
                        if (response.isSuccessful()) {
                            ResponseBody responseBody = response.body();
                            if (responseBody != null) {
                                try {
                                    String profileJson = responseBody.string();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
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
                    Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            showNoInternetConnectionError();
        }
    }
    private void callUpdateShippingAddressAPI(String shippingAddress, String id) {
        UpdateShippingAddressModel requestBody = new UpdateShippingAddressModel(id,shippingAddress);
        if (helperClass.getNetworkInfo()) {
            Call<ResponseBody> updateShippingAddressCall = apiService.updateShippingAddress(requestBody);
            updateShippingAddressCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        hideLoader();
                        if (response.isSuccessful()) {
                            ResponseBody responseBody = response.body();
                            if (responseBody != null) {
                                try {
                                    String profileJson = responseBody.string();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
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
                    Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            showNoInternetConnectionError();
        }
    }


    public void changeDriveandBreakUI(boolean drive, boolean Bbreak) {
        int driveSeconds = (int) (drivesec / 1000);
        int breakSeconds = (int) (breaksec / 1000);

        updateTimeTextView(driveSeconds, drive, driveTimeTV, driveblionk);
        this.timedrive.scheduleAtFixedRate(new TimeTas(), 0L, 500L);

        if (drive) {
            handleDrive(driveSeconds);
        } else {
            handleDriveWithoutStartStop(driveSeconds);
        }

        handleBreak(Bbreak, breakSeconds);
    }

    private void updateTimeTextView(int seconds, boolean isDrive, TextView textView, View visibilityView) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;

        String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);

        if (isDrive && seconds > 28800) {
            visibilityView.setVisibility(View.VISIBLE);
        } else {
            visibilityView.setVisibility(View.GONE);
        }

        textView.setText(time);
    }

    private void handleDrive(int driveSeconds) {
        if (driveSeconds > DRIVE_THRESHOLD_SECONDS) {
            startMediaAndVibration();
        }

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @SuppressLint("NewApi")
            @Override
            public void run() {
                updateTimeTextView(driveSeconds, true, driveTimeTV, driveblionk);
                handler.postDelayed(this, 1000);
            }
        });
    }

    private void handleDriveWithoutStartStop(int driveSeconds) {
        updateTimeTextView(driveSeconds, false, driveTimeTV, driveblionk);
    }

    private void handleBreak(boolean isBreak, int breakSeconds) {
        if (isBreak) {
            startbreak();
        } else {
            stopbreak();

            if (breakhelper.startbreakTime() != null && breakhelper.stopbreakTime() != null) {
                long time = (new Date()).getTime() - this.offrestarttime().getTime();
                offtimeFromLong(time);
            }
        }

        this.breaktimer.scheduleAtFixedRate(new TimeBreak(), 0L, 500L);

        if (!isBreak) {
            updateTimeTextView(breakSeconds, false, breaktime, null);
        } else {
            final Handler handler = new Handler();
            handler.post(new Runnable() {
                @SuppressLint("NewApi")
                @Override
                public void run() {
                    updateTimeTextView(breakSeconds, true, breaktime, null);
                    handler.postDelayed(this, 1000);
                }
            });
        }
    }

    private void startMediaAndVibration() {
        mp.start();
        vibrator.vibrate(VIBRATION_DURATION);
    }

    private void stopMedia() {
        mp.stop();
    }

    public void changeShift(boolean shift) {
        Log.i("TAG", "==========S=========" + shifthelper.shifttimerCounting());

        if (shift) {
            startShift();
        } else {
            stopShiftAndHandleTime();
        }

        this.shifttimer.scheduleAtFixedRate(new TimeTaskshift(), 0L, 500L);

        if (!shift) {
            updateShiftTimeTextView(shiftsec, shifttime, null);
        } else {
            final Handler handler = new Handler();
            handler.post(new Runnable() {
                @SuppressLint("NewApi")
                @Override
                public void run() {
                    updateShiftTimeTextView(shiftsec, shifttime, handler);
                }
            });
        }
    }

    private void startShift() {
        startshift();
    }

    private void stopShiftAndHandleTime() {
        stopshift();
        if (shifthelper.shiftstartTime() != null && shifthelper.shiftstopTime() != null) {
            long time = (new Date()).getTime() - onrestarttime().getTime();
            ontimeFromLong(time);
        }
    }

    private void updateShiftTimeTextView(long seconds, TextView textView, Handler handler) {
        int progressSeconds = (int) (seconds / 1000);

        int hours = progressSeconds / 3600;
        int minutes = (progressSeconds % 3600) / 60;
        int secs = progressSeconds % 60;

        String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);

        textView.setText(time);

        if (handler != null) {
            handler.postDelayed(() -> updateShiftTimeTextView(seconds, textView, handler), 1000);
        }
    }
    private void initializeMediaResources() {
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.example_sound);
        // You can set up additional configurations for the mediaPlayer if needed
        mediaPlayer.setOnCompletionListener(mp -> releaseMediaPlayer()); // Release resources after playback completion
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    To create and set up dialogs in Android, you can define a method like `setupDialogs()` to initialize and configure your dialogs. Below is an example using the `AlertDialog` class:

            ```java
    private AlertDialog confirmationDialog;

    private void setupDialogs() {
        setupConfirmationDialog();
        // Add more dialogs if needed
    }

    private void setupConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to proceed?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle positive button click
                // For example, perform some action or dismiss the dialog
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle negative button click
                // For example, dismiss the dialog
                dialog.dismiss();
            }
        });

        // Create the AlertDialog
        confirmationDialog = builder.create();
    }

    // Show the confirmation dialog when needed
    private void showConfirmationDialog() {
        if (confirmationDialog != null && !confirmationDialog.isShowing()) {
            confirmationDialog.show();
        }
    }



    private void setupTimers() {
        setupCountDownTimer();
        setupHandler();
        // Add more timers if needed
    }

    private void setupCountDownTimer() {
        countDownTimer = new CountDownTimer(30000, 1000) { // 30 seconds with 1-second intervals
            @Override
            public void onTick(long millisUntilFinished) {
                // Handle each tick (e.g., update UI)
                long secondsRemaining = millisUntilFinished / 1000;
                // Update UI or perform actions based on remaining time
            }

            @Override
            public void onFinish() {
                // Handle timer completion (e.g., perform some action)
            }
        };
    }

    private void startCountDownTimer() {
        countDownTimer.start();
    }

    private void cancelCountDownTimer() {
        countDownTimer.cancel();
    }

    private void setupHandler() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                // Handle timer tick (e.g., update UI)
                handler.postDelayed(this, 1000); // Repeat every 1 second
            }
        };
    }

    private void startHandlerTimer() {
        handler.post(runnable);
    }

    private void stopHandlerTimer() {
        handler.removeCallbacks(runnable);
    }

}