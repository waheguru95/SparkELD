package com.example.eld.alert;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.eld.utils.Breakhelper;
import com.example.eld.utils.DriveHelper;
import com.example.eld.utils.Shifthelper;

public class ResetDataReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("reset_progress_data")) {
            DriveHelper driveHelper = new DriveHelper(context);
            Breakhelper breakhelper = new Breakhelper(context);
            Shifthelper shifthelper = new Shifthelper(context);
            driveHelper.resetDriveTime();
            breakhelper.resetBreakTime();
            shifthelper.resetShiftTime();
        }
    }
}

