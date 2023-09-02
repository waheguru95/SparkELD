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
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

import com.example.eld.R;
import com.example.eld.alert.FiveMinuteN;
import com.example.eld.alert.ResetDataReceiver;
import com.example.eld.custumclass.Helperclass;
import com.example.eld.custumclass.TimestampConverter;
import com.example.eld.fragments.Certify_fragment;
import com.example.eld.fragments.Deshboard_fragment;
import com.example.eld.fragments.Logs_fragment;
import com.example.eld.fragments.Reports_fragment;
import com.example.eld.utils.Breakhelper;
import com.example.eld.utils.DriveHelper;
import com.example.eld.utils.Heplper;
import com.example.eld.utils.OffDutyHelper;
import com.example.eld.utils.PersonalHelper;
import com.example.eld.utils.Shifthelper;
import com.example.eld.utils.WeekHelper;
import com.example.eld.utils.Yardmoveshelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
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

public class Deshboard_screen extends AppCompatActivity {

    public void onButtonClicked() {

        // myFragment.changeUI();
    }

    Deshboard_fragment myFragment;

    BluetoothAdapter bAdapter = null;
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
    public DriveHelper driveHelper;
    public OffDutyHelper offDutyHelper;
    public WeekHelper weekHelper;
    public Heplper heplper;
    public Breakhelper breakhelper;
    public Shifthelper shifthelper;
    public Yardmoveshelper yardmoveshelper;
    public PersonalHelper personalHelper;
    BottomNavigationView bottomNavigationView;
    ImageView side_menu, notification;
    DrawerLayout drawerLayout;
    CardView changestatus;
    CardView onoffbluetooth;
    Dialog confirmation_logout, status_dilog;
    String locationn;
    FirebaseFirestore firebaseFirestore;
    TextView locationnnv;
    Double lattitube = 0.0;
    Double logitude = 0.0;
    Double getodometer = 0.0;
    Double enginhour = 0.0;
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
    String graph = "logs";//NA
    TextView driveingtiming, ondutytiming, sleeptiming, offdutyting, yardtiming, personaltiming, conectionstatus, singalstatus, fullstatus;
    ImageView onblue, offblue;
    private PeriodicWorkRequest periodicWorkRequest;
    private WorkManager workManager;
    private long backpresstime;
    private EldManager mEldManager;
    private final Set<EldBroadcastTypes> subscribedRecords = EnumSet.of(EldBroadcastTypes.ELD_BUFFER_RECORD, EldBroadcastTypes.ELD_CACHED_RECORD, EldBroadcastTypes.ELD_FUEL_RECORD, EldBroadcastTypes.ELD_DATA_RECORD, EldBroadcastTypes.ELD_DRIVER_BEHAVIOR_RECORD, EldBroadcastTypes.ELD_EMISSIONS_PARAMETERS_RECORD, EldBroadcastTypes.ELD_ENGINE_PARAMETERS_RECORD, EldBroadcastTypes.ELD_TRANSMISSION_PARAMETERS_RECORD);
    private Handler mHandler;
    private Runnable mRunnable;
    private double lastSpeed = 0.0;
    private ActivityResultLauncher<Intent> activityLauncher;


    private final EldBleConnectionStateChangeCallback bleConnectionStateChangeCallback = new EldBleConnectionStateChangeCallback() {
        @Override
        public void onConnectionStateChange(final int newState) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
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
                }
            });
        }
    };

    private boolean isNotificationScheduled = false;
    private final EldBleDataCallback bleDataCallback = new EldBleDataCallback() {
        @Override
        public void OnDataRecord(final EldBroadcast dataRec, final EldBroadcastTypes RecordType) {
            runOnUiThread(new Runnable() {
                @SuppressLint("SetTextI18n")
                @Override
                public void run() {

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


                                    Helperclass.setStatusChange(true, Deshboard_screen.this);
                                    if (Helperclass.getStartOdometer(Deshboard_screen.this).isEmpty() && !units[4].toString().isEmpty()) {
                                        //double odo = convertKmToMiles(Double.parseDouble(units[4])) + 5;
                                        double speed = convertKmToMiles(Double.parseDouble(units[3])) + 5;
                                        Helperclass.setStartOdometer(String.valueOf(speed), Deshboard_screen.this);
                                    }
                                } else if (units[0].toString().equalsIgnoreCase("0")) {
                                    Log.d("TAG", " =========E_OFF========== " + units[0].toString());
                                    status = "E_OFF";

                                    Helperclass.setlastIsDriving(false, Deshboard_screen.this);
                                    if (Helperclass.getStartOdometer(Deshboard_screen.this).isEmpty() && !units[4].toString().isEmpty()) {
                                        //double odo = convertKmToMiles(Double.parseDouble(units[4])) + 5;
                                        double speed = convertKmToMiles(Double.parseDouble(units[3])) + 5;
                                        Helperclass.setStartOdometer(String.valueOf(speed), Deshboard_screen.this);
                                    }
                                }
                                if (!units[6].toString().isEmpty())
                                    enginhour = Double.parseDouble(units[6].toString());
                                if (!units[11].toString().isEmpty() && units[11].length() > 0)
                                    lattitube = Double.parseDouble(units[11].toString());
                                if (!units[12].toString().isEmpty() && units[11].length() > 0)
                                    logitude = Double.parseDouble(units[12].toString());
                                if (lattitube != 0.0 && logitude != 0.0) {
                                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                                    try {
                                        List<Address> addresses = geocoder.getFromLocation(lattitube, logitude, 1);
                                        Address obj = addresses.get(0);
                                        locationn = obj.getLocality() + "," + obj.getAdminArea();
                                        locationnnv.setText(locationn);

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                checkDrivingCondition(Double.parseDouble(units[3]));

                            } else {
                                locationn = "Location Not Available";
                            }
                        }
                    }

                }
            });
        }
    };
    //    private final EldBleScanCallback bleScanCallback = new EldBleScanCallback() {
//        @Override
//        public void onScanResult(EldScanObject device) {
//            final String strDevice;
//            if (device != null) {
//                strDevice = device.getDeviceId();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        conectionstatus.setText("Connecting..");
//
//                    }
//                });
//                EldBleError res = mEldManager.ConnectToEld(bleDataCallback, subscribedRecords, bleConnectionStateChangeCallback);
//                if (res != EldBleError.SUCCESS) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            conectionstatus.setText("Failed");
//
//                        }
//                    });
//                }
//            } else {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        conectionstatus.setText("No Device");
//
//                    }
//                });
//            }
//        }
//
//        @Override
//        public void onScanResult(ArrayList deviceList) {
//            Log.d("BLETEST", "BleScanCallback multiple");
//            final String strDevice;
//            EldScanObject so;
//            if (deviceList != null) {
//                so = (EldScanObject) deviceList.get(0);
//                strDevice = so.getDeviceId();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        //conectionstatus.setText("Connecting..");
//                        conectionstatus.setText("FOUND ELD " + strDevice + ", CONNECTING");
//
//                    }
//                });
//                EldBleError res = mEldManager.ConnectToEld(bleDataCallback, subscribedRecords, bleConnectionStateChangeCallback, strDevice);
//                if (res != EldBleError.SUCCESS) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            conectionstatus.setText("Failed");
//
//                        }
//                    });
//                }
//
//            } else {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        //AGAIN COLLING HERE
//                        conectionstatus.setText("No Device");
//                        mEldManager.EnableBluetooth(REQUEST_BT_ENABLE);
//                    }
//                });
//            }
//        }
//    };
    private final Handler hourlyhandler = new Handler();
    private final Runnable logRunnable = new Runnable() {
        @Override
        public void run() {
            String daa = datat.getText().toString();
            locationn = locationnnv.getText().toString();
            if (daa.equals("")) {
                if (daa.equals("")) {

                } else {
                    String[] units = daa.split(",");
                    Log.d("TAG", " =========units[4]========== " + units[4]);
                    if (units.length > 10) {
                        if (!units[4].toString().isEmpty()) getodometer = Double.valueOf(units[4]);
                        if (!units[6].toString().isEmpty()) enginhour = Double.valueOf(units[6]);

                    }

                }
                //float y = y;// pervious y value will send
                status = "INT";
                orign = "Auto";
                inserdata(getDateTime(), y, getDateTime(), status, locationn, getodometer, enginhour, orign, "logs");

            }
            hourlyhandler.postDelayed(this, 15 * 60 * 1000); // schedule the next execution after 1 hour
        }
    };


    @NotNull
    public final DriveHelper getdriveHelper() {
        DriveHelper helper = this.driveHelper;
        if (helper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("driveHelper");
        }
        return helper;
    }

    @NotNull
    public final Heplper getHeplper() {
        Heplper helperr = this.heplper;
        if (helperr == null) {
            Intrinsics.throwUninitializedPropertyAccessException("heplper");
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
        driveingtiming = findViewById(R.id.driveingtiming);
        ondutytiming = findViewById(R.id.ondutytiming);
        sleeptiming = findViewById(R.id.sleeptiming);
        offdutyting = findViewById(R.id.offdutytiming);
        yardtiming = findViewById(R.id.yardtiming);
        personaltiming = findViewById(R.id.personaltiming);
        singalstatus = findViewById(R.id.singalstatus);
        fullstatus = findViewById(R.id.fullstatus);
        onblue = findViewById(R.id.onblue);
        offblue = findViewById(R.id.offblue);
        onoffbluetooth = findViewById(R.id.onoffbluetooth);
        conectionstatus = findViewById(R.id.conectionstatus);
        datat = findViewById(R.id.datat);

        firebaseFirestore = FirebaseFirestore.getInstance();

        driveHelper = new DriveHelper(getApplicationContext());
        offDutyHelper = new OffDutyHelper(getApplicationContext());
        heplper = new Heplper(getApplicationContext());
        breakhelper = new Breakhelper(getApplicationContext());
        shifthelper = new Shifthelper(getApplicationContext());
        yardmoveshelper = new Yardmoveshelper(getApplicationContext());
        personalHelper = new PersonalHelper(getApplicationContext());
        confirmation_logout = new Dialog(this);
        status_dilog = new Dialog(this);

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



     /*   Log.d("TAG", " =========STATUSCHANGE========== " + Helperclass.getStatusChange(this));
        if (Helperclass.getStatusChange(this)) {
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
                confirmation_logout.setContentView(R.layout.logout_dilog);
                confirmation_logout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                CardView yes = confirmation_logout.findViewById(R.id.yeslogout);
                CardView no = confirmation_logout.findViewById(R.id.no);
                TextView text = confirmation_logout.findViewById(R.id.logoputtext);
                text.setText("Do you want to Disconnect the device?");

                yes.setOnClickListener(v1 -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
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
                if (ContextCompat.checkSelfPermission(Deshboard_screen.this, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_DENIED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        ActivityCompat.requestPermissions(Deshboard_screen.this, new String[]{Manifest.permission.BLUETOOTH_SCAN}, 2);
                    }
                }
                if (ContextCompat.checkSelfPermission(Deshboard_screen.this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        ActivityCompat.requestPermissions(Deshboard_screen.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 2);
                    }
                }
                conectionstatus.setText("SCANNING...");
                ScanForEld();
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Deshboard_screen.this, Notification_screen.class));
            }
        });
        side_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        Helperclass.setDeshBoard(true, this);
        myFragment = new Deshboard_fragment();
        controlfunction(myFragment);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.myhome:
                        myFragment = new Deshboard_fragment();
                        Helperclass.setDeshBoard(true, Deshboard_screen.this);
                        controlfunction(myFragment);
                        break;
                    case R.id.logs:
                        Helperclass.setDeshBoard(false, Deshboard_screen.this);
                        controlfunction(new Logs_fragment());
                        break;
                    case R.id.certfy:
                        Helperclass.setDeshBoard(false, Deshboard_screen.this);
                        controlfunction(new Certify_fragment());
                        break;
                    case R.id.reports:
                        Helperclass.setDeshBoard(false, Deshboard_screen.this);
                        controlfunction(new Reports_fragment());
                        break;

                }
                return true;
            }
        });

    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deshboard_screen);
// Create a new instance of WorkManager
        workManager = WorkManager.getInstance();

//        ResetDataAlarm();

        initview();

        changestatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status_dilog.setContentView(R.layout.change_status_dilog);
                status_dilog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                status_dilog.setCancelable(true);

                CardView drive = status_dilog.findViewById(R.id.drive);
                CardView onduty = status_dilog.findViewById(R.id.onduty);
                CardView sleep = status_dilog.findViewById(R.id.sleep);
                CardView offduty = status_dilog.findViewById(R.id.offduty);
                CardView yard = status_dilog.findViewById(R.id.yard);
                CardView personaluse = status_dilog.findViewById(R.id.personaluse);

                drive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        status_dilog.dismiss();
                        singalstatus.setText("D");
                        fullstatus.setText("Drive");
                        driveingtiming.setVisibility(View.VISIBLE);
                        ondutytiming.setVisibility(View.GONE);
                        sleeptiming.setVisibility(View.GONE);
                        offdutyting.setVisibility(View.GONE);
                        yardtiming.setVisibility(View.GONE);
                        personaltiming.setVisibility(View.GONE);
                        locationn = locationnnv.getText().toString();

                        String daa = datat.getText().toString();

                        if (!daa.equals("")) {
                            String[] units = daa.split(",");
                            if (units.length > 10) {
                                if (!units[4].toString().isEmpty())
                                    getodometer = Double.valueOf(units[4]);
                                if (!units[6].toString().isEmpty())
                                    enginhour = Double.valueOf(units[6]);

                            }
                        }
                        float y = 2f;
                        status = "Drive";
                        orign = "Manual";

                        inserdata(getDateTime(), y, getDateTime(), status, locationn, getodometer, enginhour, orign, "logs");
                        startStopDriveAction();

                    }
                });
                offduty.setOnClickListener(v1 -> {
                    if (offDutyHelper.offdutytimerCounting()) {
                        status_dilog.dismiss();
                        Toast.makeText(getApplicationContext(), "You are already in this mode", Toast.LENGTH_SHORT).show();
                    } else {
                        status_dilog.dismiss();
                        confirmation_logout.setContentView(R.layout.edit_dialog);
                        confirmation_logout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        CardView yes = confirmation_logout.findViewById(R.id.yes);
                        CardView cancel = confirmation_logout.findViewById(R.id.cancel);
                        TextView text = confirmation_logout.findViewById(R.id.dialog_title);
                        EditText edittextedit = confirmation_logout.findViewById(R.id.edittextedit);
                        text.setText("Off duty reason");
                        yes.setOnClickListener(v11 -> {
                            String result = edittextedit.getText().toString();
                            if (result.equals("")) {
                                Toast.makeText(getApplicationContext(), "Enter something", Toast.LENGTH_SHORT).show();
                            } else {
                                singalstatus.setText("OFF");
                                fullstatus.setText("Off duty");
                                driveingtiming.setVisibility(View.GONE);
                                ondutytiming.setVisibility(View.GONE);
                                sleeptiming.setVisibility(View.GONE);
                                offdutyting.setVisibility(View.VISIBLE);
                                yardtiming.setVisibility(View.GONE);
                                personaltiming.setVisibility(View.GONE);

                                Toast.makeText(getApplicationContext(), "Your reason - " + result, Toast.LENGTH_SHORT).show();

                                confirmation_logout.dismiss();
                                locationn = locationnnv.getText().toString();
                                String daa = datat.getText().toString();

                                if (!daa.equals("")) {
                                    String[] units = daa.split(",");
                                    if (units.length > 10) {
                                        if (!units[4].toString().isEmpty())
                                            getodometer = Double.valueOf(units[4]);
                                        if (!units[6].toString().isEmpty())
                                            enginhour = Double.valueOf(units[6]);

                                    }
                                }

                                float y = 4f;
                                status = "OFFD";
                                orign = "Manual";

                                inserdata(getDateTime(), y, getDateTime(), status, locationn, getodometer, enginhour, orign, "logs");
                                startstopOFFDuty();

                            }
                        });
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v1) {
                                confirmation_logout.dismiss();
                                status_dilog.show();
                            }
                        });
                        confirmation_logout.setCancelable(false);
                        confirmation_logout.show();
                    }

                });
                onduty.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (shifthelper.shifttimerCounting()) {
                            status_dilog.dismiss();
                            Toast.makeText(getApplicationContext(), "You are already in this mode", Toast.LENGTH_SHORT).show();
                        } else {
                            status_dilog.dismiss();
                            confirmation_logout.setContentView(R.layout.edit_dialog);
                            confirmation_logout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            CardView yes = confirmation_logout.findViewById(R.id.yes);
                            CardView cancel = confirmation_logout.findViewById(R.id.cancel);
                            TextView text = confirmation_logout.findViewById(R.id.dialog_title);
                            EditText edittextedit = confirmation_logout.findViewById(R.id.edittextedit);
                            text.setText("On duty reason");

                            yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String result = edittextedit.getText().toString();
                                    if (result.equals("")) {
                                        Toast.makeText(getApplicationContext(), "Enter something", Toast.LENGTH_SHORT).show();
                                    } else {
                                        singalstatus.setText("ON");
                                        fullstatus.setText("on duty");
                                        driveingtiming.setVisibility(View.GONE);
                                        ondutytiming.setVisibility(View.VISIBLE);
                                        sleeptiming.setVisibility(View.GONE);
                                        offdutyting.setVisibility(View.GONE);
                                        yardtiming.setVisibility(View.GONE);
                                        personaltiming.setVisibility(View.GONE);
                                        Toast.makeText(getApplicationContext(), "Your reason - " + result, Toast.LENGTH_SHORT).show();
                                        confirmation_logout.dismiss();

                                        locationn = locationnnv.getText().toString();
                                        String daa = datat.getText().toString();

                                        if (!daa.equals("")) {
                                            String[] units = daa.split(",");
                                            if (units.length > 10) {
                                                if (!units[4].toString().isEmpty())
                                                    getodometer = Double.valueOf(units[4]);
                                                if (!units[6].toString().isEmpty())
                                                    enginhour = Double.valueOf(units[6]);

                                            }
                                        }

                                        float y = 1f;
                                        status = "OND";
                                        orign = "Manual";

                                        inserdata(getDateTime(), y, getDateTime(), status, locationn, getodometer, enginhour, orign, "logs");
                                        startstopshift(true);


                                    }
                                }
                            });
                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    confirmation_logout.dismiss();
                                    status_dilog.show();
                                }
                            });
                            confirmation_logout.setCancelable(false);
                            confirmation_logout.show();
                        }

                    }
                });
                sleep.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        status_dilog.dismiss();
                        singalstatus.setText("SB");
                        fullstatus.setText("Sleep berth");
                        driveingtiming.setVisibility(View.GONE);
                        ondutytiming.setVisibility(View.GONE);
                        sleeptiming.setVisibility(View.VISIBLE);
                        offdutyting.setVisibility(View.GONE);
                        yardtiming.setVisibility(View.GONE);
                        personaltiming.setVisibility(View.GONE);
                        float y = 3f;
                        String status = "Sleep";
                        locationn = locationnnv.getText().toString();
                        String daa = datat.getText().toString();

                        if (daa.equals("")) {

                        } else {
                            String[] units = daa.split(",");
                            if (units.length > 10) {
                                if (!units[4].toString().isEmpty())
                                    getodometer = Double.valueOf(units[4]);
                                if (!units[6].toString().isEmpty())
                                    enginhour = Double.valueOf(units[6]);

                            }
                        }
                        orign = "Manual";

                        inserdata(getDateTime(), y, getDateTime(), status, locationn, getodometer, enginhour, orign, "logs");
                        startstopsleep();

                    }
                });
                yard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        status_dilog.dismiss();

                        singalstatus.setText("YM");
                        fullstatus.setText("Yard move");
                        driveingtiming.setVisibility(View.GONE);
                        ondutytiming.setVisibility(View.GONE);
                        sleeptiming.setVisibility(View.GONE);
                        offdutyting.setVisibility(View.GONE);
                        yardtiming.setVisibility(View.VISIBLE);
                        personaltiming.setVisibility(View.GONE);
                        locationn = locationnnv.getText().toString();
                        String daa = datat.getText().toString();
                        float y = 2f;
                        status = "YM";
                        if (daa.equals("")) {

                        } else {
                            String[] units = daa.split(",");
                            if (units.length > 10) {
                                if (!units[4].toString().isEmpty())
                                    getodometer = Double.valueOf(units[4]);
                                if (!units[6].toString().isEmpty())
                                    enginhour = Double.valueOf(units[6]);

                            }
                        }
                        orign = "Manual";

                        inserdata(getDateTime(), y, getDateTime(), status, locationn, getodometer, enginhour, orign, "logs");
                        startstopy();

                        Helperclass.setlastIsDriving(false, Deshboard_screen.this);
                        Helperclass.setStartOdometer("", Deshboard_screen.this);

                    }
                });

                personaluse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        status_dilog.dismiss();

                        singalstatus.setText("PU");
                        fullstatus.setText("Personal use");
                        driveingtiming.setVisibility(View.GONE);
                        ondutytiming.setVisibility(View.GONE);
                        sleeptiming.setVisibility(View.GONE);
                        offdutyting.setVisibility(View.GONE);
                        yardtiming.setVisibility(View.GONE);
                        personaltiming.setVisibility(View.VISIBLE);
                        locationn = locationnnv.getText().toString();
                        String daa = datat.getText().toString();

                        float y = 2f;
                        status = "PU";
                        if (daa.equals("")) {

                        } else {
                            String[] units = daa.split(",");
                            if (units.length > 10) {
                                if (!units[4].toString().isEmpty())
                                    getodometer = Double.valueOf(units[4]);
                                if (!units[6].toString().isEmpty())
                                    enginhour = Double.valueOf(units[6]);

                            }
                        }

                        orign = "Manual";

                        inserdata(getDateTime(), y, getDateTime(), status, locationn, getodometer, enginhour, orign, "logs");

                        startstopp();
                        Helperclass.setlastIsDriving(false, Deshboard_screen.this);
                        Helperclass.setStartOdometer("", Deshboard_screen.this);
                    }
                });
                status_dilog.show();
            }
        });


        DriveHelper driveHelper = this.driveHelper;
        OffDutyHelper offDutyHelper = this.offDutyHelper;
        Heplper heplper = this.heplper;
        Shifthelper shifthelper = this.shifthelper;
        Breakhelper breakhelper = this.breakhelper;
        Yardmoveshelper yardmoveshelper = this.yardmoveshelper;
        PersonalHelper personalHelper = this.personalHelper;


        Log.d("CLOCK", "=====drivesec=====" + drivesec);
        Log.d("CLOCK", "=====driveHelper=====" + driveHelper.drivetimerCounting());
        Log.d("CLOCK", "=====offDutyHelper=====" + offDutyHelper.offdutytimerCounting());
        Log.d("CLOCK", "=====breakhelper=====" + breakhelper.breaktimerCounting());
        Log.d("CLOCK", "=====shifthelper=====" + shifthelper.shifttimerCounting());

        if (driveHelper.drivetimerCounting()) {
            //startDriveTimer();
            singalstatus.setText("D");
            fullstatus.setText("Drive");
            driveingtiming.setVisibility(View.VISIBLE);
            ondutytiming.setVisibility(View.GONE);
            sleeptiming.setVisibility(View.GONE);
            offdutyting.setVisibility(View.GONE);
            personaltiming.setVisibility(View.GONE);
            yardtiming.setVisibility(View.GONE);

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
                    driveingtiming.setText(time);

                    handl.postDelayed(this, 1000);
                }
            });

        } else {
            stopDriveTimer();
            if (driveHelper.startdriveTime() != null && driveHelper.stopdriveTime() != null) {
                long time = (new Date()).getTime() - this.calcRestartTime().getTime();
                timeStringFromLong(time);
            }
        }
        this.timedrive.scheduleAtFixedRate(new TimeDrive(), 0L, 500L);

        if (shifthelper.shifttimerCounting()) {
            startshift();
            if (!driveHelper.drivetimerCounting()) {
                singalstatus.setText("ON");
                fullstatus.setText("on duty");
                driveingtiming.setVisibility(View.GONE);
                ondutytiming.setVisibility(View.VISIBLE);
                sleeptiming.setVisibility(View.GONE);
                offdutyting.setVisibility(View.GONE);
                yardtiming.setVisibility(View.GONE);
                personaltiming.setVisibility(View.GONE);


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


                        ondutytiming.setText(time);
                        handl.postDelayed(this, 1000);
                    }
                });
            }


        } else {
            stopshift();
            if (shifthelper.shiftstartTime() != null && shifthelper.shiftstopTime() != null) {
                long time = (new Date()).getTime() - this.onrestarttime().getTime();
                ontimeFromLong(time);

            }
        }
        this.ontimer.scheduleAtFixedRate(new TimeShift(), 0L, 500L);

        if (offDutyHelper.offdutytimerCounting()) {
            startoffdutyTimer();

            singalstatus.setText("OFF");
            fullstatus.setText("Off duty");
            driveingtiming.setVisibility(View.GONE);
            ondutytiming.setVisibility(View.GONE);
            sleeptiming.setVisibility(View.GONE);
            offdutyting.setVisibility(View.VISIBLE);
            yardtiming.setVisibility(View.GONE);
            personaltiming.setVisibility(View.GONE);


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
            });


        } else {
            stopoffdutyTimer();
            if (offDutyHelper.startoffdutyTime() != null && offDutyHelper.stopoffdutyTime() != null) {
                long time = (new Date()).getTime() - this.offdutyrestarttime().getTime();
                offtimeFromLong(time);

            }
        }
        this.offtimer.scheduleAtFixedRate(new TimeTaskoffduty(), 0L, 500L);


        if (heplper.timerCountinge()) {

            //startsleep();
            singalstatus.setText("SB");
            fullstatus.setText("Sleep berth");
            driveingtiming.setVisibility(View.GONE);
            ondutytiming.setVisibility(View.GONE);
            sleeptiming.setVisibility(View.VISIBLE);
            offdutyting.setVisibility(View.GONE);
            yardtiming.setVisibility(View.GONE);
            personaltiming.setVisibility(View.GONE);
            startstopsleep();
        } else {
            stopsleep();
            if (heplper.startTimee() != null && heplper.stopTimee() != null) {
                long time = (new Date()).getTime() - this.restarttime().getTime();
                timeFromLong(time);

            }
        }
        this.sleeptimer.scheduleAtFixedRate(new TimeTask(), 0L, 500L);


        if (yardmoveshelper.ytimerCountinge()) {

            starty();
            singalstatus.setText("YM");
            fullstatus.setText("Yard move");
            driveingtiming.setVisibility(View.GONE);
            ondutytiming.setVisibility(View.GONE);
            sleeptiming.setVisibility(View.GONE);
            offdutyting.setVisibility(View.GONE);
            yardtiming.setVisibility(View.VISIBLE);
            personaltiming.setVisibility(View.GONE);
        } else {

            stopy();
            if (yardmoveshelper.ystartTimee() != null && yardmoveshelper.ystopTimee() != null) {
                long time = (new Date()).getTime() - this.yrestarttime().getTime();
                ytimeFromLong(time);
            }
        }
        this.ytimer.scheduleAtFixedRate(new TimeTasky(), 0L, 500L);

        if (personalHelper.ptimerCountinge()) {
            startp();
            singalstatus.setText("PU");
            fullstatus.setText("Personal use");
            driveingtiming.setVisibility(View.GONE);
            ondutytiming.setVisibility(View.GONE);
            sleeptiming.setVisibility(View.GONE);
            offdutyting.setVisibility(View.GONE);
            yardtiming.setVisibility(View.GONE);
            personaltiming.setVisibility(View.VISIBLE);
        } else {
            stopp();
            if (personalHelper.pstartTimee() != null && personalHelper.pstopTimee() != null) {
                long time = (new Date()).getTime() - this.prestarttime().getTime();
                ptimeFromLong(time);
            }
        }
        this.ptimer.scheduleAtFixedRate(new TimeTaskp(), 0L, 500L);

        locationnnv.setText("Location Not Available");
        VIN_no.setText("VIN: No Device");
        createFirstLog();

    }

    private void inserdata(String x_axis, float y_axis, String dateTime, String status, String locationn, Double getodometer, Double enginhour, String orign, String graph) {
        Double odoo = (getodometer * 1000) / 1609.344;
        DecimalFormat df = new DecimalFormat("#");
        y = y_axis;
        Log.d("TAG", " =========x=========> " + x_axis);
        Log.d("TAG", " =========y_axis=========> " + y_axis);
        HashMap hashMap = new HashMap();
        hashMap.put("x", x_axis + "");
        hashMap.put("y", y_axis + "");
        hashMap.put("time", FieldValue.serverTimestamp());
        hashMap.put("status", status);
        hashMap.put("location", locationn);
        hashMap.put("odometer", df.format(odoo) + "");
        hashMap.put("eh", enginhour + "");
        hashMap.put("orign", orign + "");
        hashMap.put("graph", graph);
//        firebaseFirestore.collection(Helperclass.getid(getApplicationContext())).add(hashMap);
    }

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

    private final void startStopDriveAction() {

        if (driveHelper.drivetimerCounting()) {
            driveHelper.setstopdriveTime(new Date());
            //stopDriveTimer();
            Toast.makeText(getApplicationContext(), "You are already in this mode", Toast.LENGTH_SHORT).show();
        } else {

            if (driveHelper.stopdriveTime() != null) {
                driveHelper.setstartdriveTime(calcRestartTime());
                driveHelper.setstopdriveTime(null);
            } else {
                driveHelper.setstartdriveTime(new Date());
            }
            if (heplper.timerCountinge()) {
                heplper.setStopTimee(new Date());
                stopsleep();
            }
            if (yardmoveshelper.ytimerCountinge()) {
                yardmoveshelper.ysetStopTimee(new Date());
                stopy();
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
            startDriveTimer();
            startbreak();

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
                    driveingtiming.setText(time);

                    handl.postDelayed(this, 1000);
                }
            });
            if (Helperclass.getDeshBoard(this)) {
                myFragment.changeDriveandBreakUI(true, true);

            }

        }
    }

    private final void stopsleep() {
        heplper.setTimerCountinge(false);
    }

    private final void startsleep() {
        heplper.setTimerCountinge(true);

    }

    private final void startstopsleep() {
        if (heplper.timerCountinge()) {
            Toast.makeText(getApplicationContext(), "You are already in this mode", Toast.LENGTH_SHORT).show();
        } else {

//            inserdataa(x,y);
            if (heplper.stopTimee() != null) {
                heplper.setStartTimee(restarttime());
                heplper.setStopTimee(null);
            } else {
                heplper.setStartTimee(new Date());
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
                stopy();
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

                    sleeptiming.setText(time);
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
            if (heplper.timerCountinge()) {
                heplper.setStopTimee(new Date());
                stopsleep();
            }
            if (yardmoveshelper.ytimerCountinge()) {
                yardmoveshelper.ysetStopTimee(new Date());
                stopy();
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


                offdutyting.setText(time);
                handl.postDelayed(this, 1000);
            }
        });
        if (Helperclass.getDeshBoard(this)) {
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


                    ondutytiming.setText(time);
                    handl.postDelayed(this, 1000);
                }
            });

        }
        if (Helperclass.getDeshBoard(this)) {
            myFragment.changeShift(true);
        }

    }

    private final void stopy() {
        yardmoveshelper.ysetTimerCountinge(false);
    }

    private final void starty() {
        yardmoveshelper.ysetTimerCountinge(true);
    }

    private final void startstopy() {
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
            if (heplper.timerCountinge()) {
                heplper.setStopTimee(new Date());
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
            starty();

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

                    yardtiming.setText(time);
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

    private final void startstopp() {
        if (personalHelper.ptimerCountinge()) {
            Toast.makeText(getApplicationContext(), "You are already in this mode", Toast.LENGTH_SHORT).show();
        } else {
            if (personalHelper.pstopTimee() != null) {
                personalHelper.psetStartTimee(prestarttime());
                personalHelper.psetStopTimee(null);
            } else {
                personalHelper.psetStartTimee(new Date());
            }
            if (driveHelper.drivetimerCounting()) {
                driveHelper.setstopdriveTime(new Date());
                stopDriveTimer();
            }
            if (heplper.timerCountinge()) {
                heplper.setStopTimee(new Date());
                stopsleep();
            }
            if (breakhelper.breaktimerCounting()) {
                breakhelper.setstopbreakTime(new Date());
                stopbreak();
            }
            if (yardmoveshelper.ytimerCountinge()) {
                yardmoveshelper.ysetStopTimee(new Date());
                stopy();
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

                    personaltiming.setText(time);
                    handl.postDelayed(this, 1000);
                }
            });
        }
    }

    private final Date restarttime() {
        long diff = heplper.startTimee().getTime() - heplper.stopTimee().getTime();

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
        long diff = personalHelper.pstartTimee().getTime() - personalHelper.pstopTimee().getTime();
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
        Log.d("TAG", " =========5ADDMile========== " + Helperclass.getStartOdometer(this));
        if (!Helperclass.getStartOdometer(this).isEmpty()) {
            totalMiles = Double.parseDouble(Helperclass.getStartOdometer(this));

            if (currentmile >= totalMiles) {
                Helperclass.setlastIsDriving(true, this);
                Helperclass.setStartOdometer("", Deshboard_screen.this);
                value = true;
                locationn = locationnnv.getText().toString();

                String daa = datat.getText().toString();

                if (!daa.equals("")) {
                    String[] units = daa.split(",");
                    if (units.length > 10) {
                        if (!units[4].toString().isEmpty()) getodometer = Double.valueOf(units[4]);
                        if (!units[6].toString().isEmpty()) enginhour = Double.valueOf(units[6]);
                    }

                }
                float y = 2f;
                status = "Drive";
                orign = "Auto";

                inserdata(getDateTime(), y, getDateTime(), status, locationn, getodometer, enginhour, orign, "logs");

                startStopDriveAction();
//testing here


            } else {
                value = false;
            }
        }
        return value;
    }

    double time = 0.0;


    public static boolean canDrive5Miles(double speedMph) {

        boolean canDrive = speedMph > 8; // assuming that the car can drive 5 miles within 5 minute
        Log.d("TAG", " =========canDrive========== " + canDrive);
        return canDrive;
    }

    public void calculateTimeAndDistance(double speedkph) {
        Log.d("TAG", " =========speedkph========== " + speedkph);

        if (speedkph >= 8.04672) {
            Helperclass.setlastIsDriving(true, this);
            Helperclass.setStartOdometer("", Deshboard_screen.this);
            locationn = locationnnv.getText().toString();

            String daa = datat.getText().toString();

            if (!daa.equals("")) {
                String[] units = daa.split(",");
                if (units.length > 10) {
                    if (!units[4].toString().isEmpty()) getodometer = Double.valueOf(units[4]);
                    if (!units[6].toString().isEmpty()) enginhour = Double.valueOf(units[6]);
                }

            }
            float y = 2f;
            status = "Drive";
            orign = "Auto";

            inserdata(getDateTime(), y, getDateTime(), status, locationn, getodometer, enginhour, orign, "logs");

            singalstatus.setText("D");
            fullstatus.setText("Drive");
            driveingtiming.setVisibility(View.VISIBLE);
            ondutytiming.setVisibility(View.GONE);
            sleeptiming.setVisibility(View.GONE);
            offdutyting.setVisibility(View.GONE);
            yardtiming.setVisibility(View.GONE);
            personaltiming.setVisibility(View.GONE);


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
            Helperclass.setisDriving(true, this);

            Helperclass.setSpeedStartTime(Long.valueOf(0), this);

        } else if (currentSpeed == STOPPED_SPEED_THRESHOLD) {
            Helperclass.setisDriving(false, this);
            Log.d("TAG", " =========NOT DRIVE CREATE LOG========== " + Helperclass.getSpeedStartTime(this));
            Log.d("TAG", " ========= savestatus ========== " + Helperclass.getSaveStatus(this));
            Log.d("TAG", " ========= status ========== " + status);
            if (Helperclass.getSpeedStartTime(this) == 0) {
                Helperclass.setSpeedStartTime(System.currentTimeMillis(), this);
                if (status.equals("Drive") && !status.equalsIgnoreCase(Helperclass.getSaveStatus(this))) {

                    //CREATE second log of Engine state
                    String daa = datat.getText().toString();
                    locationn = locationnnv.getText().toString();

                    String[] units = daa.split(",");
                    if (units.length > 7) {
                        float y = 4f;
                        orign = "Auto";
                        if (!units[4].toString().isEmpty()) getodometer = Double.valueOf(units[4]);
                        if (!units[6].toString().isEmpty()) enginhour = Double.valueOf(units[6]);

                        Helperclass.setSaveStatus(status, this);
                        Helperclass.setStatusChange(true, this);

                        inserdata(getDateTime(), y, getDateTime(), status, locationn, getodometer, enginhour, orign, "logs");
//STOP  E_OFF START OFFD

                        singalstatus.setText("OFF");
                        fullstatus.setText("Off duty");
                        driveingtiming.setVisibility(View.GONE);
                        ondutytiming.setVisibility(View.GONE);
                        sleeptiming.setVisibility(View.GONE);
                        offdutyting.setVisibility(View.VISIBLE);
                        yardtiming.setVisibility(View.GONE);
                        personaltiming.setVisibility(View.GONE);


                    }

                }

            }
            if (System.currentTimeMillis() - Helperclass.getSpeedStartTime(this) >= FIVE_MINUTES_IN_MS) {
                Log.d("TAG", " =========isNotificationScheduled========== " + isNotificationScheduled);
                Log.d("TAG", " =========STATUS========== " + Helperclass.getSaveStatus(this).equalsIgnoreCase("Drive"));
                // Schedule a notification and log creation after 5 minutes
                if (Helperclass.getlastIsDriving(this)) {
                    scheduleNotification();
                    //isNotificationScheduled = true;
                }
            }
        } else {
            Helperclass.setSpeedStartTime(Long.valueOf(0), this);
        }

        Log.d("TAG", " =========ISDRIVE========== " + Helperclass.getisDriving(this));
        Log.d("TAG", " =========LASTISDRIVING========== " + Helperclass.getlastIsDriving(this));
        Log.d("TAG", " =========STATUSCCC========== " + Helperclass.getStatusChange(this));
        Log.d("TAG", " =========SAVEstatus========== " + Helperclass.getSaveStatus(this));
        Log.d("TAG", " =========status========== " + status);
        // Change the status based on driving condition
        if (Helperclass.getisDriving(this)) {
            if (!status.equalsIgnoreCase(Helperclass.getSaveStatus(this))) {
                //CREATE second log of Engine state
                String daa = datat.getText().toString();
                locationn = locationnnv.getText().toString();

                String[] units = daa.split(",");
                if (units.length > 6) {
                    orign = "Auto";
                    if (!units[4].toString().isEmpty()) getodometer = Double.valueOf(units[4]);
                    if (!units[6].toString().isEmpty()) enginhour = Double.valueOf(units[6]);
                    Helperclass.setSaveStatus(status, this);
                    Helperclass.setStatusChange(true, this);
                    float y = 0f;
                    if (status.equals("E_OFF")) {
                        // E_OFF START MODE OFFD and STOP other mode
                        y = 4f;
                        singalstatus.setText("OFF");
                        fullstatus.setText("Off duty");
                        driveingtiming.setVisibility(View.GONE);
                        ondutytiming.setVisibility(View.GONE);
                        sleeptiming.setVisibility(View.GONE);
                        offdutyting.setVisibility(View.VISIBLE);
                        yardtiming.setVisibility(View.GONE);
                        personaltiming.setVisibility(View.GONE);


                    } else if (status.equals("E_ON")) {
                        // E_ON START MODE OND and stop other mode
                        y = 1f;
                        singalstatus.setText("ON");
                        fullstatus.setText("on duty");
                        driveingtiming.setVisibility(View.GONE);
                        ondutytiming.setVisibility(View.VISIBLE);
                        sleeptiming.setVisibility(View.GONE);
                        offdutyting.setVisibility(View.GONE);
                        yardtiming.setVisibility(View.GONE);
                        personaltiming.setVisibility(View.GONE);
                        startstopshift(true);

                    }

                    inserdata(getDateTime(), y, getDateTime(), status, locationn, getodometer, enginhour, orign, "logs");


                }

            } else {
                Log.d("TAG", " =========NO CHANGE IN STATUS========== " + status);

                String daa = datat.getText().toString();
                locationn = locationnnv.getText().toString();
                if (!daa.equals("")) {
                    String[] units = daa.split(",");
                    if (units.length > 6) {
                        try {

                            if (status.equals("E_ON") && !Helperclass.getlastIsDriving(this)) {

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
            if (!status.equalsIgnoreCase(Helperclass.getSaveStatus(this))) {

                //CREATE second log of Engine state
                String daa = datat.getText().toString();
                locationn = locationnnv.getText().toString();

                String[] units = daa.split(",");
                if (units.length > 12) {
                    float y = 0f;
                    orign = "Auto";
                    if (!units[4].toString().isEmpty()) getodometer = Double.valueOf(units[4]);
                    if (!units[6].toString().isEmpty()) enginhour = Double.valueOf(units[6]);
                    Helperclass.setSaveStatus(status, this);
                    Helperclass.setStatusChange(true, this);

                    if (status.equals("E_OFF")) {
                        // E_OFF START MODE OFFD and STOP other mode
                        y = 4f;
                        singalstatus.setText("OFF");
                        fullstatus.setText("Off duty");
                        driveingtiming.setVisibility(View.GONE);
                        ondutytiming.setVisibility(View.GONE);
                        sleeptiming.setVisibility(View.GONE);
                        offdutyting.setVisibility(View.VISIBLE);
                        yardtiming.setVisibility(View.GONE);
                        personaltiming.setVisibility(View.GONE);


                    } else if (status.equals("E_ON")) {
                        // E_ON START MODE OND and stop other mode
                        y = 1f;
                        singalstatus.setText("ON");
                        fullstatus.setText("on duty");
                        driveingtiming.setVisibility(View.GONE);
                        ondutytiming.setVisibility(View.VISIBLE);
                        sleeptiming.setVisibility(View.GONE);
                        offdutyting.setVisibility(View.GONE);
                        yardtiming.setVisibility(View.GONE);
                        personaltiming.setVisibility(View.GONE);
                        startstopshift(true);

                    } else if (status.equals("Drive")) {
                        y = 2f;
                        singalstatus.setText("D");
                        fullstatus.setText("Drive");
                        driveingtiming.setVisibility(View.VISIBLE);
                        ondutytiming.setVisibility(View.GONE);
                        sleeptiming.setVisibility(View.GONE);
                        offdutyting.setVisibility(View.GONE);
                        yardtiming.setVisibility(View.GONE);
                        personaltiming.setVisibility(View.GONE);


                        startStopDriveAction();
//testing here

                    }
                    inserdata(getDateTime(), y, getDateTime(), status, locationn, getodometer, enginhour, orign, "logs");
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

        status_dilog.setContentView(R.layout.change_status_notification);
        status_dilog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        status_dilog.setCancelable(true);
        status_dilog.show();
        CardView no = status_dilog.findViewById(R.id.no);
        CardView drive = status_dilog.findViewById(R.id.drive);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status_dilog.dismiss();
                confirmation_logout.setContentView(R.layout.edit_dialog);
                confirmation_logout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                CardView yes = confirmation_logout.findViewById(R.id.yes);
                CardView cancel = confirmation_logout.findViewById(R.id.cancel);
                TextView text = confirmation_logout.findViewById(R.id.dialog_title);
                EditText edittextedit = confirmation_logout.findViewById(R.id.edittextedit);
                text.setText("On duty reason");

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String result = edittextedit.getText().toString();
                        if (result.equals("")) {
                            Toast.makeText(getApplicationContext(), "Enter something", Toast.LENGTH_SHORT).show();
                        } else {
                            singalstatus.setText("ON");
                            fullstatus.setText("on duty");
                            driveingtiming.setVisibility(View.GONE);
                            ondutytiming.setVisibility(View.VISIBLE);
                            sleeptiming.setVisibility(View.GONE);
                            offdutyting.setVisibility(View.GONE);
                            yardtiming.setVisibility(View.GONE);
                            personaltiming.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Your reason - " + result, Toast.LENGTH_SHORT).show();
                            confirmation_logout.dismiss();

                            locationn = locationnnv.getText().toString();
                            String daa = datat.getText().toString();

                            if (!daa.equals("")) {
                                String[] units = daa.split(",");
                                if (units.length > 10) {
                                    if (!units[4].toString().isEmpty())
                                        getodometer = Double.valueOf(units[4]);
                                    if (!units[6].toString().isEmpty())
                                        enginhour = Double.valueOf(units[6]);

                                }
                            }


                            float y = 1f;
                            status = "OND";
                            orign = "Manual";
                            Helperclass.setlastIsDriving(false, Deshboard_screen.this);
                            inserdata(getDateTime(), y, getDateTime(), status, locationn, getodometer, enginhour, orign, "logs");
                            startstopshift(true);

                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmation_logout.dismiss();
                        status_dilog.show();
                    }
                });
                confirmation_logout.setCancelable(false);
                confirmation_logout.show();
            }
        });

        drive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                status_dilog.dismiss();
            }
        });

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

    private final class TimeTask extends TimerTask {
        public TimeTask() {
        }

        public void run() {
            if (heplper.timerCountinge()) {
                long datt = (new Date()).getTime();
                long time = datt - heplper.startTimee().getTime();
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


    public void createFirstLog() {
        Log.d("TAG", " =========createFirstLog========== " + Helperclass.getFirstLogin(this));
        if (Helperclass.getFirstLogin(this)) {

            String daa = datat.getText().toString();
            locationn = locationnnv.getText().toString();

            if (!daa.equals("")) {
                String[] units = daa.split(",");
                Log.d("TAG", " =========OFFD========== " + units[4]);
                if (units.length > 11) {
                    if (!units[4].isEmpty()) getodometer = Double.valueOf(units[4]);
                    if (!units[6].isEmpty()) enginhour = Double.valueOf(units[6]);
                }
            }

            float y = 4f;
            status = "OFFD";
            orign = "Auto";

            inserdata(getDateTime(), y, getDateTime(), status, locationn, getodometer, enginhour, orign, "logs");
            Helperclass.setFirstLogin(false, this);

            singalstatus.setText("OFF");
            fullstatus.setText("Off duty");
            driveingtiming.setVisibility(View.GONE);
            ondutytiming.setVisibility(View.GONE);
            sleeptiming.setVisibility(View.GONE);
            offdutyting.setVisibility(View.VISIBLE);
            yardtiming.setVisibility(View.GONE);
            personaltiming.setVisibility(View.GONE);

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
                if (heplper.timerCountinge()) {
                    heplper.setStopTimee(new Date());
                    stopsleep();
                }
                if (yardmoveshelper.ytimerCountinge()) {
                    yardmoveshelper.ysetStopTimee(new Date());
                    stopy();
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


                    offdutyting.setText(time);
                    handl.postDelayed(this, 1000);
                }
            });
        }
    }

    public void createINTLog() {


        String daa = datat.getText().toString();
        locationn = locationnnv.getText().toString();

        if (!daa.equals("")) {
            String[] units = daa.split(",");
            Log.d("TAG", " =========OFFD========== " + units[4]);
            if (units.length > 11) {
                if (!units[4].isEmpty()) getodometer = Double.valueOf(units[4]);
                if (!units[6].isEmpty()) enginhour = Double.valueOf(units[6]);
            }
        }

        float y = this.y;
        orign = "Auto";
        inserdata(getDateTime(), y, getDateTime(), "INT", locationn, getodometer, enginhour, orign, "logs");

    }

    public static double convertKmToMiles(double km) {
        double miles = km / 1.609344;
        //  double miles = km * 0.621371;
        return miles;
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

}