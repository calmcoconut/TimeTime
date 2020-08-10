package com.example.timetime.notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.example.timetime.AppStart;
import com.example.timetime.DevProperties;
import com.example.timetime.R;
import com.example.timetime.ui.homesummary.LogTimeToActivity;

public class PushNotification {
    private static AlarmManager alarmManager;
    private static PendingIntent pendingIntent;
    private static Long pushInterval = DevProperties.INTERVAL_NOTIFICATION_MINUTES * 1000L;

    public static void createRepeatingNotification(Context context) {
        Notification notification = buildNotification(context);
        initScheduleNotification(context, notification);
    }

    private static Notification buildNotification(Context context) {
        Intent logTimeToActivity = new Intent(context, LogTimeToActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, logTimeToActivity, 0);

        return new NotificationCompat.Builder(context, AppStart.CHANNEL_ID_1)
                .setSmallIcon(R.drawable.ic_access_time_black_24dp)
                .setContentTitle("Log your time")
                .setContentText("What have you been up to?")
                .setPriority(NotificationManagerCompat.IMPORTANCE_LOW)
                .setCategory(NotificationCompat.CATEGORY_EVENT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
    }

    private static void initScheduleNotification(Context context, Notification notification) {
        if (alarmManager == null) {
            alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        }
        Intent notificationIntent = new Intent(context, BroadCastNotificationReceiver.class)
                .putExtra(BroadCastNotificationReceiver.NOTIFICATION_REPEATING,
                        notification);

        pendingIntent = PendingIntent.getBroadcast(context,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        updateInterval();
    }

    private static void updateInterval() {
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, (SystemClock.elapsedRealtime() +
                    pushInterval), pushInterval, pendingIntent);
        }
    }

    public static void disablePushNotification() {
        DevProperties.IS_PUSH_NOTIFICATION_ENABLED = false;
    }

    public static Long getPushInterval() {
        return pushInterval;
    }

    public static void setPushInterval(Long pushInterval) {
        PushNotification.pushInterval = pushInterval;
        updateInterval();
    }
}
