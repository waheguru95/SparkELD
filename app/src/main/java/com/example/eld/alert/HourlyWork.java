package com.example.eld.alert;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class HourlyWork extends Worker {
    private Context mContext;

    public HourlyWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mContext = context.getApplicationContext();

    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d("MyWorker", "Starting work...=========");

        Data inputData = getInputData();
        Log.d("MyWorker", "Starting work...=========" + inputData.getBoolean("condition", false));
        Log.d("MyWorker", "PeriodicWorkRequest...=========" + inputData.getBoolean("Houly", false));
        if (inputData.getBoolean("Houly", false)) {
            // Perform actions for PeriodicWorkRequest
            Log.d("MyWorker", "PeriodicWorkRequest");
         //   ((DashBoardScreen) mContext).createINTLog(); // Call your activity method

        }


        return Result.success();
    }


}



