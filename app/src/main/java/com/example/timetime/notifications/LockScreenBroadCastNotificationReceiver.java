package com.example.timetime.notifications;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.BackoffPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.util.concurrent.TimeUnit;

public class LockScreenBroadCastNotificationReceiver extends BroadcastReceiver {
    public static final String NOTIFICATION_LOCKSCREEN_REPEATING = "repeating_interval_lockscreen_notification";
    public static final int NOTIFICATION_LOCKSCREEN_REPEATING_ID = 2;
    private static Notification lockScreenNotification;

    @Override
    public void onReceive(Context context, Intent intent) {
        lockScreenNotification = intent.getParcelableExtra(NOTIFICATION_LOCKSCREEN_REPEATING);
        initNotification(context);
    }

    private void initWorkManager(Context context, Intent intent) {
        if (intent.getParcelableExtra(NOTIFICATION_LOCKSCREEN_REPEATING) != null) {
            lockScreenNotification = intent.getParcelableExtra(NOTIFICATION_LOCKSCREEN_REPEATING);
            WorkManager workManager = WorkManager.getInstance(context);
            WorkRequest screenOffWorkRequest =
                    new OneTimeWorkRequest.Builder(ScreenOnOffWorker.class)
                            .setBackoffCriteria(BackoffPolicy.LINEAR,
                                    OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                                    TimeUnit.MILLISECONDS)
                            .build();

            workManager.enqueue(screenOffWorkRequest);
        }
    }

    public static void initNotification(Context context) {
        if (lockScreenNotification != null) {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(NOTIFICATION_LOCKSCREEN_REPEATING_ID, lockScreenNotification);
        }
        lockScreenNotification = null;
    }

}
