package com.example.timetime.notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.core.app.NotificationCompat;
import androidx.work.BackoffPolicy;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import com.example.timetime.AppStart;
import com.example.timetime.R;
import com.example.timetime.ui.homesummary.LogTimeToActivity;
import com.example.timetime.utils.DevProperties;

import java.util.concurrent.TimeUnit;

public class LockScreenNotification {

    private static AlarmManager alarmManager;
    private static PendingIntent pendingIntentForBroadCastReceiver;
    private static PendingIntent pendingIntentForWork;
    private static Long lockScreenInterval = DevProperties.INTERVAL_LOCKSCREEN_NOTIFICATION_MINUTES * 5L;

    public static void createRepeatingLockScreenNotification(Context context) {
        Notification notification = buildNotification(context);
        initScheduleNotification(context, notification);
//        initWorkManager(context);
    }


    private static Notification buildNotification(Context context) {
        Intent logTimeToActivity = new Intent(context, LogTimeToActivity.class);
        logTimeToActivity.putExtra(DevProperties.IS_NOTIFICATION_EXTRA_KEY, true);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, logTimeToActivity, 0);

        Bundle bundle = new Bundle();
        bundle.putBoolean(DevProperties.IS_NOTIFICATION_EXTRA_KEY, true);

        return new NotificationCompat.Builder(context, AppStart.CHANNEL_ID_LOCKSCREEN)
                .setSmallIcon(R.drawable.ic_access_time_black_24dp)
                .setContentTitle("Log your time")
                .setContentText("What have you been up to?")
                .setDefaults(Notification.DEFAULT_ALL)
                .setNotificationSilent()
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setAutoCancel(true)
                .setFullScreenIntent(pendingIntent, true)
                .build();
    }

    private static void initScheduleNotification(Context context, Notification notification) {
        if (alarmManager == null) {
            alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        }
        Intent notificationIntent = new Intent(context, LockScreenBroadCastNotificationReceiver.class)
                .putExtra(LockScreenBroadCastNotificationReceiver.NOTIFICATION_LOCKSCREEN_REPEATING,
                        notification);

        pendingIntentForBroadCastReceiver = PendingIntent.getBroadcast(
                context,
                1,
                notificationIntent,
                0);

        updateInterval();
    }

    private static void initWorkManager(Context context) {
        WorkManager workManager = WorkManager.getInstance(context);
        PeriodicWorkRequest screenOffWorkRequest =
                new PeriodicWorkRequest.Builder(ScreenOnOffWorker.class,
                        15, TimeUnit.MINUTES)
                        .addTag(DevProperties.IS_NOTIFICATION_EXTRA_KEY)
                        .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                        .build();

        workManager.enqueueUniquePeriodicWork(
                "lockscreen",
                ExistingPeriodicWorkPolicy.KEEP,
                screenOffWorkRequest);
    }

    private static void updateInterval() {
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, (SystemClock.elapsedRealtime() +
                    lockScreenInterval), lockScreenInterval, pendingIntentForBroadCastReceiver);
        }
    }

    public static void disableLockScreenNotification() {
        DevProperties.IS_LOCKSCREEN_NOTIFICATION_ENABLED = false;
    }

    public static Long getLockScreenInterval() {
        return lockScreenInterval;
    }

    public static void setLockScreenInterval(Long lockScreenInterval) {
        LockScreenNotification.lockScreenInterval = lockScreenInterval;
        updateInterval();
    }
}
