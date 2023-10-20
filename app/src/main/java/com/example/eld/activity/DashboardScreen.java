package com.example.eld.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.eld.LoginActivity;
import com.example.eld.R;
import com.example.eld.alert.FiveMinuteN;
import com.example.eld.alert.ResetDataReceiver;
import com.example.eld.fragments.Certify_fragment;
import com.example.eld.fragments.DashboardFragment;
import com.example.eld.fragments.LogsFragment;
import com.example.eld.fragments.Reports_fragment;
import com.example.eld.models.DriverProfileModel;
import com.example.eld.network.dto.attendance.AddAttendanceRecordRequestModel;
import com.example.eld.network.dto.login.response.AddAttendanceRecordResponseModel;
import com.example.eld.network.dto.login.response.ChangePasswordResponseModel;
import com.example.eld.utils.Breakhelper;
import com.example.eld.utils.DriveHelper;
import com.example.eld.utils.Helper;
import com.example.eld.utils.OffDutyHelper;
import com.example.eld.utils.PersonalHelper;
import com.example.eld.utils.Shifthelper;
import com.example.eld.utils.TimestampConverter;
import com.example.eld.utils.WeekHelper;
import com.example.eld.utils.Yardmoveshelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.iosix.eldblelib.EldBleConnectionStateChangeCallback;
import com.iosix.eldblelib.EldBleDataCallback;
import com.iosix.eldblelib.EldBroadcast;
import com.iosix.eldblelib.EldBroadcastTypes;
import com.iosix.eldblelib.EldManager;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import kotlin.jvm.internal.Intrinsics;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardScreen extends BaseActivity {

    private static final int REQUEST_BASE = 100;
    private static final int REQUEST_BT_ENABLE = REQUEST_BASE + 1;
    private static final double STOPPED_SPEED_THRESHOLD = 0.0;
    private static final long FIVE_MINUTES_IN_MS = 300000; // 5 minutes in milliseconds
    private final Timer timedrive = new Timer();
    private final Timer sleeptimer = new Timer();
    private final Timer offtimer = new Timer();
    private final Timer ontimer = new Timer();
    private final Timer ytimer = new Timer();
    private final Timer ptimer = new Timer();
    private final Set<EldBroadcastTypes> subscribedRecords = EnumSet.of(EldBroadcastTypes.ELD_BUFFER_RECORD, EldBroadcastTypes.ELD_CACHED_RECORD, EldBroadcastTypes.ELD_FUEL_RECORD, EldBroadcastTypes.ELD_DATA_RECORD, EldBroadcastTypes.ELD_DRIVER_BEHAVIOR_RECORD, EldBroadcastTypes.ELD_EMISSIONS_PARAMETERS_RECORD, EldBroadcastTypes.ELD_ENGINE_PARAMETERS_RECORD, EldBroadcastTypes.ELD_TRANSMISSION_PARAMETERS_RECORD);
    private final Handler hourlyhandler = new Handler();
    public DriveHelper driveHelper;
    public OffDutyHelper offDutyHelper;
    public WeekHelper weekHelper;
    public Helper heplper;
    public Breakhelper breakhelper;
    public Shifthelper shifthelper;
    public Yardmoveshelper yardmoveshelper;
    public PersonalHelper personalHelper;
    DashboardFragment myFragment;
    BluetoothAdapter bAdapter = null;
    BottomNavigationView bottomNavigationView;
    ImageView side_menu, notification;
    DrawerLayout drawerLayout;
    CardView changestatus;
    CardView onoffbluetooth;
    Dialog confirmation_logout, driverWorkStatusDialog;
    String location;
    FirebaseFirestore firebaseFirestore;
    TextView locationnnv;
    Double latitude = 0.0;
    Double longitude = 0.0;
    Double getOdometer = 0.0;
    Double engineHours = 0.0;
    TextView VIN_no, deviceid, datat;
    float y;
    long drivesec;
    long ondutysec;
    long offdutysec;
    long sleepsec;
    long yarmovsec;
    long psec;
    // ---------NEW PARAMEERS---------------//
    String status = "";
    String orign = "NA";//NA
    //    private final Runnable logRunnable = new Runnable() {
//        @Override
//        public void run() {
//            String daa = datat.getText().toString();
//            location = locationnnv.getText().toString();
//            if (daa.equals("")) {
//                if (daa.equals("")) {
//
//                } else {
//                    String[] units = daa.split(",");
//                    Log.d("TAG", " =========units[4]========== " + units[4]);
//                    if (units.length > 10) {
//                        if (!units[4].toString().isEmpty()) getOdometer = Double.valueOf(units[4]);
//                        if (!units[6].toString().isEmpty()) engineHours = Double.valueOf(units[6]);
//
//                    }
//
//                }
//                //float y = y;// pervious y value will send
//                status = "INT";
//                orign = "Auto";
//                inserdata(getDateTime(), y, getDateTime(), status, location, getOdometer, engineHours, orign, "logs");
//
//            }
//            hourlyhandler.postDelayed(this, 15 * 60 * 1000); // schedule the next execution after 1 hour
//        }
//    };
    String graph = "logs";//NA
    TextView driveTimingView, onDutyTimingView, sleepTimingView, offDutyTimingView, yardTimingView, personalTimingView, conectionstatus, signalStatusView, fullStatusView;
    ImageView onblue, offblue;
    double time = 0.0;
    private PeriodicWorkRequest periodicWorkRequest;
    private WorkManager workManager;
    private long backpresstime;
    private EldManager mEldManager;
    private final EldBleConnectionStateChangeCallback bleConnectionStateChangeCallback = new EldBleConnectionStateChangeCallback() {
        @Override
        public void onConnectionStateChange(final int newState) {
            runOnUiThread(() -> {
                if (newState == 0) {
                    onblue.setVisibility(View.GONE);
                    offblue.setVisibility(View.VISIBLE);

                    conectionstatus.setText("Disconnected");
                    deviceid.setText("Disconnected from ELD");
                } else if (newState == 2) {
                    conectionstatus.setText("Connected");
                    onblue.setVisibility(View.VISIBLE);
                    offblue.setVisibility(View.GONE);

                    deviceid.setText(mEldManager.EldGetSerial());
                } else {
                    conectionstatus.append("New State of connection " + Integer.toString(newState, 10) + "\n");
                }
            });
        }
    };
    private Handler mHandler;
    private Runnable mRunnable;
    private double lastSpeed = 0.0;
    private ActivityResultLauncher<Intent> activityLauncher;
    private boolean isNotificationScheduled = false;
    private final EldBleDataCallback bleDataCallback = new EldBleDataCallback() {
        @Override
        public void OnDataRecord(final EldBroadcast dataRec, final EldBroadcastTypes RecordType) {
            runOnUiThread(() -> {

                if (dataRec.getBroadcastString().length() > 0) {
                    // Remove the "Data: " prefix from the string
                    String[] dataParts = dataRec.getBroadcastString().split(": ");
                    String dataValue = dataParts[1];

                    Log.i("TAG", "======daa========" + dataValue);
                    if (dataRec.getBroadcastString().contains("Data:")) {
                        datat.setText(dataRec.getBroadcastString());
                        String[] units = dataValue.split(",");
                        Log.d("TAG", " =========E_ON_EOFF========== " + units[0].toString());


                        if (units.length > 12) {
                            if (!units[1].isEmpty())
                                VIN_no.setText("VIN: " + units[1].toString());
                            if (units[0].toString().equalsIgnoreCase("1")) {
                                Log.d("TAG", " =========E_ON========== " + units[0].toString());

                                status = "E_ON";


                                helperClass.setStatusChange(true);
                                if (helperClass.getODOMETER().isEmpty() && !units[4].toString().isEmpty()) {
                                    //double odo = convertKmToMiles(Double.parseDouble(units[4])) + 5;
                                    double speed = convertKmToMiles(Double.parseDouble(units[3])) + 5;
                                    helperClass.setODOMETER(String.valueOf(speed));

                                }
                            } else if (units[0].equalsIgnoreCase("0")) {
                                Log.d("TAG", " =========E_OFF========== " + units[0]);
                                status = "E_OFF";

                                helperClass.setIS_DRIVING(false);
                                if (helperClass.getODOMETER().isEmpty() && !units[4].isEmpty()) {
                                    //double odo = convertKmToMiles(Double.parseDouble(units[4])) + 5;
                                    double speed = convertKmToMiles(Double.parseDouble(units[3])) + 5;
                                    helperClass.setODOMETER(String.valueOf(speed));
                                }
                            }
                            if (!units[6].isEmpty())
                                engineHours = Double.parseDouble(units[6]);
                            if (!units[11].isEmpty() && units[11].length() > 0)
                                latitude = Double.parseDouble(units[11]);
                            if (!units[12].isEmpty() && units[11].length() > 0)
                                longitude = Double.parseDouble(units[12]);
                            if (latitude != 0.0 && longitude != 0.0) {
                                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                                try {
                                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                                    Address obj = addresses.get(0);
                                    location = obj.getLocality() + "," + obj.getAdminArea();
                                    locationnnv.setText(location);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            checkDrivingCondition(Double.parseDouble(units[3]));

                        } else {
                            location = "Location Not Available";
                        }
                    }
                }

            });
        }
    };

    public static boolean canDrive5Miles(double speedMph) {

        boolean canDrive = speedMph > 8; // assuming that the car can drive 5 miles within 5 minute
        Log.d("TAG", " =========canDrive========== " + canDrive);
        return canDrive;
    }

    public static double convertKmToMiles(double km) {
        double miles = km / 1.609344;
        //  double miles = km * 0.621371;
        return miles;
    }

    public void onButtonClicked() {

        // myFragment.changeUI();
    }

    @NotNull
    public final DriveHelper getdriveHelper() {
        DriveHelper helper = this.driveHelper;
        if (helper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("driveHelper");
        }
        return helper;
    }

    @NotNull
    public final Helper getHeplper() {
        Helper helperr = this.heplper;
        if (helperr == null) {
            Intrinsics.throwUninitializedPropertyAccessException("helper");
        }
        return helperr;
    }

    @NotNull
    public final Breakhelper getbreakhelper() {
        Breakhelper breakhelper = this.breakhelper;
        if (breakhelper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("breakhelper");
        }
        return breakhelper;
    }

    @NotNull
    public final Shifthelper getshifthelper() {
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

    public void initview() {
        bottomNavigationView = findViewById(R.id.bottomnavigationn);
        drawerLayout = findViewById(R.id.home_drawer_lay);
        side_menu = findViewById(R.id.side_menu);
        notification = findViewById(R.id.notification);
        locationnnv = findViewById(R.id.locationnnv);
        changestatus = findViewById(R.id.changestatus);
        deviceid = findViewById(R.id.deviceid);
        VIN_no = findViewById(R.id.VIN_no);
        driveTimingView = findViewById(R.id.driveingtiming);
        onDutyTimingView = findViewById(R.id.ondutytiming);
        sleepTimingView = findViewById(R.id.sleeptiming);
        offDutyTimingView = findViewById(R.id.offdutytiming);
        yardTimingView = findViewById(R.id.yardtiming);
        personalTimingView = findViewById(R.id.personaltiming);
        signalStatusView = findViewById(R.id.signal_status);
        fullStatusView = findViewById(R.id.fullstatus);
        onblue = findViewById(R.id.onblue);
        offblue = findViewById(R.id.offblue);
        onoffbluetooth = findViewById(R.id.onoffbluetooth);
        conectionstatus = findViewById(R.id.conectionstatus);
        datat = findViewById(R.id.datat);

        firebaseFirestore = FirebaseFirestore.getInstance();

        driveHelper = new DriveHelper(getApplicationContext());
        offDutyHelper = new OffDutyHelper(getApplicationContext());
        heplper = new Helper(getApplicationContext());
        breakhelper = new Breakhelper(getApplicationContext());
        shifthelper = new Shifthelper(getApplicationContext());
        yardmoveshelper = new Yardmoveshelper(getApplicationContext());
        personalHelper = new PersonalHelper(getApplicationContext());
        confirmation_logout = new Dialog(this);
        driverWorkStatusDialog = new Dialog(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }

        mEldManager = EldManager.GetEldManager(this, "123456789A");

//BLUETOOTH CONNECTIVITY
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            bAdapter = BluetoothAdapter.getDefaultAdapter();

        }

        if (bAdapter.isEnabled()) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_DENIED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_SCAN}, 2);
                }
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 2);
                }
            }
            ScanForEld();

        } else {
//            conectionstatus.setText("Enable\nBluetooth");

        }



     /*   Log.d("TAG", " =========STATUSCHANGE========== " + helperClass.getStatusChange(this));
        if (helperClass.getStatusChange(this)) {
            datat.setText("1,3AKJGLBG8GSHB0540,555,23,791565.400,0.000,20238.40,0.00,13.28,03/17/23,13:48:36,39.619926,-74.787261,0,68,8,9,1.2,4524,315");
            //datat.setText("0,3AKJGLBG8GSHB0540,0,73,,,20238.40,,,,,,,,,68,8,9,1.2,4524,315");
        } else {
            datat.setText("1,3AKJGLBG8GSHB0540,0,0,791265,,,,,,,,,,,68,8,9,1.2,4524,315");
        }
        String[] units = datat.getText().toString().split(",");*/

        boolean condition = getIntent().getBooleanExtra("condition", false);
        boolean houly = getIntent().getBooleanExtra("houly", false);
        Log.d("TAG", "======condition========" + condition);
        Log.d("TAG", "======condition========" + houly);

        // Do something with the retrieved values
        if (condition) {
            dialog_StillDriving();
        } else if (houly) {
            createINTLog();
        }


        onoffbluetooth.setOnClickListener(v -> {
            String status = conectionstatus.getText().toString();
            if (status.equals("Disconnect")) {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, 0);
            } else if (status.equals("Connected")) {
                confirmation_logout.setContentView(R.layout.dialog_logout);
                confirmation_logout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                CardView yes = confirmation_logout.findViewById(R.id.btnLogout);
                CardView no = confirmation_logout.findViewById(R.id.btnCancel);
                TextView text = confirmation_logout.findViewById(R.id.logoputtext);
                text.setText("Do you want to Disconnect the device?");

                yes.setOnClickListener(v1 -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        bAdapter.disable();
                    }
                    confirmation_logout.dismiss();
                });
                no.setOnClickListener(v12 -> confirmation_logout.dismiss());
                confirmation_logout.setCancelable(false);
                confirmation_logout.show();
            } else if (status.equals("No Device")) {
                //request android 12 runtime bluetooth permissions
                if (ContextCompat.checkSelfPermission(DashboardScreen.this, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_DENIED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        ActivityCompat.requestPermissions(DashboardScreen.this, new String[]{Manifest.permission.BLUETOOTH_SCAN}, 2);
                    }
                }
                if (ContextCompat.checkSelfPermission(DashboardScreen.this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        ActivityCompat.requestPermissions(DashboardScreen.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 2);
                    }
                }
                conectionstatus.setText("SCANNING...");
                ScanForEld();
            }
        });

        notification.setOnClickListener(v -> startActivity(new Intent(DashboardScreen.this, Notification_screen.class)));
        side_menu.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        helperClass.setDASHBOARD(true);
        myFragment = new DashboardFragment();
        controlfunction(myFragment);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.myhome:
                    myFragment = new DashboardFragment();
                    helperClass.setDASHBOARD(true);
                    controlfunction(myFragment);
                    break;
                case R.id.logs:
                    helperClass.setDASHBOARD(true);
                    controlfunction(new LogsFragment());
                    break;
                case R.id.certfy:
                    helperClass.setDASHBOARD(true);
                    controlfunction(new Certify_fragment());
                    break;
                case R.id.reports:
                    helperClass.setDASHBOARD(true);
                    controlfunction(new Reports_fragment());
                    break;

            }
            return true;
        });

    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_screen);
        workManager = WorkManager.getInstance();
        initview();

        changestatus.setOnClickListener(v -> {

            driverWorkStatusDialog.setContentView(R.layout.change_status_dilog);
            driverWorkStatusDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            driverWorkStatusDialog.setCancelable(true);

            CardView drive = driverWorkStatusDialog.findViewById(R.id.drive);
            CardView onDuty = driverWorkStatusDialog.findViewById(R.id.onduty);
            CardView sleep = driverWorkStatusDialog.findViewById(R.id.sleep);
            CardView offDuty = driverWorkStatusDialog.findViewById(R.id.offduty);
            CardView yard = driverWorkStatusDialog.findViewById(R.id.yard);
            CardView personalUse = driverWorkStatusDialog.findViewById(R.id.personaluse);

            setClickListeners(drive, onDuty, sleep, offDuty, yard, personalUse);
            driverWorkStatusDialog.show();
        });
    }

    private void setClickListeners(CardView... statusCards) {
        for (CardView card : statusCards) {
            card.setOnClickListener(view -> handleStatusChange(card));
        }
    }

    private void handleStatusChange(CardView card) {
        driverWorkStatusDialog.dismiss();
        String status = getStatusFromCard(card);
        updateUIForStatus(status);
        String location = locationnnv.getText().toString();
        String data = datat.getText().toString();
        orign = "manual";
        String attendanceType = "";
        if (!data.isEmpty()) {
            processData(data);
        }
        if (status.equals("Drive")) {
            insertData(getDateTime(), y, getDateTime(), status, location, getOdometer, engineHours, orign, "logs");
            startStopDriveAction();
            attendanceType = "Drive";
        } else if (status.equals("Off duty")) {
            attendanceType = "OffDuty";
            handleOffDuty();
        } else if (status.equals("On duty")) {
            attendanceType = "onDuty";
            handleOnDuty();
        } else if (status.equals("Sleep berth")) {
            attendanceType = "sleep";
            handleSleepData();
        } else if (status.equals("Yard move")) {
            attendanceType = "yardMove";
            handleYardData();
        } else if (status.equals("Personal use")) {
            attendanceType = "personalUse";
            insertPersonalUseData();
        }
        callAddAttendanceRecord(status, attendanceType);

    }


    private String getStatusFromCard(CardView card) {
        int cardId = card.getId();
        switch (cardId) {
            case R.id.drive:
                return "Drive";
            case R.id.onduty:
                return "On duty";
            case R.id.sleep:
                return "Sleep berth";
            case R.id.offduty:
                return "Off duty";
            case R.id.yard:
                return "Yard move";
            case R.id.personaluse:
                return "Personal use";
            default:
                return "Unknown";
        }
    }

    private void updateUIForStatus(String status) {
        signalStatusView.setText(getStatusAbbreviation(status));
        fullStatusView.setText(status);
        updateTimingViewsForStatus(status);
    }

    private String getStatusAbbreviation(String status) {
        String abbreviation = "";
        switch (status) {
            case "Drive":
                abbreviation = "D";
                break;
            case "On duty":
                abbreviation = "OD";
                break;
            case "Sleep berth":
                abbreviation = "SB";
                break;
            case "Off duty":
                abbreviation = "OFF";
                break;
            case "Yard move":
                abbreviation = "YM";
                break;
            case "Personal use":
                abbreviation = "PU";
                break;
            default:
                abbreviation = "N/A";
                break;
        }
        return abbreviation;
    }

    private void updateTimingViewsForStatus(String status) {
        switch (status) {
            case "Drive":
                setVisibility(driveTimingView, View.VISIBLE);
                setVisibility(onDutyTimingView, View.GONE);
                setVisibility(sleepTimingView, View.GONE);
                setVisibility(offDutyTimingView, View.GONE);
                setVisibility(yardTimingView, View.GONE);
                setVisibility(personalTimingView, View.GONE);
                break;
            case "On duty":
                setVisibility(driveTimingView, View.GONE);
                setVisibility(onDutyTimingView, View.VISIBLE);
                setVisibility(sleepTimingView, View.GONE);
                setVisibility(offDutyTimingView, View.GONE);
                setVisibility(yardTimingView, View.GONE);
                setVisibility(personalTimingView, View.GONE);
                break;
            case "Sleep berth":
                setVisibility(driveTimingView, View.GONE);
                setVisibility(onDutyTimingView, View.GONE);
                setVisibility(sleepTimingView, View.VISIBLE);
                setVisibility(offDutyTimingView, View.GONE);
                setVisibility(yardTimingView, View.GONE);
                setVisibility(personalTimingView, View.GONE);
                break;
            case "Off duty":
                setVisibility(driveTimingView, View.GONE);
                setVisibility(onDutyTimingView, View.GONE);
                setVisibility(sleepTimingView, View.GONE);
                setVisibility(offDutyTimingView, View.VISIBLE);
                setVisibility(yardTimingView, View.GONE);
                setVisibility(personalTimingView, View.GONE);
                break;
            case "Yard move":
                setVisibility(driveTimingView, View.GONE);
                setVisibility(onDutyTimingView, View.GONE);
                setVisibility(sleepTimingView, View.GONE);
                setVisibility(offDutyTimingView, View.GONE);
                setVisibility(yardTimingView, View.VISIBLE);
                setVisibility(personalTimingView, View.GONE);
                break;
            case "Personal use":
                setVisibility(driveTimingView, View.GONE);
                setVisibility(onDutyTimingView, View.GONE);
                setVisibility(sleepTimingView, View.GONE);
                setVisibility(offDutyTimingView, View.GONE);
                setVisibility(yardTimingView, View.GONE);
                setVisibility(personalTimingView, View.VISIBLE);
                break;
            default:
                // Handle an unrecognized status here
                break;
        }
    }

    // Helper function to set the visibility of a view
    private void setVisibility(View view, int visibility) {
        if (view != null) {
            view.setVisibility(visibility);
        }
    }

    private void processData(String data) {
        String[] units = data.split(",");
        if (units.length > 10) {
            if (!units[4].isEmpty())
                getOdometer = Double.valueOf(units[4]);
            if (!units[6].isEmpty())
                engineHours = Double.valueOf(units[6]);
        }
    }

    private void insertData(String x_axis, float y_axis, String dateTime, String status, String location, Double getOdometer, Double engineHours, String orign, String graph) {
        Double odoo = (getOdometer * 1000) / 1609.344;
        DecimalFormat df = new DecimalFormat("#");
        y = y_axis;
        Log.d("TAG", " =========x=========> " + x_axis);
        Log.d("TAG", " =========y_axis=========> " + y_axis);
        HashMap hashMap = new HashMap();
        hashMap.put("x", x_axis + "");
        hashMap.put("y", y_axis + "");
        hashMap.put("time", FieldValue.serverTimestamp());
        hashMap.put("status", status);
        hashMap.put("location", location);
        hashMap.put("odometer", df.format(odoo) + "");
        hashMap.put("eh", engineHours + "");
        hashMap.put("orign", orign + "");
        hashMap.put("graph", graph);
//        firebaseFirestore.collection(helperClass.getid(getApplicationContext())).add(hashMap);
    }

    private void startStopDriveAction() {
        if (driveHelper.drivetimerCounting()) {
            handleDriveTimerRunning();
        } else {
            handleDriveTimerNotRunning();
        }
    }

    private void handleDriveTimerRunning() {
        driveHelper.setstopdriveTime(new Date());
        Toast.makeText(getApplicationContext(), "You are already in this mode", Toast.LENGTH_SHORT).show();
    }

    private void handleDriveTimerNotRunning() {
        if (driveHelper.stopdriveTime() != null) {
            driveHelper.setstartdriveTime(calcRestartTime());
            driveHelper.setstopdriveTime(null);
        } else {
            driveHelper.setstartdriveTime(new Date());
        }

        handleStopwatchTimers();
        startDriveTimer();
        startbreak();

        updateDriveTimeUI();
    }

    private void handleStopwatchTimers() {
        if (heplper.isTimerCounting()) {
            heplper.setStopTime(new Date());
            stopsleep();
        }
        if (yardmoveshelper.ytimerCountinge()) {
            yardmoveshelper.ysetStopTimee(new Date());
            stopyard();
        }
        if (personalHelper.ptimerCountinge()) {
            personalHelper.psetStopTimee(new Date());
            stopp();
        }

        if (breakhelper.stopbreakTime() != null) {
            breakhelper.setstartbreakTime(offrestarttime());
            breakhelper.setstopbreakTime(null);
        } else {
            breakhelper.setstartbreakTime(new Date());
        }
    }

    private void updateDriveTimeUI() {
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
                driveTimingView.setText(time);
                handl.postDelayed(this, 1000);
            }
        });

        if (helperClass.getDASHBOARD()) {
            myFragment.changeDriveandBreakUI(true, true);
        }
    }

    private void handleOffDuty() {
        driverWorkStatusDialog.dismiss();
        if (offDutyHelper.offdutytimerCounting()) {
            Toast.makeText(getApplicationContext(), "You are already in this mode", Toast.LENGTH_SHORT).show();
        } else {
            driverWorkStatusDialog.dismiss();
            showOffDutyReasonDialog();
        }
    }


    private void showOffDutyReasonDialog() {
        confirmation_logout.setContentView(R.layout.edit_dialog);
        confirmation_logout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        CardView updateButton = confirmation_logout.findViewById(R.id.update);
        CardView cancel = confirmation_logout.findViewById(R.id.cancel);
        TextView text = confirmation_logout.findViewById(R.id.dialog_title);
        EditText edittextedit = confirmation_logout.findViewById(R.id.edittextedit);
        text.setText("Off duty reason");

        updateButton.setOnClickListener(v -> {
            String result = edittextedit.getText().toString();
            if (result.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Enter off duty reason", Toast.LENGTH_SHORT).show();
            } else {
                handleOffDutyMode(result);
            }
        });

        cancel.setOnClickListener(v -> {
            confirmation_logout.dismiss();
            driverWorkStatusDialog.show();
        });

        confirmation_logout.setCancelable(false);
        confirmation_logout.show();
    }

    private void handleOffDutyMode(String reason) {
        updateOffDutyUI();
        confirmation_logout.dismiss();
        location = locationnnv.getText().toString();
        String data = datat.getText().toString();
        processData(data);
        float y = 4f;
        status = "OFFD";
        insertData(getDateTime(), y, getDateTime(), status, location, getOdometer, engineHours, orign, "logs");
        Toast.makeText(getApplicationContext(), "Your` reason - " + reason, Toast.LENGTH_SHORT).show();
        startstopOFFDuty();
    }

    private void updateOffDutyUI() {
        // Set the signal status and full status
        signalStatusView.setText("OFF");
        fullStatusView.setText("Off Duty");

        // Update the visibility of timing views
        setVisibility(driveTimingView, View.GONE);
        setVisibility(onDutyTimingView, View.GONE);
        setVisibility(sleepTimingView, View.GONE);
        setVisibility(offDutyTimingView, View.VISIBLE);
        setVisibility(yardTimingView, View.GONE);
        setVisibility(personalTimingView, View.GONE);
    }

    private void handleOnDuty() {
        if (shifthelper.shifttimerCounting()) {
            handleOnDutyAlreadyRunning();
        } else {
            driverWorkStatusDialog.dismiss();
            showOnDutyReasonDialog();
        }
    }

    private void handleOnDutyAlreadyRunning() {
        // Shift timer is already running
        driverWorkStatusDialog.dismiss();
        Toast.makeText(getApplicationContext(), "You are already in this mode", Toast.LENGTH_SHORT).show();
    }

    private void showOnDutyReasonDialog() {
        confirmation_logout.setContentView(R.layout.edit_dialog);
        confirmation_logout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        CardView updateButton = confirmation_logout.findViewById(R.id.update);
        CardView cancel = confirmation_logout.findViewById(R.id.cancel);
        TextView text = confirmation_logout.findViewById(R.id.dialog_title);
        EditText edittextedit = confirmation_logout.findViewById(R.id.edittextedit);

        text.setText("On duty reason");

        updateButton.setOnClickListener(v -> {
            String result = edittextedit.getText().toString();
            if (result.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Enter something", Toast.LENGTH_SHORT).show();
            } else {
                handleOnDutyMode(result);
            }
        });

        cancel.setOnClickListener(v -> {
            confirmation_logout.dismiss();
            driverWorkStatusDialog.show();
        });

        confirmation_logout.setCancelable(false);
        confirmation_logout.show();
    }

    private void handleOnDutyMode(String reason) {
        signalStatusView.setText("ON");
        fullStatusView.setText("On duty");
        driveTimingView.setVisibility(View.GONE);
        onDutyTimingView.setVisibility(View.VISIBLE);
        sleepTimingView.setVisibility(View.GONE);
        offDutyTimingView.setVisibility(View.GONE);
        yardTimingView.setVisibility(View.GONE);
        personalTimingView.setVisibility(View.GONE);

        Toast.makeText(getApplicationContext(), "Your reason - " + reason, Toast.LENGTH_SHORT).show();

        confirmation_logout.dismiss();

        location = locationnnv.getText().toString();
        String data = datat.getText().toString();

        processData(data);
        float y = 1f;
        orign = "Manual";

        insertData(getDateTime(), y, getDateTime(), status, location, getOdometer, engineHours, orign, "logs");
        startstopshift(true);
    }

    private void handleSleepData() {
        String dateTime = getDateTime();

        // Update UI elements for Sleep Berth mode
        signalStatusView.setText("SB");
        fullStatusView.setText("Sleep Berth");
        driveTimingView.setVisibility(View.GONE);
        onDutyTimingView.setVisibility(View.GONE);
        sleepTimingView.setVisibility(View.VISIBLE);
        offDutyTimingView.setVisibility(View.GONE);
        yardTimingView.setVisibility(View.GONE);
        personalTimingView.setVisibility(View.GONE);

        // Additional processing and data retrieval (location, odometer, engine hours, etc.)
        location = locationnnv.getText().toString();
        String data = datat.getText().toString();
        processData(data);

        // Define sleep-related parameters
        float y = 3f;
        status = "Sleep";
        orign = "Manual";

        // Insert sleep data into your data repository or database
        insertData(dateTime, y, dateTime, status, location, getOdometer, engineHours, orign, "logs");

        // Perform any additional actions specific to Sleep Berth mode
        startStopSleep();
    }

    private void handleYardData() {
        String dateTime = getDateTime();

        // Update UI elements for Yard Move mode
        signalStatusView.setText("YM");
        fullStatusView.setText("Yard Move");
        driveTimingView.setVisibility(View.GONE);
        onDutyTimingView.setVisibility(View.GONE);
        sleepTimingView.setVisibility(View.GONE);
        offDutyTimingView.setVisibility(View.GONE);
        yardTimingView.setVisibility(View.VISIBLE);
        personalTimingView.setVisibility(View.GONE);

        // Additional processing and data retrieval (location, odometer, engine hours, etc.)
        location = locationnnv.getText().toString();
        // Define yard move-related parameters
        float y = 2f;
        status = "YM";
        orign = "Manual";

        // Insert yard move data into your data repository or database
        insertData(dateTime, y, dateTime, status, location, getOdometer, engineHours, orign, "logs");

        // Perform any additional actions specific to Yard Move mode
        startStopYard();

        // If needed, update other variables or elements specific to Yard Move
        helperClass.setIS_DRIVING(false);
        helperClass.setODOMETER("");
    }

    private void insertPersonalUseData() {
        String dateTime = getDateTime();

        // Update UI elements for Personal Use mode
        signalStatusView.setText("PU");
        fullStatusView.setText("Personal Use");
        driveTimingView.setVisibility(View.GONE);
        onDutyTimingView.setVisibility(View.GONE);
        sleepTimingView.setVisibility(View.GONE);
        offDutyTimingView.setVisibility(View.GONE);
        yardTimingView.setVisibility(View.GONE);
        personalTimingView.setVisibility(View.VISIBLE);

        // Additional processing and data retrieval (location, odometer, engine hours, etc.)
        location = locationnnv.getText().toString();

        // Define personal use-related parameters
        float y = 2f;
        status = "PU";
        orign = "Manual";

        // Insert personal use data into your data repository or database
        insertData(dateTime, y, dateTime, status, location, getOdometer, engineHours, orign, "logs");

        // Perform any additional actions specific to Personal Use mode
        startStopPersonalTime();


        // If needed, update other variables or elements specific to Personal Use
        helperClass.setIS_DRIVING(false);
        helperClass.setODOMETER("");
    }

            /*  drive.setOnClickListener(v16 ->

    {
        driverWorkStatusDialog.dismiss();
        signalStatusView.setText("D");
        fullStatusView.setText("Drive");
        driveTimingView.setVisibility(View.VISIBLE);
        onDutyTimingView.setVisibility(View.GONE);
        sleepTimingView.setVisibility(View.GONE);
        offDutyTimingView.setVisibility(View.GONE);
        yardTimingView.setVisibility(View.GONE);
        personalTimingView.setVisibility(View.GONE);
        location = locationnnv.getText().toString();

        String daa = datat.getText().toString();

        if (!daa.equals("")) {
            String[] units = daa.split(",");
            if (units.length > 10) {
                if (!units[4].isEmpty())
                    getOdometer = Double.valueOf(units[4]);
                if (!units[6].isEmpty())
                    engineHours = Double.valueOf(units[6]);

            }
        }
        float y = 2f;
        status = "Drive";
        orign = "Manual";

        insertData(getDateTime(), y, getDateTime(), status, location, getOdometer, engineHours, orign, "logs");
        startStopDriveAction();
        // callAddAttendanceRecord(status,);

    });
            offduty.setOnClickListener(v1 ->

    {
        if (offDutyHelper.offdutytimerCounting()) {
            driverWorkStatusDialog.dismiss();
            Toast.makeText(getApplicationContext(), "You are already in this mode", Toast.LENGTH_SHORT).show();
        } else {
            driverWorkStatusDialog.dismiss();
            confirmation_logout.setContentView(R.layout.edit_dialog);
            confirmation_logout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            CardView upDateBtn = confirmation_logout.findViewById(R.id.update);
            CardView cancel = confirmation_logout.findViewById(R.id.cancel);
            TextView text = confirmation_logout.findViewById(R.id.dialog_title);
            EditText edittextedit = confirmation_logout.findViewById(R.id.edittextedit);
            text.setText("Off duty reason");
            upDateBtn.setOnClickListener(v11 -> {
                String result = edittextedit.getText().toString();
                if (result.equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter something", Toast.LENGTH_SHORT).show();
                } else {
                    signalStatusView.setText("OFF");
                    fullStatusView.setText("Off duty");
                    driveTimingView.setVisibility(View.GONE);
                    onDutyTimingView.setVisibility(View.GONE);
                    sleepTimingView.setVisibility(View.GONE);
                    offDutyTimingView.setVisibility(View.VISIBLE);
                    yardTimingView.setVisibility(View.GONE);
                    personalTimingView.setVisibility(View.GONE);

                    Toast.makeText(getApplicationContext(), "Your reason - " + result, Toast.LENGTH_SHORT).show();

                    confirmation_logout.dismiss();
                    location = locationnnv.getText().toString();
                    String daa = datat.getText().toString();

                    if (!daa.equals("")) {
                        String[] units = daa.split(",");
                        if (units.length > 10) {
                            if (!units[4].isEmpty())
                                getOdometer = Double.valueOf(units[4]);
                            if (!units[6].isEmpty())
                                engineHours = Double.valueOf(units[6]);

                        }
                    }

                    float y = 4f;
                    status = "OFFD";
                    orign = "Manual";

                    insertData(getDateTime(), y, getDateTime(), status, location, getOdometer, engineHours, orign, "logs");
                    startstopOFFDuty();

                }
            });
            cancel.setOnClickListener(v112 -> {
                confirmation_logout.dismiss();
                driverWorkStatusDialog.show();
            });
            confirmation_logout.setCancelable(false);
            confirmation_logout.show();
        }

    });
            onduty.setOnClickListener(v12 ->

    {

        if (shifthelper.shifttimerCounting()) {
            driverWorkStatusDialog.dismiss();
            Toast.makeText(getApplicationContext(), "You are already in this mode", Toast.LENGTH_SHORT).show();
        } else {
            driverWorkStatusDialog.dismiss();
            confirmation_logout.setContentView(R.layout.edit_dialog);
            confirmation_logout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            CardView yes = confirmation_logout.findViewById(R.id.update);
            CardView cancel = confirmation_logout.findViewById(R.id.cancel);
            TextView text = confirmation_logout.findViewById(R.id.dialog_title);
            EditText edittextedit = confirmation_logout.findViewById(R.id.edittextedit);
            text.setText("On duty reason");

            yes.setOnClickListener(v121 -> {
                String result = edittextedit.getText().toString();
                if (result.equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter something", Toast.LENGTH_SHORT).show();
                } else {
                    signalStatusView.setText("ON");
                    fullStatusView.setText("on duty");
                    driveTimingView.setVisibility(View.GONE);
                    onDutyTimingView.setVisibility(View.VISIBLE);
                    sleepTimingView.setVisibility(View.GONE);
                    offDutyTimingView.setVisibility(View.GONE);
                    yardTimingView.setVisibility(View.GONE);
                    personalTimingView.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Your reason - " + result, Toast.LENGTH_SHORT).show();
                    confirmation_logout.dismiss();

                    location = locationnnv.getText().toString();
                    String daa = datat.getText().toString();

                    if (!daa.equals("")) {
                        String[] units = daa.split(",");
                        if (units.length > 10) {
                            if (!units[4].isEmpty())
                                getOdometer = Double.valueOf(units[4]);
                            if (!units[6].isEmpty())
                                engineHours = Double.valueOf(units[6]);

                        }
                    }

                    float y = 1f;
                    status = "OND";
                    orign = "Manual";

                    insertData(getDateTime(), y, getDateTime(), status, location, getOdometer, engineHours, orign, "logs");
                    startstopshift(true);
                }
            });
            cancel.setOnClickListener(v1212 -> {
                confirmation_logout.dismiss();
                driverWorkStatusDialog.show();
            });
            confirmation_logout.setCancelable(false);
            confirmation_logout.show();
        }

    });
            sleep.setOnClickListener(v13 ->

    {
        driverWorkStatusDialog.dismiss();
        signalStatusView.setText("SB");
        fullStatusView.setText("Sleep berth");
        driveTimingView.setVisibility(View.GONE);
        onDutyTimingView.setVisibility(View.GONE);
        sleepTimingView.setVisibility(View.VISIBLE);
        offDutyTimingView.setVisibility(View.GONE);
        yardTimingView.setVisibility(View.GONE);
        personalTimingView.setVisibility(View.GONE);
        float y = 3f;
        String status = "Sleep";
        location = locationnnv.getText().toString();
        String daa = datat.getText().toString();

        if (daa.equals("")) {

        } else {
            String[] units = daa.split(",");
            if (units.length > 10) {
                if (!units[4].isEmpty())
                    getOdometer = Double.valueOf(units[4]);
                if (!units[6].isEmpty())
                    engineHours = Double.valueOf(units[6]);

            }
        }
        orign = "Manual";

        insertData(getDateTime(), y, getDateTime(), status, location, getOdometer, engineHours, orign, "logs");
        startstopsleep();

    });
            yard.setOnClickListener(v14 ->

    {
        driverWorkStatusDialog.dismiss();

        signalStatusView.setText("YM");
        fullStatusView.setText("Yard move");
        driveTimingView.setVisibility(View.GONE);
        onDutyTimingView.setVisibility(View.GONE);
        sleepTimingView.setVisibility(View.GONE);
        offDutyTimingView.setVisibility(View.GONE);
        yardTimingView.setVisibility(View.VISIBLE);
        personalTimingView.setVisibility(View.GONE);
        location = locationnnv.getText().toString();
        String daa = datat.getText().toString();
        float y = 2f;
        status = "YM";
        if (daa.equals("")) {

        } else {
            String[] units = daa.split(",");
            if (units.length > 10) {
                if (!units[4].isEmpty())
                    getOdometer = Double.valueOf(units[4]);
                if (!units[6].isEmpty())
                    engineHours = Double.valueOf(units[6]);

            }
        }
        orign = "Manual";

        insertData(getDateTime(), y, getDateTime(), status, location, getOdometer, engineHours, orign, "logs");
        startstopy();

        helperClass.setIS_DRIVING(false);
        helperClass.setODOMETER("");

    });

            personaluse.setOnClickListener(v15 ->

    {
        driverWorkStatusDialog.dismiss();

        signalStatusView.setText("PU");
        fullStatusView.setText("Personal use");
        driveTimingView.setVisibility(View.GONE);
        onDutyTimingView.setVisibility(View.GONE);
        sleepTimingView.setVisibility(View.GONE);
        offDutyTimingView.setVisibility(View.GONE);
        yardTimingView.setVisibility(View.GONE);
        personalTimingView.setVisibility(View.VISIBLE);
        location = locationnnv.getText().toString();
        String daa = datat.getText().toString();

        float y = 2f;
        status = "PU";
        if (daa.equals("")) {

        } else {
            String[] units = daa.split(",");
            if (units.length > 10) {
                if (!units[4].isEmpty())
                    getOdometer = Double.valueOf(units[4]);
                if (!units[6].isEmpty())
                    engineHours = Double.valueOf(units[6]);

            }
        }

        orign = "Manual";

        insertData(getDateTime(), y, getDateTime(), status, location, getOdometer, engineHours, orign, "logs");

        startstopy();
        helperClass.setIS_DRIVING(false);
        helperClass.setODOMETER("");
    });*/


    @Override
    public void onBackPressed() {
        if (bottomNavigationView.getSelectedItemId() == R.id.myhome) {
            if (backpresstime + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
                mEldManager.DisconnectEld();
                return;
            } else {
                Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            }
            backpresstime = System.currentTimeMillis();
        } else {
            bottomNavigationView.setSelectedItemId(R.id.myhome);
        }
    }

    public void controlfunction(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.myframelayout, fragment).commit();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_BT_ENABLE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
//                mEldManager.ScanForElds(bleScanCallback);
            } else {
                conectionstatus.append("Unable to enable bluetooth\n");
            }
        }
    }

    private void ScanForEld() {
//        if (mEldManager.ScanForElds(bleScanCallback) == EldBleError.BLUETOOTH_NOT_ENABLED)
//            mEldManager.EnableBluetooth(REQUEST_BT_ENABLE);
    }

    private String getDateTime() {

        return TimestampConverter.generateTimestamp();
    }

    private final void stopoffdutyTimer() {
        offDutyHelper.setoffdutytimerCounting(false);
    }

    private final void startoffdutyTimer() {
        offDutyHelper.setoffdutytimerCounting(true);
    }

    private final void stopDriveTimer() {
        driveHelper.setdrivetimerCounting(false);
        //updateValue(false);
    }

    private final void startDriveTimer() {
        driveHelper.setdrivetimerCounting(true);
    }

//    private final void startStopDriveAction() {
//
//        if (driveHelper.drivetimerCounting()) {
//            driveHelper.setstopdriveTime(new Date());
//            //stopDriveTimer();
//            Toast.makeText(getApplicationContext(), "You are already in this mode", Toast.LENGTH_SHORT).show();
//        } else {
//
//            if (driveHelper.stopdriveTime() != null) {
//                driveHelper.setstartdriveTime(calcRestartTime());
//                driveHelper.setstopdriveTime(null);
//            } else {
//                driveHelper.setstartdriveTime(new Date());
//            }
//            if (heplper.timerCountinge()) {
//                heplper.setStopTimee(new Date());
//                stopsleep();
//            }
//            if (yardmoveshelper.ytimerCountinge()) {
//                yardmoveshelper.ysetStopTimee(new Date());
//                stopy();
//            }
//            if (personalHelper.ptimerCountinge()) {
//                personalHelper.psetStopTimee(new Date());
//                stopp();
//            }
//
//            if (breakhelper.stopbreakTime() != null) {
//                breakhelper.setstartbreakTime(offrestarttime());
//                breakhelper.setstopbreakTime(null);
//            } else {
//                breakhelper.setstartbreakTime(new Date());
//            }
//            startDriveTimer();
//            startbreak();
//
//            final Handler handl = new Handler();
//            handl.post(new Runnable() {
//                @SuppressLint("NewApi")
//                @Override
//                public void run() {
//
//                    long driveprog = drivesec / 1000;
//                    int peogressss = (int) driveprog;
//
//                    int hours = peogressss / 3600;
//                    int minutes = (peogressss % 3600) / 60;
//                    int secs = peogressss % 60;
//                    String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
//                    driveTimingView.setText(time);
//
//                    handl.postDelayed(this, 1000);
//                }
//            });
//            if (helperClass.getDASHBOARD()) {
//                myFragment.changeDriveandBreakUI(true, true);
//
//            }
//
//        }
//    }

    private final void stopsleep() {
        heplper.setTimerCountinge(false);
    }

    private final void startsleep() {
        heplper.setTimerCountinge(true);

    }

    private final void startStopSleep() {
        if (heplper.isTimerCounting()) {
            Toast.makeText(getApplicationContext(), "You are already in this mode", Toast.LENGTH_SHORT).show();
        } else {
            if (heplper.stopTime() != null) {
                heplper.setStartTime(restarttime());
                heplper.setStopTime(null);
            } else {
                heplper.setStartTime(new Date());
            }

            if (driveHelper.drivetimerCounting()) {
                driveHelper.setstartdriveTime(new Date());
                stopDriveTimer();
            }
            if (breakhelper.breaktimerCounting()) {
                breakhelper.setstopbreakTime(new Date());
                stopbreak();
            }
            if (shifthelper.shifttimerCounting()) {
                shifthelper.setshiftstopTime(new Date());
                stopshift();
            }
            if (yardmoveshelper.ytimerCountinge()) {
                yardmoveshelper.ysetStopTimee(new Date());
                stopyard();
            }
            if (personalHelper.ptimerCountinge()) {
                personalHelper.psetStopTimee(new Date());
                stopp();
            }
            if (offDutyHelper.offdutytimerCounting()) {
                offDutyHelper.setstopoffdutyTime(new Date());
                stopoffdutyTimer();
            }

            startsleep();

            final Handler handl = new Handler();
            handl.post(new Runnable() {
                @SuppressLint("NewApi")
                @Override
                public void run() {

                    long driveprog = sleepsec / 1000;
                    int peogressss = (int) driveprog;

                    int hours = peogressss / 3600;
                    int minutes = (peogressss % 3600) / 60;
                    int secs = peogressss % 60;

                    String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);

                    sleepTimingView.setText(time);
                    handl.postDelayed(this, 1000);
                }
            });
            //onDriveUpdate(false, false, false, true);
        }
    }

    private final void stopbreak() {
        breakhelper.setbreaktimerCounting(false);
    }

    private final void startbreak() {
        breakhelper.setbreaktimerCounting(true);

    }

    private final void startstopbreak() {
        Log.d("TAG", " =========breakstop========== " + breakhelper.stopbreakTime());
        if (breakhelper.stopbreakTime() != null) {
            breakhelper.setstartbreakTime(offrestarttime());
            breakhelper.setstopbreakTime(null);
        } else {
            breakhelper.setstartbreakTime(new Date());
        }


        startbreak();
/*
        final Handler handl = new Handler();
        handl.post(new Runnable() {
            @SuppressLint("NewApi")
            @Override
            public void run() {

                long driveprog = offdutysec / 1000;
                int peogressss = (int) driveprog;


                int hours = peogressss / 3600;
                int minutes = (peogressss % 3600) / 60;
                int secs = peogressss % 60;

                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);


                offdutyting.setText(time);
                handl.postDelayed(this, 1000);
            }
        });*/
        //onDriveUpdate(false, false, true, false);
    }

    private void startstopOFFDuty() {
        Log.d("TAG", " =========off duty click========== " + offDutyHelper.offdutytimerCounting());
        Log.d("TAG", " ========= break ========== " + breakhelper.breaktimerCounting());
        Log.d("TAG", " ========= shift ========== " + shifthelper.shifttimerCounting());
        if (offDutyHelper.offdutytimerCounting()) {
            offDutyHelper.setstartoffdutyTime(new Date());
            Toast.makeText(getApplicationContext(), "You are already in this mode", Toast.LENGTH_SHORT).show();
        } else {
            if (offDutyHelper.stopoffdutyTime() != null) {
                offDutyHelper.setstartoffdutyTime(offdutystarttime());
                offDutyHelper.setstopoffdutyTime(null);
            } else {
                offDutyHelper.setstartoffdutyTime(new Date());
            }
            if (heplper.isTimerCounting()) {
                heplper.setStopTime(new Date());
                stopsleep();
            }
            if (yardmoveshelper.ytimerCountinge()) {
                yardmoveshelper.ysetStopTimee(new Date());
                stopyard();
            }
        }

        if (driveHelper.drivetimerCounting()) {
            driveHelper.setstopdriveTime(new Date());
            stopDriveTimer();
        }
        if (breakhelper.breaktimerCounting()) {
            breakhelper.setstopbreakTime(new Date());

            stopbreak();
        }
        if (shifthelper.shifttimerCounting()) {
            shifthelper.setshiftstopTime(new Date());
            stopshift();

        }

        startoffdutyTimer();

        final Handler handl = new Handler();
        handl.post(new Runnable() {
            @SuppressLint("NewApi")
            @Override
            public void run() {

                long driveprog = offdutysec / 1000;
                int peogressss = (int) driveprog;


                int hours = peogressss / 3600;
                int minutes = (peogressss % 3600) / 60;
                int secs = peogressss % 60;

                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);


                offDutyTimingView.setText(time);
                handl.postDelayed(this, 1000);
            }
        });
        if (helperClass.getDASHBOARD()) {
            myFragment.changeDriveandBreakUI(false, false);
            myFragment.changeShift(false);
        }

    }

    private final void stopshift() {
        shifthelper.setshifttimerCounting(false);
    }

    private final void startshift() {
        shifthelper.setshifttimerCounting(true);
    }

    private final void startstopshift(boolean show) {

        if (shifthelper.shiftstopTime() != null) {
            shifthelper.setshiftstartTime(onrestarttime());
            shifthelper.setshiftstopTime(null);
        } else {
            shifthelper.setshiftstartTime(new Date());
        }

        if (offDutyHelper.offdutytimerCounting()) {
            offDutyHelper.setstopoffdutyTime(new Date());
            stopoffdutyTimer();
        }


        startshift();
        if (show) {

            final Handler handl = new Handler();
            handl.post(new Runnable() {
                @SuppressLint("NewApi")
                @Override
                public void run() {

                    long driveprog = ondutysec / 1000;
                    int peogressss = (int) driveprog;


                    int hours = peogressss / 3600;
                    int minutes = (peogressss % 3600) / 60;
                    int secs = peogressss % 60;

                    String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);


                    onDutyTimingView.setText(time);
                    handl.postDelayed(this, 1000);
                }
            });

        }
        if (helperClass.getDASHBOARD()) {
            myFragment.changeShift(true);
        }

    }

    private final void stopyard() {
        yardmoveshelper.ysetTimerCountinge(false);
    }

    private final void startyard() {
        yardmoveshelper.ysetTimerCountinge(true);
    }

    private final void startStopYard() {
        if (yardmoveshelper.ytimerCountinge()) {
//            yardmoveshelper.ysetStopTimee(new Date());
//            stopy();
            Toast.makeText(getApplicationContext(), "You are already in this mode", Toast.LENGTH_SHORT).show();
        } else {
            if (yardmoveshelper.ystopTimee() != null) {
                yardmoveshelper.ysetStartTimee(yrestarttime());
                yardmoveshelper.ysetStopTimee(null);
            } else {
                yardmoveshelper.ysetStartTimee(new Date());
            }
            if (driveHelper.drivetimerCounting()) {
                driveHelper.setstopdriveTime(new Date());
                stopDriveTimer();
            }
            if (heplper.isTimerCounting()) {
                heplper.setStopTime(new Date());
                stopsleep();
            }
            if (breakhelper.breaktimerCounting()) {
                breakhelper.setstopbreakTime(new Date());
                stopbreak();
            }
            if (shifthelper.shifttimerCounting()) {
                shifthelper.setshiftstopTime(new Date());
                stopshift();
            }
            if (personalHelper.ptimerCountinge()) {
                personalHelper.psetStopTimee(new Date());
                stopp();
            }
            if (offDutyHelper.offdutytimerCounting()) {
                offDutyHelper.setstopoffdutyTime(new Date());
                stopoffdutyTimer();
            }
            startyard();

            final Handler handl = new Handler();
            handl.post(new Runnable() {
                @SuppressLint("NewApi")
                @Override
                public void run() {

                    long driveprog = yarmovsec / 1000;
                    int peogressss = (int) driveprog;


                    int hours = peogressss / 3600;
                    int minutes = (peogressss % 3600) / 60;
                    int secs = peogressss % 60;

                    String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);

                    yardTimingView.setText(time);
                    handl.postDelayed(this, 1000);
                }
            });
        }
    }

    private final void stopp() {
        personalHelper.psetTimerCountinge(false);
    }

    private final void startp() {
        personalHelper.psetTimerCountinge(true);
    }

    private final void startStopPersonalTime() {
        if (personalHelper.ptimerCountinge()) {
            Toast.makeText(getApplicationContext(), "You are already in this mode", Toast.LENGTH_SHORT).show();
        } else {
            if (personalHelper.pstopTime() != null) {
                personalHelper.psetStartTimee(prestarttime());
                personalHelper.psetStopTimee(null);
            } else {
                personalHelper.psetStartTimee(new Date());
            }
            if (driveHelper.drivetimerCounting()) {
                driveHelper.setstopdriveTime(new Date());
                stopDriveTimer();
            }
            if (heplper.isTimerCounting()) {
                heplper.setStopTime(new Date());
                stopsleep();
            }
            if (breakhelper.breaktimerCounting()) {
                breakhelper.setstopbreakTime(new Date());
                stopbreak();
            }
            if (yardmoveshelper.ytimerCountinge()) {
                yardmoveshelper.ysetStopTimee(new Date());
                stopyard();
            }
            if (shifthelper.shifttimerCounting()) {
                shifthelper.setshiftstopTime(new Date());
                stopshift();
            }
            if (offDutyHelper.offdutytimerCounting()) {
                offDutyHelper.setstopoffdutyTime(new Date());
                stopoffdutyTimer();
            }
            startp();

            final Handler handl = new Handler();
            handl.post(new Runnable() {
                @SuppressLint("NewApi")
                @Override
                public void run() {

                    long driveprog = psec / 1000;
                    int peogressss = (int) driveprog;


                    int hours = peogressss / 3600;
                    int minutes = (peogressss % 3600) / 60;
                    int secs = peogressss % 60;

                    String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);

                    personalTimingView.setText(time);
                    handl.postDelayed(this, 1000);
                }
            });
        }
    }

    private final Date restarttime() {
        long diff = heplper.startTime().getTime() - heplper.stopTime().getTime();

        return new Date(System.currentTimeMillis() + diff);
    }

    private final String timeFromLong(long ms) {
        long seconds = (ms / 1000) % 60;
        long minutes = (ms / (1000 * 60) % 60);
        long hours = (ms / (1000 * 60 * 60) % 24);
        sleepsec = ms;
        return makeTimesleep(hours, minutes, seconds);
    }

    private final String makeTimesleep(long hours, long minutes, long seconds) {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

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

    private final Date offdutystarttime() {
        long diff = 0;
        if (offDutyHelper.startoffdutyTime() != null && offDutyHelper.stopoffdutyTime() != null) {
            diff = offDutyHelper.startoffdutyTime().getTime() - offDutyHelper.stopoffdutyTime().getTime();
        }

        return new Date(System.currentTimeMillis() + diff);
    }

    private final Date offrestarttime() {
        long diff = 0;
        if (breakhelper.startbreakTime() != null && breakhelper.stopbreakTime() != null) {
            diff = breakhelper.startbreakTime().getTime() - breakhelper.stopbreakTime().getTime();
        }

        return new Date(System.currentTimeMillis() + diff);
    }

    private final String offtimeFromLong(long ms) {
        long seconds = (ms / 1000) % 60;
        long minutes = (ms / (1000 * 60) % 60);
        long hours = (ms / (1000 * 60 * 60) % 24);

        offdutysec = ms;
        return makeTimeoff(hours, minutes, seconds);
    }

    private final String makeTimeoff(long hours, long minutes, long seconds) {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private final Date onrestarttime() {
        long diff = shifthelper.shiftstartTime().getTime() - shifthelper.shiftstopTime().getTime();
        return new Date(System.currentTimeMillis() + diff);
    }

    private final Date offdutyrestarttime() {
        long diff = offDutyHelper.startoffdutyTime().getTime() - offDutyHelper.stopoffdutyTime().getTime();
        return new Date(System.currentTimeMillis() + diff);
    }

    private final String ontimeFromLong(long ms) {
        long seconds = (ms / 1000) % 60;
        long minutes = (ms / (1000 * 60) % 60);
        long hours = (ms / (1000 * 60 * 60) % 24);

        ondutysec = ms;
        return makeTimeon(hours, minutes, seconds);
    }

    private final String makeTimeon(long hours, long minutes, long seconds) {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
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

    private boolean Fivemile(double Odometer) {
        boolean value = false;
        Double totalMiles = 0.0;
        Double currentmile = convertKmToMiles(Odometer);
        Log.d("TAG", " =========Miles========== " + currentmile);
        Log.d("TAG", " =========5ADDMile========== " + helperClass.getODOMETER());
        if (!helperClass.getODOMETER().isEmpty()) {
            totalMiles = Double.parseDouble(helperClass.getODOMETER());

            if (currentmile >= totalMiles) {
                helperClass.setIS_DRIVING(true);
                helperClass.setODOMETER("");
                value = true;
                location = locationnnv.getText().toString();

                String daa = datat.getText().toString();

                if (!daa.equals("")) {
                    String[] units = daa.split(",");
                    if (units.length > 10) {
                        if (!units[4].isEmpty()) getOdometer = Double.valueOf(units[4]);
                        if (!units[6].isEmpty()) engineHours = Double.valueOf(units[6]);
                    }

                }
                float y = 2f;
                status = "Drive";
                orign = "Auto";

                insertData(getDateTime(), y, getDateTime(), status, location, getOdometer, engineHours, orign, "logs");

                startStopDriveAction();
//testing here


            } else {
                value = false;
            }
        }
        return value;
    }

    public void calculateTimeAndDistance(double speedkph) {
        Log.d("TAG", " =========speedkph========== " + speedkph);

        if (speedkph >= 8.04672) {
            helperClass.setIS_DRIVING(true);
            helperClass.setODOMETER("");
            location = locationnnv.getText().toString();

            String daa = datat.getText().toString();

            if (!daa.equals("")) {
                String[] units = daa.split(",");
                if (units.length > 10) {
                    if (!units[4].isEmpty()) getOdometer = Double.valueOf(units[4]);
                    if (!units[6].isEmpty()) engineHours = Double.valueOf(units[6]);
                }

            }
            float y = 2f;
            status = "Drive";
            orign = "Auto";

            insertData(getDateTime(), y, getDateTime(), status, location, getOdometer, engineHours, orign, "logs");

            signalStatusView.setText("D");
            fullStatusView.setText("Drive");
            driveTimingView.setVisibility(View.VISIBLE);
            onDutyTimingView.setVisibility(View.GONE);
            sleepTimingView.setVisibility(View.GONE);
            offDutyTimingView.setVisibility(View.GONE);
            yardTimingView.setVisibility(View.GONE);
            personalTimingView.setVisibility(View.GONE);


            startStopDriveAction();

//testing here
        } else {
            Log.d("TAG", " =========NOT DRIVEING========== ");
        }
    }

    private void checkDrivingCondition(double currentSpeed) {
        Log.d("FIVEMILE", " =========EldDataRecordSPEED========== " + currentSpeed);


        // Check if the vehicle is driving
        if (currentSpeed > 0) {
            helperClass.setIS_DRIVING(true);

            helperClass.setSPEED_START_TIME(Long.valueOf(0));

        } else if (currentSpeed == STOPPED_SPEED_THRESHOLD) {
            helperClass.setIS_DRIVING(false);
            Log.d("TAG", " =========NOT DRIVE CREATE LOG========== " + helperClass.getSPEED_START_TIME());
            Log.d("TAG", " ========= savestatus ========== " + helperClass.getSaveStatus());
            Log.d("TAG", " ========= status ========== " + status);
            if (helperClass.getSPEED_START_TIME() == 0) {
                helperClass.setSPEED_START_TIME(System.currentTimeMillis());
                if (status.equals("Drive") && !status.equalsIgnoreCase(helperClass.getSaveStatus())) {

                    //CREATE second log of Engine state
                    String daa = datat.getText().toString();
                    location = locationnnv.getText().toString();

                    String[] units = daa.split(",");
                    if (units.length > 7) {
                        float y = 4f;
                        orign = "Auto";
                        if (!units[4].isEmpty()) getOdometer = Double.valueOf(units[4]);
                        if (!units[6].isEmpty()) engineHours = Double.valueOf(units[6]);

                        helperClass.setSaveStatus(status);
                        helperClass.setStatusChange(true);

                        insertData(getDateTime(), y, getDateTime(), status, location, getOdometer, engineHours, orign, "logs");
//STOP  E_OFF START OFFD

                        signalStatusView.setText("OFF");
                        fullStatusView.setText("Off duty");
                        driveTimingView.setVisibility(View.GONE);
                        onDutyTimingView.setVisibility(View.GONE);
                        sleepTimingView.setVisibility(View.GONE);
                        offDutyTimingView.setVisibility(View.VISIBLE);
                        yardTimingView.setVisibility(View.GONE);
                        personalTimingView.setVisibility(View.GONE);


                    }

                }

            }
            if (System.currentTimeMillis() - helperClass.getSPEED_START_TIME() >= FIVE_MINUTES_IN_MS) {
                Log.d("TAG", " =========isNotificationScheduled========== " + isNotificationScheduled);
                Log.d("TAG", " =========STATUS========== " + helperClass.getSaveStatus().equalsIgnoreCase("Drive"));
                // Schedule a notification and log creation after 5 minutes
                if (helperClass.getIS_DRIVING()) {
                    scheduleNotification();
                    //isNotificationScheduled = true;
                }
            }
        } else {
            helperClass.setSPEED_START_TIME(Long.valueOf(0));
        }

        Log.d("TAG", " =========ISDRIVE========== " + helperClass.getIS_DRIVING());
        Log.d("TAG", " =========LASTISDRIVING========== " + helperClass.getIS_DRIVING());
        Log.d("TAG", " =========STATUSCCC========== " + helperClass.getStatusChange());
        Log.d("TAG", " =========SAVEstatus========== " + helperClass.getSaveStatus());
        Log.d("TAG", " =========status========== " + status);
        // Change the status based on driving condition
        if (helperClass.getIS_DRIVING()) {
            if (!status.equalsIgnoreCase(helperClass.getSaveStatus())) {
                //CREATE second log of Engine state
                String daa = datat.getText().toString();
                location = locationnnv.getText().toString();

                String[] units = daa.split(",");
                if (units.length > 6) {
                    orign = "Auto";
                    if (!units[4].isEmpty()) getOdometer = Double.valueOf(units[4]);
                    if (!units[6].isEmpty()) engineHours = Double.valueOf(units[6]);
                    helperClass.setSaveStatus(status);
                    helperClass.setStatusChange(true);
                    float y = 0f;
                    if (status.equals("E_OFF")) {
                        // E_OFF START MODE OFFD and STOP other mode
                        y = 4f;
                        signalStatusView.setText("OFF");
                        fullStatusView.setText("Off duty");
                        driveTimingView.setVisibility(View.GONE);
                        onDutyTimingView.setVisibility(View.GONE);
                        sleepTimingView.setVisibility(View.GONE);
                        offDutyTimingView.setVisibility(View.VISIBLE);
                        yardTimingView.setVisibility(View.GONE);
                        personalTimingView.setVisibility(View.GONE);


                    } else if (status.equals("E_ON")) {
                        // E_ON START MODE OND and stop other mode
                        y = 1f;
                        signalStatusView.setText("ON");
                        fullStatusView.setText("on duty");
                        driveTimingView.setVisibility(View.GONE);
                        onDutyTimingView.setVisibility(View.VISIBLE);
                        sleepTimingView.setVisibility(View.GONE);
                        offDutyTimingView.setVisibility(View.GONE);
                        yardTimingView.setVisibility(View.GONE);
                        personalTimingView.setVisibility(View.GONE);
                        startstopshift(true);

                    }

                    insertData(getDateTime(), y, getDateTime(), status, location, getOdometer, engineHours, orign, "logs");


                }

            } else {
                Log.d("TAG", " =========NO CHANGE IN STATUS========== " + status);

                String daa = datat.getText().toString();
                location = locationnnv.getText().toString();
                if (!daa.equals("")) {
                    String[] units = daa.split(",");
                    if (units.length > 6) {
                        try {

                            if (status.equals("E_ON") && !helperClass.getIS_DRIVING()) {

                                calculateTimeAndDistance(currentSpeed);
                                //Fivemile(currentSpeed);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }

            }


        } else {
            if (!status.equalsIgnoreCase(helperClass.getSaveStatus())) {

                //CREATE second log of Engine state
                String daa = datat.getText().toString();
                location = locationnnv.getText().toString();

                String[] units = daa.split(",");
                if (units.length > 12) {
                    float y = 0f;
                    orign = "Auto";
                    if (!units[4].isEmpty()) getOdometer = Double.valueOf(units[4]);
                    if (!units[6].isEmpty()) engineHours = Double.valueOf(units[6]);
                    helperClass.setSaveStatus(status);
                    helperClass.setStatusChange(true);

                    if (status.equals("E_OFF")) {
                        // E_OFF START MODE OFFD and STOP other mode
                        y = 4f;
                        signalStatusView.setText("OFF");
                        fullStatusView.setText("Off duty");
                        driveTimingView.setVisibility(View.GONE);
                        onDutyTimingView.setVisibility(View.GONE);
                        sleepTimingView.setVisibility(View.GONE);
                        offDutyTimingView.setVisibility(View.VISIBLE);
                        yardTimingView.setVisibility(View.GONE);
                        personalTimingView.setVisibility(View.GONE);


                    } else if (status.equals("E_ON")) {
                        // E_ON START MODE OND and stop other mode
                        y = 1f;
                        signalStatusView.setText("ON");
                        fullStatusView.setText("on duty");
                        driveTimingView.setVisibility(View.GONE);
                        onDutyTimingView.setVisibility(View.VISIBLE);
                        sleepTimingView.setVisibility(View.GONE);
                        offDutyTimingView.setVisibility(View.GONE);
                        yardTimingView.setVisibility(View.GONE);
                        personalTimingView.setVisibility(View.GONE);
                        startstopshift(true);

                    } else if (status.equals("Drive")) {
                        y = 2f;
                        signalStatusView.setText("D");
                        fullStatusView.setText("Drive");
                        driveTimingView.setVisibility(View.VISIBLE);
                        onDutyTimingView.setVisibility(View.GONE);
                        sleepTimingView.setVisibility(View.GONE);
                        offDutyTimingView.setVisibility(View.GONE);
                        yardTimingView.setVisibility(View.GONE);
                        personalTimingView.setVisibility(View.GONE);


                        startStopDriveAction();
//testing here

                    }
                    insertData(getDateTime(), y, getDateTime(), status, location, getOdometer, engineHours, orign, "logs");
                }

            }
        }


    }

    private void scheduleNotification() {
        // Check if the app is in the foreground or background
        boolean isAppInForeground = isAppInForeground();
        Log.d("TAG", "======isAppInForeground========" + isAppInForeground);
        // If the app is in the foreground, show an alert dialog
        if (isAppInForeground) {

            dialog_StillDriving();

        } else {
            // If the app is in the background or not running, show a notification

            Data inputData = new Data.Builder().putBoolean("condition", true).build();

            OneTimeWorkRequest notificationWork = new OneTimeWorkRequest.Builder(FiveMinuteN.class).setInitialDelay(5, TimeUnit.MINUTES).setInputData(inputData).build();

            workManager.enqueue(notificationWork);
        }
    }

    public void dialog_StillDriving() {
        driverWorkStatusDialog.setContentView(R.layout.change_status_notification);
        driverWorkStatusDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        driverWorkStatusDialog.setCancelable(true);
        driverWorkStatusDialog.show();
        CardView no = driverWorkStatusDialog.findViewById(R.id.no);
        CardView drive = driverWorkStatusDialog.findViewById(R.id.drive);

        no.setOnClickListener(v -> {
            driverWorkStatusDialog.dismiss();
            confirmation_logout.setContentView(R.layout.edit_dialog);
            confirmation_logout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            CardView yes = confirmation_logout.findViewById(R.id.update);
            CardView cancel = confirmation_logout.findViewById(R.id.cancel);
            TextView text = confirmation_logout.findViewById(R.id.dialog_title);
            EditText edittextedit = confirmation_logout.findViewById(R.id.edittextedit);
            text.setText("On duty reason");

            yes.setOnClickListener(v12 -> {
                String result = edittextedit.getText().toString();
                if (result.equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter something", Toast.LENGTH_SHORT).show();
                } else {
                    signalStatusView.setText("ON");
                    fullStatusView.setText("on duty");
                    driveTimingView.setVisibility(View.GONE);
                    onDutyTimingView.setVisibility(View.VISIBLE);
                    sleepTimingView.setVisibility(View.GONE);
                    offDutyTimingView.setVisibility(View.GONE);
                    yardTimingView.setVisibility(View.GONE);
                    personalTimingView.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Your reason - " + result, Toast.LENGTH_SHORT).show();
                    confirmation_logout.dismiss();

                    location = locationnnv.getText().toString();
                    String daa = datat.getText().toString();

                    if (!daa.equals("")) {
                        String[] units = daa.split(",");
                        if (units.length > 10) {
                            if (!units[4].isEmpty())
                                getOdometer = Double.valueOf(units[4]);
                            if (!units[6].isEmpty())
                                engineHours = Double.valueOf(units[6]);

                        }
                    }


                    float y = 1f;
                    status = "OND";
                    orign = "Manual";
                    helperClass.setIS_DRIVING(false);
                    insertData(getDateTime(), y, getDateTime(), status, location, getOdometer, engineHours, orign, "logs");
                    startstopshift(true);

                }
            });
            cancel.setOnClickListener(v1 -> {
                confirmation_logout.dismiss();
                driverWorkStatusDialog.show();
            });
            confirmation_logout.setCancelable(false);
            confirmation_logout.show();
        });

        drive.setOnClickListener(v -> driverWorkStatusDialog.dismiss());

    }

    private boolean isAppInForeground() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(1);

        if (!runningTasks.isEmpty()) {
            ComponentName topActivity = runningTasks.get(0).topActivity;
            // App is in the foreground
            return topActivity.getPackageName().equals(getPackageName());
        }

        // App is in the background or closed
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void createFirstLog() {
        if (helperClass.getFirstLogin()) {
            String rawData = datat.getText().toString();
            location = locationnnv.getText().toString();

            // Extract values from the 'rawData' string and update 'getOdometer' and 'engineHours'.
            updateOdometerAndEngineHours(rawData);

            // Initialize variables
            float initialY = 4f;
            String logStatus = "OFFD";
            String originType = "Auto";

            // Insert data and set 'FirstLogin' to false
            insertLogEntry(initialY, logStatus, location, originType);

            // Update UI elements
            updateUIState();

            // Start the off-duty timer
            startOffDutyTimer();
        }
    }

    private void updateOdometerAndEngineHours(String rawData) {
        if (!rawData.isEmpty()) {
            String[] dataUnits = rawData.split(",");
            if (dataUnits.length > 11) {
                if (!dataUnits[4].isEmpty()) getOdometer = Double.valueOf(dataUnits[4]);
                if (!dataUnits[6].isEmpty()) engineHours = Double.valueOf(dataUnits[6]);
            }
        }
    }

    private void insertLogEntry(float initialY, String logStatus, String logLocation, String originType) {
        insertLogData(getCurrentDateTime(), initialY, logStatus, logLocation, getOdometer, engineHours, originType, "logs");
        helperClass.setFirstLogin(false);
    }

    private void updateUIState() {
        signalStatusView.setText("OFF");
        fullStatusView.setText("Off duty");
        driveTimingView.setVisibility(View.GONE);
        onDutyTimingView.setVisibility(View.GONE);
        sleepTimingView.setVisibility(View.GONE);
        offDutyTimingView.setVisibility(View.VISIBLE);
        yardTimingView.setVisibility(View.GONE);
        personalTimingView.setVisibility(View.GONE);

        // Handle other timer-related logic
        handleTimers();
    }

    private void startOffDutyTimer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @SuppressLint("NewApi")
            @Override
            public void run() {
                long offDutyProgress = offdutysec / 1000;
                int progressInSeconds = (int) offDutyProgress;

                int hours = progressInSeconds / 3600;
                int minutes = (progressInSeconds % 3600) / 60;
                int seconds = progressInSeconds % 60;

                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds);

                offDutyTimingView.setText(time);
                handler.postDelayed(this, 1000);
            }
        });
    }

    private void handleTimers() {
        if (offDutyHelper.offdutytimerCounting()) {
            offDutyHelper.setstartoffdutyTime(new Date());
            Toast.makeText(getApplicationContext(), "You are already in this mode", Toast.LENGTH_SHORT).show();
        } else {
            // Handle off-duty logic
        }

        // Handle other timers here
    }

    private void insertLogData(Date dateTime, float y, String status, String location, Double odometer, Double hours, String origin, String logType) {
        // Implement the data insertion logic here.
    }

    private Date getCurrentDateTime() {
        // Implement the logic to get the current date and time.
        return new Date();
    }

    public void createINTLog() {


        String daa = datat.getText().toString();
        location = locationnnv.getText().toString();

        if (!daa.equals("")) {
            String[] units = daa.split(",");
            Log.d("TAG", " =========OFFD========== " + units[4]);
            if (units.length > 11) {
                if (!units[4].isEmpty()) getOdometer = Double.valueOf(units[4]);
                if (!units[6].isEmpty()) engineHours = Double.valueOf(units[6]);
            }
        }

        float y = this.y;
        orign = "Auto";
        insertData(getDateTime(), y, getDateTime(), "INT", location, getOdometer, engineHours, orign, "logs");

    }

    public void ResetDataAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, ResetDataReceiver.class);
        intent.setAction("reset_progress_data");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 0);

        // If the alarm time has already passed today, schedule it for tomorrow
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.add(Calendar.DATE, 1);
        }

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }

    private void setAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ResetDataReceiver.class);
        intent.setAction("reset_data_action");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 0);

        // If the alarm time has already passed today, schedule it for tomorrow
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.add(Calendar.DATE, 1);
        }

        // Set the alarm to repeat every day
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private final class TimeTask extends TimerTask {
        public TimeTask() {
        }

        public void run() {
            if (heplper.isTimerCounting()) {
                long datt = (new Date()).getTime();
                long time = datt - heplper.startTime().getTime();
                timeFromLong(time);

            }
        }
    }

    private final class TimeDrive extends TimerTask {
        public TimeDrive() {
        }

        public void run() {
            if (driveHelper.drivetimerCounting()) {
                long datt = (new Date()).getTime();
                long time;
                if (driveHelper.startdriveTime() != null) {
                    time = datt - driveHelper.startdriveTime().getTime();
                    timeStringFromLong(time);
                }


            }
        }
    }

    private final class TimeTaskoffduty extends TimerTask {
        public TimeTaskoffduty() {
        }

        public void run() {
            if (offDutyHelper.offdutytimerCounting()) {
                long datt = (new Date()).getTime();
                Log.i("TAG", "======START OFF DUTY========" + offDutyHelper.startoffdutyTime());
                long time;
                if (offDutyHelper.startoffdutyTime() != null) {
                    time = datt - offDutyHelper.startoffdutyTime().getTime();
                    offtimeFromLong(time);
                }


            }
        }
    }

    private final class TimeShift extends TimerTask {
        public TimeShift() {
        }

        public void run() {
            if (shifthelper.shifttimerCounting()) {
                long datt = (new Date()).getTime();
                long time;
                if (shifthelper.shiftstartTime() != null) {
                    time = datt - shifthelper.shiftstartTime().getTime();
                    ontimeFromLong(time);
                }

            }
        }
    }

    private final class TimeTasky extends TimerTask {
        public TimeTasky() {
        }

        public void run() {
            if (yardmoveshelper.ytimerCountinge()) {
                long datt = (new Date()).getTime();
                long time = datt - yardmoveshelper.ystartTimee().getTime();
                ytimeFromLong(time);
            }
        }
    }

    private final class TimeTaskp extends TimerTask {
        public TimeTaskp() {

        }

        public void run() {
            if (personalHelper.ptimerCountinge()) {
                long datt = (new Date()).getTime();
                long time = datt - personalHelper.pstartTimee().getTime();
                ptimeFromLong(time);

            }
        }

    }

    private void callAddAttendanceRecord(String status, String attendanceType) {
        AddAttendanceRecordRequestModel requestBody = new AddAttendanceRecordRequestModel();
        requestBody.setStatus(status);
        requestBody.setVin(helperClass.getDriverProfile().getData().get(0).getVinNo());
        requestBody.setSpeed(String.valueOf(lastSpeed));
        requestBody.setOdometer(String.valueOf(getOdometer));
        requestBody.setEngineHours(String.valueOf(engineHours));
        requestBody.setLatitude(String.valueOf(latitude));
        requestBody.setLongitude(String.valueOf(longitude));
        requestBody.setLocation(String.valueOf(location));
        requestBody.setAttendenceType(attendanceType);
        requestBody.setUserId(helperClass.getID());
        requestBody.setRecordDate(getDateTime());
        requestBody.setCoDriver("");
        requestBody.setShippingAddress("");
        requestBody.setTripNo("");

        if (helperClass.getNetworkInfo()) {
            Call<ResponseBody> addAttendanceRecordCall = apiService.addAttendanceRecord(requestBody);
            addAttendanceRecordCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        hideLoader();
                        if (response.isSuccessful()) {
                            ResponseBody responseBody = response.body();
                            if (responseBody != null) {
                                try {
                                    String addAttendanceJson = responseBody.string();
                                    Gson gson = new Gson();
                                    AddAttendanceRecordResponseModel addAtendanceResponseModel = gson.fromJson(addAttendanceJson, AddAttendanceRecordResponseModel.class);
                                    String addAtendanceResponseString = gson.toJson(addAtendanceResponseModel);
                                    helperClass.setADD_ATTENDANCE_ID(""+addAtendanceResponseModel.getData());

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
                    Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            showNoInternetConnectionError();
        }
    }


}