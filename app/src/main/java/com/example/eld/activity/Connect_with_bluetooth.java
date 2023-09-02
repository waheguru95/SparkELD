package com.example.eld.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eld.R;
import com.example.eld.custumclass.Helperclass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.iosix.eldblelib.EldBleConnectionStateChangeCallback;
import com.iosix.eldblelib.EldBleDataCallback;
import com.iosix.eldblelib.EldBleError;
import com.iosix.eldblelib.EldBleScanCallback;
import com.iosix.eldblelib.EldBroadcast;
import com.iosix.eldblelib.EldBroadcastTypes;
import com.iosix.eldblelib.EldCachedPeriodicRecord;
import com.iosix.eldblelib.EldDataRecord;
import com.iosix.eldblelib.EldManager;
import com.iosix.eldblelib.EldScanObject;
import com.tuyenmonkey.mkloader.MKLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class Connect_with_bluetooth extends AppCompatActivity {
    ImageView bluetooth_back;
    TextView deviceresult;
    Switch switchbutton,scanning;
    TextView driveerid,drivername,shoemgs;
    MKLoader loadingd;
    RecyclerView showeld;

    private static final int REQUEST_BASE = 100;
    private static final int REQUEST_BT_ENABLE = REQUEST_BASE + 1;
    private EldManager mEldManager;
    private Set<EldBroadcastTypes> subscribedRecords = EnumSet.of(EldBroadcastTypes.ELD_BUFFER_RECORD, EldBroadcastTypes.ELD_CACHED_RECORD, EldBroadcastTypes.ELD_FUEL_RECORD, EldBroadcastTypes.ELD_DATA_RECORD, EldBroadcastTypes.ELD_DRIVER_BEHAVIOR_RECORD, EldBroadcastTypes.ELD_EMISSIONS_PARAMETERS_RECORD, EldBroadcastTypes.ELD_ENGINE_PARAMETERS_RECORD, EldBroadcastTypes.ELD_TRANSMISSION_PARAMETERS_RECORD);


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_with_bluetooth);
        bluetooth_back = findViewById(R.id.bluetooth_back);
        deviceresult = findViewById(R.id.deviceresult);
        switchbutton = findViewById(R.id.switchbutton);
        driveerid=findViewById(R.id.driveerid);
        drivername=findViewById(R.id.drivername);
        scanning=findViewById(R.id.scanning);
        loadingd=findViewById(R.id.loadingd);
        showeld=findViewById(R.id.showeld);
        shoemgs=findViewById(R.id.shoemgs);

        driveerid.setText(Helperclass.getEmail(this));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
        mEldManager = EldManager.GetEldManager(getApplicationContext(), "123456789A");
        final BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bAdapter.isEnabled()) {
            switchbutton.setChecked(true);
            scanning.setVisibility(View.VISIBLE);
        }else {
            scanning.setVisibility(View.GONE);
            switchbutton.setChecked(false);

        }
        bluetooth_back.setOnClickListener(v -> finish());

        switchbutton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
//                    bAdapter.enable();
                scanning.setVisibility(View.VISIBLE);
            } else {
//                    bAdapter.disable();
                scanning.setVisibility(View.GONE);
                deviceresult.setText("Disconnected");
            }
        });
        scanning.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
//                    ScanForEld();
                loadingd.setVisibility(View.VISIBLE);
                showeld.setVisibility(View.VISIBLE);
                shoemgs.setVisibility(View.GONE);
            } else {
                loadingd.setVisibility(View.GONE);
                showeld.setVisibility(View.GONE);
                shoemgs.setVisibility(View.GONE);
            }
        });

    }
    private EldBleDataCallback bleDataCallback = new EldBleDataCallback() ;

    private final EldBleConnectionStateChangeCallback bleConnectionStateChangeCallback = new EldBleConnectionStateChangeCallback() {
        @Override
        public void onConnectionStateChange(final int newState) {
          runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (newState == 0) {
                        deviceresult.setText("Disconnected");

                    } else if (newState == 2) {
                        deviceresult.setText("Connected");
//                        mMACView.setText("CONNECTED TO ELD: " + mEldManager.EldGetSerial());
                    }
//                    else {
//                        mStatusView.append("New State of connection " + Integer.toString(newState, 10) + "\n");
//                    }

                }
            });
        }
    };


    private EldBleScanCallback bleScanCallback = new EldBleScanCallback() {

        @Override
        public void onScanResult(EldScanObject device) {

            final String strDevice;
            if (device != null) {
                strDevice = device.getDeviceId();
          runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });

                EldBleError res = mEldManager.ConnectToEld(bleDataCallback, subscribedRecords, bleConnectionStateChangeCallback);

                if (res != EldBleError.SUCCESS) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            shoemgs.setVisibility(View.VISIBLE);
                            loadingd.setVisibility(View.GONE);
                            showeld.setVisibility(View.GONE);
                            shoemgs.setText("Connection Failed");
                        }
                    });
                }
            } else {
           runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        shoemgs.setVisibility(View.VISIBLE);
                        loadingd.setVisibility(View.GONE);
                        showeld.setVisibility(View.GONE);
                        shoemgs.setText("No device found");

                    }
                });
            }
        }
        @Override
        public void onScanResult(ArrayList deviceList) {
            final String strDevice;
            EldScanObject so;

            if (deviceList != null) {
                so = (EldScanObject) deviceList.get(0);
                strDevice = so.getDeviceId();

       runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
                EldBleError res = mEldManager.ConnectToEld(bleDataCallback, subscribedRecords, bleConnectionStateChangeCallback, strDevice);
                if (res != EldBleError.SUCCESS) {
                 runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            shoemgs.setVisibility(View.VISIBLE);
                            loadingd.setVisibility(View.GONE);
                            showeld.setVisibility(View.GONE);
                            shoemgs.setText("Connection Failed");
                        }
                    });
                }
            } else {
             runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        shoemgs.setVisibility(View.VISIBLE);
                        loadingd.setVisibility(View.GONE);
                        showeld.setVisibility(View.GONE);
                        shoemgs.setText("No device found");

                    }
                });
            }
        }
    };


    private void ScanForEld() {
        if (mEldManager.ScanForElds(bleScanCallback) == EldBleError.BLUETOOTH_NOT_ENABLED)
            mEldManager.EnableBluetooth(REQUEST_BT_ENABLE);
    }
}