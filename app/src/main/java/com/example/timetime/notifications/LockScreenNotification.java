package com.example.timetime.notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.core.app.NotificationCompat;
import com.example.timetime.AppStart;
import com.example.timetime.R;
import com.example.timetime.ui.homesummary.LogTimeToActivity;
import com.example.timetime.utils.DevProperties;

import java.util.concurrent.TimeUnit;

public class LockScreenNotification {

    private static AlarmManager alarmManager;
    private static PendingIntent pendingIntentForBroadCastReceiver;
    private static Long lockScreenInterval = TimeUnit.MILLISECONDS.convert(DevProperties.INTERVAL_LOCKSCREEN_NOTIFICATION_MINUTES, TimeUnit.MINUTES);

    public static void createRepeatingLockScreenNotification(Context context) {
        Notification notification = buildNotification(context);
        initScheduleNotification(context, notification);
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

        boolean alarmUp = (PendingIntent.getBroadcast(context, 1, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT) == null);

        if (alarmUp) {
            pendingIntentForBroadCastReceiver = PendingIntent.getBroadcast(
                    context,
                    1,
                    notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            updateInterval();
        }
    }

    private static void updateInterval() {
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, (SystemClock.elapsedRealtime() +
                    lockScreenInterval), lockScreenInterval, pendingIntentForBroadCastReceiver);
        }
    }

    public static void lockScreenNotificationEnabled(Context context, boolean bool) {
        DevProperties.IS_LOCKSCREEN_NOTIFICATION_ENABLED = bool;
        if (bool) {
            if (alarmManager != null) {
                alarmManager.cancel(pendingIntentForBroadCastReceiver);
            }
            createRepeatingLockScreenNotification(context);
        }
        else {
            if (alarmManager != null) {
                alarmManager.cancel(pendingIntentForBroadCastReceiver);
            }
        }
    }

    public static Long getLockScreenInterval() {
        return lockScreenInterval;
    }

    public static void setLockScreenInterval(Long lockScreenInterval) {
        alarmManager.cancel(pendingIntentForBroadCastReceiver);
        LockScreenNotification.lockScreenInterval = lockScreenInterval;
        updateInterval();
    }
}
