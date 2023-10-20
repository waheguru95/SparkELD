package com.example.eld.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
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


    public void changeDriveandBreakUI(boolean drive, boolean Bbreak) {
        // Change the UI as needed
        Log.i("TAG", "==========SHIVANI=========" + driveHelper.drivetimerCounting());

        if (drive) {
            long driveprog = drivesec / 1000;
            int peogressss = (int) driveprog;
            if (peogressss > 28800) {
                final Handler handlerr = new Handler();
                handlerr.post(new Runnable() {
                    @SuppressLint("NewApi")
                    @Override
                    public void run() {
                        mp.start();
                        handlerr.postDelayed(this, 1000);
                    }
                });
                final Handler handlery = new Handler();
                handlery.post(new Runnable() {
                    @SuppressLint("NewApi")
                    @Override
                    public void run() {
                        secccc++;
                        if (secccc == 9) {
                            mp.stop();
                        }
                        handlery.postDelayed(this, 1000);
                    }
                });
                vibrator.vibrate(8000);

            }


            final Handler handl = new Handler();
            handl.post(new Runnable() {
                @SuppressLint("NewApi")
                @Override
                public void run() {

                    long driveprog = drivesec / 1000;
                    int peogressss = (int) driveprog;


                    int hours = peogressss / 3600;
                    int minutes = (peogressss % 3600) / 60;
                    int secs = peogressss % 60;

                    String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);

                    if (peogressss > 28800) {
                        driveblionk.setVisibility(View.VISIBLE);
                    } else {
                        driveblionk.setVisibility(View.GONE);
                    }


                    driveTimeTV.setText(time);
                    handl.postDelayed(this, 1000);
                }
            });
        } else {
            long driveprog = drivesec / 1000;
            int peogressss = (int) driveprog;

            int hours = peogressss / 3600;
            int minutes = (peogressss % 3600) / 60;
            int secs = peogressss % 60;
            String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
            driveTimeTV.setText(time);
        }
        this.timedrive.scheduleAtFixedRate((TimerTask) (new TimeTas()), 0L, 500L);
        if (drive) {
            long driveprog = drivesec / 1000;
            int peogressss = (int) driveprog;
            if (peogressss > 28800) {
                final Handler handlerr = new Handler();
                handlerr.post(new Runnable() {
                    @SuppressLint("NewApi")
                    @Override
                    public void run() {
                        mp.start();
                        handlerr.postDelayed(this, 1000);
                    }
                });
                final Handler handlery = new Handler();
                handlery.post(new Runnable() {
                    @SuppressLint("NewApi")
                    @Override
                    public void run() {
                        secccc++;
                        if (secccc == 9) {
                            mp.stop();
                        }
                        handlery.postDelayed(this, 1000);
                    }
                });
                vibrator.vibrate(8000);

            }


            final Handler handl = new Handler();
            handl.post(new Runnable() {
                @SuppressLint("NewApi")
                @Override
                public void run() {

                    long driveprog = drivesec / 1000;
                    int peogressss = (int) driveprog;


                    int hours = peogressss / 3600;
                    int minutes = (peogressss % 3600) / 60;
                    int secs = peogressss % 60;

                    String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);

                    if (peogressss > 28800) {
                        driveblionk.setVisibility(View.VISIBLE);
                    } else {
                        driveblionk.setVisibility(View.GONE);
                    }


                    driveTimeTV.setText(time);
                    handl.postDelayed(this, 1000);
                }
            });
        } else {
            long driveprog = drivesec / 1000;
            int peogressss = (int) driveprog;

            int hours = peogressss / 3600;
            int minutes = (peogressss % 3600) / 60;
            int secs = peogressss % 60;
            String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
            driveTimeTV.setText(time);
        }
        if (Bbreak) {
            startbreak();

        } else {

            stopbreak();
            if (breakhelper.startbreakTime() != null && breakhelper.stopbreakTime() != null) {
                long time = (new Date()).getTime() - this.offrestarttime().getTime();
                offtimeFromLong(time);

            }
        }
        this.breaktimer.scheduleAtFixedRate((TimerTask) (new TimeBreak()), 0L, 500L);
        if (!Bbreak) {
            long driveprog = breaksec / 1000;
            int peogressss = (int) driveprog;

            int hours = peogressss / 3600;
            int minutes = (peogressss % 3600) / 60;
            int secs = peogressss % 60;
            String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours,

                    minutes, secs);
            breaktime.setText(time);
        } else {
            final Handler handl = new Handler();
            handl.post(new Runnable() {
                @SuppressLint("NewApi")
                @Override
                public void run() {

                    long driveprog = breaksec / 1000;
                    int peogressss = (int) driveprog;


                    int hours = peogressss / 3600;
                    int minutes = (peogressss % 3600) / 60;
                    int secs = peogressss % 60;

                    String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);

                    breaktime.setText(time);

                    handl.postDelayed(this, 1000);
                }
            });
        }

    }

    public void changeShift(boolean shift) {
        Toast.makeText(getActivity(), "Call fragment now", Toast.LENGTH_SHORT).show();
        Log.i("TAG", "==========S=========" + shifthelper.shifttimerCounting());


        if (shift) {
            startshift();

        } else {

            stopshift();
            if (shifthelper.shiftstartTime() != null && shifthelper.shiftstopTime() != null) {
                long time = (new Date()).getTime() - this.onrestarttime().getTime();
                ontimeFromLong(time);

            }
        }
        this.shifttimer.scheduleAtFixedRate((TimerTask) (new TimeTaskshift()), 0L, 500L);
        if (!shift) {
            long driveprog = shiftsec / 1000;
            int peogressss = (int) driveprog;

            int hours = peogressss / 3600;
            int minutes = (peogressss % 3600) / 60;
            int secs = peogressss % 60;
            String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
            shifttime.setText(time);
        } else {
            final Handler handl = new Handler();
            handl.post(new Runnable() {
                @SuppressLint("NewApi")
                @Override
                public void run() {

                    long driveprog = shiftsec / 1000;
                    int peogressss = (int) driveprog;


                    int hours = peogressss / 3600;
                    int minutes = (peogressss % 3600) / 60;
                    int secs = peogressss % 60;

                    String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);


                    shifttime.setText(time);
                    handl.postDelayed(this, 1000);
                }
            });
        }


    }

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

    @SuppressLint("MissingPermission")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


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

        driveHelper = new DriveHelper(getContext());
        weekHelper = new WeekHelper(getContext());
        //heplper = new Heplper(getContext());
        breakhelper = new Breakhelper(getContext());
        shifthelper = new Shifthelper(getContext());
        yardmoveshelper = new Yardmoveshelper(getContext());
        personalHelper = new PersonalHelper(getContext());


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        lineChart = view.findViewById(R.id.lineChart);

        mp = MediaPlayer.create(getContext(), R.raw.violationsound);

        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);


        confirmation_logout = new Dialog(getContext());
//OLD CODE
  /*      firebaseFirestore.collection(Helperclass.getAuthenticToken(getActivity())).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots){
                List<DocumentSnapshot> listt=queryDocumentSnapshots.getDocuments();
                List<Entry> lineEntries = new ArrayList<>();
                if (!queryDocumentSnapshots.isEmpty()){
                    for (DocumentSnapshot d:listt){
                        eventmodel=d.toObject(Eventmodel.class);
                        float x= Float.parseFloat(eventmodel.getX());
                        float y= Float.parseFloat(eventmodel.getY());
                        lineEntries.add(new Entry(x,y));
                    }
                    showchart(lineEntries);
                }
                else {
                    lineChart.clear();
                    lineChart.invalidate();
                }
            }
        });*/


     /*   firebaseFirestore.collection(Helperclass.getAuthenticToken(getActivity())).orderBy("time", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
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
                            // Log.d("TAG", " =========XXXXXXXXX=========> " + em.getX().toString());
                            float x = Float.parseFloat(TimestampConverter.convertTimestampforx(em.getX()));
                            float y = Float.parseFloat(em.getY());
                            lineEntries.add(new Entry(x, y));

                        }
                        //  Log.d("TAG", "======lineEntries======" + lineEntries.size());
                        showchart(lineEntries);
                    } else {
                        Log.d("TAG", "Current data: null");

                        lineChart.clear();
                        lineChart.invalidate();
                    }
                }
            }
        });*/


        editdriver.setOnClickListener(v -> {
            confirmation_logout.setContentView(R.layout.edit_dialog);
            confirmation_logout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            CardView updateBtn = confirmation_logout.findViewById(R.id.update);
            CardView cancel = confirmation_logout.findViewById(R.id.cancel);
            TextView text = confirmation_logout.findViewById(R.id.dialog_title);
            EditText edittextedit = confirmation_logout.findViewById(R.id.edittextedit);
            text.setText("Co driver");

            updateBtn.setOnClickListener(v1 -> {
                drivername = edittextedit.getText().toString();
                if (drivername.equals("")) {
                    Toast.makeText(getActivity(), "Enter something", Toast.LENGTH_SHORT).show();
                } else {
                    String tripnor = tripno.getText().toString();
                    String shipnorf = shipno.getText().toString();

                    codriver.setText(drivername);
                    confirmation_logout.dismiss();
                    callUpdateCoDriverAPI(edittextedit.getText().toString(), helperClass.getADD_ATTENDANCE_ID());
                }
            });
            cancel.setOnClickListener(v12 -> confirmation_logout.dismiss());
            confirmation_logout.setCancelable(false);
            confirmation_logout.show();
        });

        trip.setOnClickListener(v -> {
            confirmation_logout.setContentView(R.layout.edit_dialog);
            confirmation_logout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            CardView updateBtn = confirmation_logout.findViewById(R.id.update);
            CardView cancel = confirmation_logout.findViewById(R.id.cancel);
            TextView text = confirmation_logout.findViewById(R.id.dialog_title);
            EditText edittextedit = confirmation_logout.findViewById(R.id.edittextedit);
            text.setText("Trip number");

            updateBtn.setOnClickListener(v13 -> {
                tripnoo = edittextedit.getText().toString();
                if (tripnoo.equals("")) {
                    Toast.makeText(getActivity(), "Enter something", Toast.LENGTH_SHORT).show();
                } else {

                    String codrivername = codriver.getText().toString();
                    String shipnorf = shipno.getText().toString();

                    tripno.setText(tripnoo);
                    //TODO
                    callUpdateTripNoAPI(tripnoo,helperClass.getADD_ATTENDANCE_ID());
                    confirmation_logout.dismiss();

                }
            });
            cancel.setOnClickListener(v14 -> confirmation_logout.dismiss());
            confirmation_logout.setCancelable(false);
            confirmation_logout.show();
        });
        ship.setOnClickListener(v -> {
            confirmation_logout.setContentView(R.layout.edit_dialog);
            confirmation_logout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            CardView updateBtn = confirmation_logout.findViewById(R.id.update);
            CardView cancel = confirmation_logout.findViewById(R.id.cancel);
            TextView text = confirmation_logout.findViewById(R.id.dialog_title);
            EditText edittextedit = confirmation_logout.findViewById(R.id.edittextedit);
            text.setText("Shipping address");

            updateBtn.setOnClickListener(v15 -> {
                shiping = edittextedit.getText().toString();
                if (shiping.equals("")) {
                    Toast.makeText(getActivity(), "Enter something", Toast.LENGTH_SHORT).show();
                } else {
                    String codrivername = codriver.getText().toString();
                    String tripnor = tripno.getText().toString();


                    shipno.setText(shiping);
                    confirmation_logout.dismiss();
                    //TODO
                    callUpdateShippingAddressAPI(shiping,helperClass.getADD_ATTENDANCE_ID());

                }
            });
            cancel.setOnClickListener(v16 -> confirmation_logout.dismiss());
            confirmation_logout.setCancelable(false);
            confirmation_logout.show();
        });


        if (driveHelper.drivetimerCounting()) {
            startdriveTimer();
        } else {
            stopdriveTimer();
            if (driveHelper.startdriveTime() != null && driveHelper.stopdriveTime() != null) {
                long time = (new Date()).getTime() - this.calcRestartTime().getTime();
                timeStringFromLong(time);
            }
        }
        this.timedrive.scheduleAtFixedRate((TimerTask) (new TimeTas()), 0L, 500L);

        if (breakhelper.breaktimerCounting()) {
            startbreak();

        } else {

            stopbreak();
            if (breakhelper.startbreakTime() != null && breakhelper.stopbreakTime() != null) {
                long time = (new Date()).getTime() - this.offrestarttime().getTime();
                offtimeFromLong(time);

            }
        }

        this.breaktimer.scheduleAtFixedRate((TimerTask) (new TimeBreak()), 0L, 500L);

        if (shifthelper.shifttimerCounting()) {
            startshift();

        } else {

            stopshift();
            if (shifthelper.shiftstartTime() != null && shifthelper.shiftstopTime() != null) {
                long time = (new Date()).getTime() - this.onrestarttime().getTime();
                ontimeFromLong(time);

            }
        }
        this.shifttimer.scheduleAtFixedRate((TimerTask) (new TimeTaskshift()), 0L, 500L);

/*
        if (yardmoveshelper.ytimerCountinge()) {


            starty();

        } else {
            stopy();
            if (yardmoveshelper.ystartTimee() != null && yardmoveshelper.ystopTimee() != null) {
                long time = (new Date()).getTime() - this.yrestarttime().getTime();
                ytimeFromLong(time);
            }
        }
        this.ytimer.scheduleAtFixedRate((TimerTask) (new TimeTasky()), 0L, 500L);


        if (personalHelper.ptimerCountinge()) {
            startp();

        } else {

            stopp();
            if (personalHelper.pstartTimee() != null && personalHelper.pstopTimee() != null) {
                long time = (new Date()).getTime() - this.prestarttime().getTime();
                ptimeFromLong(time);
            }
        }
        this.ptimer.scheduleAtFixedRate((TimerTask) (new TimeTaskp()), 0L, 500L);
*/


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
                int progress = (int) driveprog;
                int remaining = 39600 - progress;
                if (remaining >= 0) {
                    driveprogress.setProgress(progress);
                } else {
                    int nextProgress = progress - 39600 + 3960; // add 3960 seconds (11 hours) to the remaining time
                    driveprogress.setProgress(nextProgress);
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

// Reset progress to zero after 8 hours (28,800 seconds)
                if (nextprogre > 28800) {
                    nextprogre = nextprogre % 28800;
                }

                int nepr = nextprogre - 28800;
                if (nextprogre > 0) {
                    breakprogress.setProgress(nepr);
                } else {
                    breakprogress.setProgress(breakpro);
                }


                //shiftprogress
                long shiftseccc = shiftsec / 1000;
                int shiftprogs = (int) shiftseccc;
                int nextprogr = shiftprogs - 50400;
                int nepre = nextprogr % 50400; // Reset progress after 14 hours

                if (shiftprogs <= 50400) {
                    shiftprogress.setProgress(shiftprogs);
                } else {
                    shiftprogress.setProgress(nepre);
                }

                handler.postDelayed(this, 1000);
            }
        });


        driveprogress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmation_logout.setContentView(R.layout.timingdetail);
                confirmation_logout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextView statusheading = confirmation_logout.findViewById(R.id.statusheading);
                TextView maxhours = confirmation_logout.findViewById(R.id.maxhours);
                TextView timespent = confirmation_logout.findViewById(R.id.timespent);
                TextView timeleft = confirmation_logout.findViewById(R.id.timeleft);
                TextView violation = confirmation_logout.findViewById(R.id.violation);


                statusheading.setText("Driving Detail");
                maxhours.setText("11:00:00");

                final Handler handler = new Handler();
                handler.post(new Runnable() {
                    @SuppressLint("NewApi")
                    @Override
                    public void run() {
                        int total = 39600;
                        long driveprog = drivesec / 1000;
                        int peogressss = (int) driveprog;
                        int left = total - peogressss;
                        int violationn = peogressss - 39600;

                        int hours = peogressss / 3600;
                        int minutes = (peogressss % 3600) / 60;
                        int secs = peogressss % 60;

                        int hourss = left / 3600;
                        int minutess = (left % 3600) / 60;
                        int secss = left % 60;

                        int hour = violationn / 3600;
                        int minute = (violationn % 3600) / 60;
                        int sec = violationn % 60;
                        String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);

                        String timee = String.format(Locale.getDefault(), "%d:%02d:%02d", hourss, minutess, secss);

                        String tim = String.format(Locale.getDefault(), "%d:%02d:%02d", hour, minute, sec);


                        if (peogressss > 39600) {
                            violation.setText(tim);
                            timeleft.setText("00:00:00");
                        } else {
                            timeleft.setText(timee);
                            violation.setText("00:00:00");
                        }

                        timespent.setText(time);

                        handler.postDelayed(this, 1000);
                    }
                });
                confirmation_logout.setCancelable(true);
                confirmation_logout.show();
            }
        });


        weekprogress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmation_logout.setContentView(R.layout.timingdetail);
                confirmation_logout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView statusheading = confirmation_logout.findViewById(R.id.statusheading);
                TextView maxhours = confirmation_logout.findViewById(R.id.maxhours);
                TextView timespent = confirmation_logout.findViewById(R.id.timespent);
                TextView timeleft = confirmation_logout.findViewById(R.id.timeleft);
                TextView violation = confirmation_logout.findViewById(R.id.violation);

                statusheading.setText("Week Detail");
                maxhours.setText("70:00:00");

                final Handler handler = new Handler();
                handler.post(new Runnable() {
                    @SuppressLint("NewApi")
                    @Override
                    public void run() {
                        int total = 252000;
                        long weekse = weeksec / 1000;
                        int weekproggg = (int) weekse;
                        int left = total - weekproggg;
                        int violationn = weekproggg - 252000;

                        int hours = weekproggg / 3600;
                        int minutes = (weekproggg % 3600) / 60;
                        int secs = weekproggg % 60;

                        int hourss = left / 3600;
                        int minutess = (left % 3600) / 60;
                        int secss = left % 60;

                        int hour = violationn / 3600;
                        int minute = (violationn % 3600) / 60;
                        int sec = violationn % 60;
                        String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
                        String timee = String.format(Locale.getDefault(), "%d:%02d:%02d", hourss, minutess, secss);
                        String tim = String.format(Locale.getDefault(), "%d:%02d:%02d", hour, minute, sec);
                        if (weekproggg > 252000) {
                            violation.setText(tim);
                            timeleft.setText("00:00:00");
                        } else {
                            timeleft.setText(timee);
                            violation.setText("00:00:00");
                        }
                        timespent.setText(time);
                        handler.postDelayed(this, 1000);
                    }
                });
                confirmation_logout.setCancelable(true);
                confirmation_logout.show();
            }
        });


        Log.d("CLOCK", "=====driveHelper=====" + driveHelper.drivetimerCounting());
        Log.d("CLOCK", "=====breakhelper=====" + breakhelper.breaktimerCounting());
        Log.d("CLOCK", "=====shifthelper=====" + shifthelper.shifttimerCounting());




       /* if (!heplper.timerCountinge()) {
            long driveprog = weeksec / 1000;
            int peogressss = (int) driveprog;

            int hours = peogressss / 3600;
            int minutes = (peogressss % 3600) / 60;
            int secs = peogressss % 60;
            String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
            weektime.setText(time);
        } else {
            final Handler handl = new Handler();
            handl.post(new Runnable() {
                @SuppressLint("NewApi")
                @Override
                public void run() {

                    long driveprog = weeksec / 1000;
                    int peogressss = (int) driveprog;


                    int hours = peogressss / 3600;
                    int minutes = (peogressss % 3600) / 60;
                    int secs = peogressss % 60;

                    String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);

                    weektime.setText(time);

                    handl.postDelayed(this, 1000);
                }
            });
        }*/


        if (driveHelper.drivetimerCounting()) {
            long driveprog = drivesec / 1000;
            int peogressss = (int) driveprog;
            if (peogressss > 28800) {
                final Handler handlerr = new Handler();
                handlerr.post(new Runnable() {
                    @SuppressLint("NewApi")
                    @Override
                    public void run() {
                        mp.start();
                        handlerr.postDelayed(this, 1000);
                    }
                });
                final Handler handlery = new Handler();
                handlery.post(new Runnable() {
                    @SuppressLint("NewApi")
                    @Override
                    public void run() {
                        secccc++;
                        if (secccc == 9) {
                            mp.stop();
                        }
                        handlery.postDelayed(this, 1000);
                    }
                });
                vibrator.vibrate(8000);

            }


            final Handler handl = new Handler();
            handl.post(new Runnable() {
                @SuppressLint("NewApi")
                @Override
                public void run() {

                    long driveprog = drivesec / 1000;
                    int peogressss = (int) driveprog;


                    int hours = peogressss / 3600;
                    int minutes = (peogressss % 3600) / 60;
                    int secs = peogressss % 60;

                    String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);

                    if (peogressss > 28800) {
                        driveblionk.setVisibility(View.VISIBLE);
                    } else {
                        driveblionk.setVisibility(View.GONE);
                    }


                    driveTimeTV.setText(time);
                    handl.postDelayed(this, 1000);
                }
            });
        } else {
            long driveprog = drivesec / 1000;
            int peogressss = (int) driveprog;

            int hours = peogressss / 3600;
            int minutes = (peogressss % 3600) / 60;
            int secs = peogressss % 60;
            String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
            driveTimeTV.setText(time);
        }
        if (!breakhelper.breaktimerCounting()) {
            long driveprog = breaksec / 1000;
            int peogressss = (int) driveprog;

            int hours = peogressss / 3600;
            int minutes = (peogressss % 3600) / 60;
            int secs = peogressss % 60;
            String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours,

                    minutes, secs);
            breaktime.setText(time);
        } else {
            final Handler handl = new Handler();
            handl.post(new Runnable() {
                @SuppressLint("NewApi")
                @Override
                public void run() {

                    long driveprog = breaksec / 1000;
                    int peogressss = (int) driveprog;


                    int hours = peogressss / 3600;
                    int minutes = (peogressss % 3600) / 60;
                    int secs = peogressss % 60;

                    String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);

                    breaktime.setText(time);

                    handl.postDelayed(this, 1000);
                }
            });
        }
        if (!shifthelper.shifttimerCounting()) {
            long driveprog = shiftsec / 1000;
            int peogressss = (int) driveprog;

            int hours = peogressss / 3600;
            int minutes = (peogressss % 3600) / 60;
            int secs = peogressss % 60;
            String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
            shifttime.setText(time);
        } else {
            final Handler handl = new Handler();
            handl.post(new Runnable() {
                @SuppressLint("NewApi")
                @Override
                public void run() {

                    long driveprog = shiftsec / 1000;
                    int peogressss = (int) driveprog;


                    int hours = peogressss / 3600;
                    int minutes = (peogressss % 3600) / 60;
                    int secs = peogressss % 60;

                    String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);


                    shifttime.setText(time);
                    handl.postDelayed(this, 1000);
                }
            });
        }


        List<Entry> lineEntries = new ArrayList<>();
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet.setLineWidth(2.2f);
        lineDataSet.setDrawValues(false);
        lineDataSet.setColor(Color.GREEN);
        lineDataSet.setCircleRadius(6);
        lineDataSet.setCircleHoleRadius(3);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setValueTextSize(12);
        lineDataSet.setValueTextColor(Color.GREEN);
        lineDataSet.setMode(LineDataSet.Mode.STEPPED);


        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.setTouchEnabled(false);
        lineChart.setPinchZoom(false);
        lineChart.setDoubleTapToZoomEnabled(false);
        // Setup X Axis
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(1.0f);
        xAxis.setXOffset(0.5f);
        xAxis.setLabelCount(25);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(24);
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

        lineChart.getAxisLeft().setCenterAxisLabels(true);
        lineChart.getAxisLeft().setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                if (value == -1 || value >= yAxisLabel.size()) return "";
                return yAxisLabel.get((int) value);
            }
        });
        lineChart.getAxisRight().setEnabled(false);
        lineChart.invalidate();


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

}