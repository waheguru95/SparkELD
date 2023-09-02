package com.example.eld.alert;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.eld.R;

public class DrivingStatusService extends Service {

    private Handler mHandler;
    private static final int INTERVAL = 300000; // 5 minutes
    private NotificationManager mNotificationManager;

    @Override
    public void onCreate() {
        super.onCreate();

        mHandler = new Handler();
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Start the periodic check
        mHandler.postDelayed(mRunnable, INTERVAL);
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            // Check if the driver is still driving
            boolean isDriving = checkDrivingStatus();
            if (!isDriving) {
                // Send a notification to the driver
                sendNotification();
            }
            // Continue the periodic check
            mHandler.postDelayed(this, INTERVAL);
        }
    };

    private boolean checkDrivingStatus() {
        // Check if the driver is currently driving
        // You can use your existing code to check the driving status here
        return false;
    }

    private void sendNotification() {
        // Create a notification to remind the driver to update their status
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, "default")
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("Update your status")
                        .setContentText("Are you still driving?")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true);

        // Show the notification
        mNotificationManager.notify(0, builder.build());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

