package com.example.timetime.notifications;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import org.jetbrains.annotations.NotNull;

public class NotificationSchedulerWorker extends Worker {

    public NotificationSchedulerWorker(@NonNull @NotNull Context context, @NonNull @NotNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @NotNull
    @Override
    public Result doWork() {
        triggerPushNotification();
        triggerLockScreenNotification();

        return Result.success();
    }

    private void triggerLockScreenNotification() {
        Log.d("isNotification", "lock screen notification triggered");
    }

    private void triggerPushNotification() {
        Log.d("isNotification", "push notification triggered");
    }
}
