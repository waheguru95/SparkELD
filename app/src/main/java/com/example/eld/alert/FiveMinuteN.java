package com.example.eld.alert;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.eld.R;
import com.example.eld.activity.DashboardActivity;


public class FiveMinuteN extends Worker {
    private Context mContext;

    private Handler mHandler;
    private Runnable mRunnable;
    private boolean mIsNotificationDisplayed;


    public FiveMinuteN(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mContext = context.getApplicationContext();

    }

    @NonNull
    @Override
    public Result doWork() {


        Data inputData = getInputData();
        Log.d("MyWorker", "Starting work...=========" + inputData.getBoolean("condition", false));
        Log.d("MyWorker", "PeriodicWorkRequest...=========" + inputData.getBoolean("Houly", false));
        if (inputData.getBoolean("condition", false)) {
            // Perform actions for OneTimeWorkRequest
            Log.d("MyWorker", "OneTimeWorkRequest");
            sendNotification();

        } else if (inputData.getBoolean("Houly", false)) {
            // Perform actions for PeriodicWorkRequest
            Log.d("MyWorker", "PeriodicWorkRequest");

            /*if (mListener != null) {
                mListener.onWorkerComplete();
            }
*/
        }


        return Result.success();
    }

    private void sendNotification() {
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        // Add extras to the intent
        intent.putExtra("condition", true);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

// Create a vibration pattern
        long[] pattern = {0, 5000}; // Vibrate immediately, then wait 100 ms, vibrate for 1 second
// Create a notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "ELD Channel"; // Name of the channel
            String description = "ELD Device"; // Description of the channel
            int importance = NotificationManager.IMPORTANCE_HIGH; // Importance level of the channel
            NotificationChannel channel = new NotificationChannel("channel_ELD", name, importance);
            channel.setDescription(description);
            // Get a NotificationManager instance using getSystemService
            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.createNotificationChannel(channel);
        }
        // Create the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "channel_ELD").setContentTitle("Still driving?").setContentText("Are you driving or not kindly update your status").setSmallIcon(R.drawable.logo).setContentIntent(pendingIntent).setPriority(NotificationCompat.PRIORITY_HIGH).setVibrate(pattern).setAutoCancel(true);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(1, builder.build());
      /*  mIsNotificationDisplayed = true;
        // Set up the handler to schedule logging every hour
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                // Check if the notification was displayed and not dismissed by the user
                if (mIsNotificationDisplayed) {
                    // Create a log and show a Toast
                    // createLog();
                    Log.d("MyWorker", "OneTimeWorkRequest=========>NO RESPONDE");

                }

                // Cancel the notification and stop the handler
                notificationManager.cancel(1);
                mHandler.removeCallbacks(mRunnable);
            }
        };
        mHandler.postDelayed(mRunnable, 30000); // 5 minutes*/

    }


}


