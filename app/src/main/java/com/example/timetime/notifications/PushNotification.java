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
import com.example.timetime.R;
import com.example.timetime.ui.homesummary.LogTimeToActivity;

public class PushNotification {
    public static AlarmManager alarmManager;

    public static void createRepeatingNotification(Context context) {
        Notification notification = createNotification(context);
        scheduleNotification(context, notification, AlarmManager.INTERVAL_HALF_HOUR);
    }

    private static Notification createNotification(Context context) {

        Intent activityIntent = new Intent(context, LogTimeToActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, activityIntent, 0);

        Notification notification = new NotificationCompat.Builder(context, AppStart.CHANNEL_ID_1)
                .setSmallIcon(R.drawable.ic_access_time_black_24dp)
                .setContentTitle("Log your time")
                .setContentText("TimeTime: What have you been up to?")
                .setPriority(NotificationManagerCompat.IMPORTANCE_LOW)
                .setCategory(NotificationCompat.CATEGORY_EVENT)
                .setContentIntent(pendingIntent)
                .build();
        return notification;
    }

    private static void scheduleNotification(Context context, Notification notification, long delay) {
        if (alarmManager == null) {
            alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        }
        Intent notificationIntent = new Intent(context, BroadCastNotificationReceiver.class);
        notificationIntent.putExtra(BroadCastNotificationReceiver.NOTIFICATION_REPEATING, notification);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        assert alarmManager != null;
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, (SystemClock.elapsedRealtime() +
                delay), delay, pendingIntent);
    }
}
