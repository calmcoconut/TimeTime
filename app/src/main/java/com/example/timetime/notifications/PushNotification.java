package com.example.timetime.notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import androidx.core.app.NotificationCompat;
import com.example.timetime.AppStart;
import com.example.timetime.R;
import com.example.timetime.ui.homesummary.LogTimeToActivity;
import com.example.timetime.utils.DevProperties;

import java.util.concurrent.TimeUnit;

public class PushNotification {
    private static AlarmManager alarmManager;
    private static PendingIntent pendingIntent;
    private static Long pushInterval =
            TimeUnit.MILLISECONDS.convert(DevProperties.INTERVAL_PUSH_NOTIFICATION_MINUTES, TimeUnit.MINUTES);

    public static void createRepeatingPushNotification(Context context) {
        Notification notification = buildNotification(context);
        initScheduleNotification(context, notification);
    }

    private static Notification buildNotification(Context context) {
        Intent logTimeToActivity = new Intent(context, LogTimeToActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, logTimeToActivity, 0);

        return new NotificationCompat.Builder(context, AppStart.CHANNEL_ID_PUSH)
                .setSmallIcon(R.drawable.ic_access_time_black_24dp)
                .setContentTitle("Log your time")
                .setContentText("What have you been up to?")
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_EVENT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
    }

    private static void initScheduleNotification(Context context, Notification notification) {
        if (alarmManager == null) {
            alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        }
        Intent notificationIntent = new Intent(context, PushBroadCastNotificationReceiver.class)
                .putExtra(PushBroadCastNotificationReceiver.NOTIFICATION_PUSH_REPEATING,
                        notification);

        pendingIntent = PendingIntent.getBroadcast(context,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        updateInterval();
    }

    private static void updateInterval() {
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, (SystemClock.elapsedRealtime() +
                    pushInterval), pushInterval, pendingIntent);
        }
    }

    public static void pushNotificationEnabled(Context context, boolean bool) {
        DevProperties.IS_PUSH_NOTIFICATION_ENABLED = bool;
        if (bool) {
            if (alarmManager != null) {
                alarmManager.cancel(pendingIntent);
            }
            createRepeatingPushNotification(context);
        }
        else {
            if (alarmManager != null) {
                alarmManager.cancel(pendingIntent);
            }
        }
    }

    public static Long getPushInterval() {
        return pushInterval;
    }

    public static void setPushInterval(Long pushInterval) {
        alarmManager.cancel(pendingIntent);
        PushNotification.pushInterval = pushInterval;
        updateInterval();
    }
}
