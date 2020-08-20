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

public class LockScreenNotification {

    private static AlarmManager alarmManager;
    private static PendingIntent pendingIntent;
    private static Long lockScreenInterval = DevProperties.INTERVAL_LOCKSCREEN_NOTIFICATION_MINUTES * 5L;

    public static void createRepeatingLockScreenNotification(Context context) {
        Notification notification = buildNotification(context);
        initScheduleNotification(context, notification);
    }


    private static Notification buildNotification(Context context) {
        Intent logTimeToActivity = new Intent(context, LogTimeToActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, logTimeToActivity, 0);

        return new NotificationCompat.Builder(context, AppStart.CHANNEL_ID_LOCKSCREEN)
                .setSmallIcon(R.drawable.ic_access_time_black_24dp)
                .setContentTitle("Log your time")
                .setContentText("What have you been up to?")
                .setDefaults(Notification.DEFAULT_ALL)
//                .setNotificationSilent()
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
        Intent notificationIntent = new Intent(context, BroadCastNotificationReceiver.class)
                .putExtra(BroadCastNotificationReceiver.NOTIFICATION_LOCKSCREEN_REPEATING,
                        notification);

        pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        updateInterval();
    }

    private static void updateInterval() {
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, (SystemClock.elapsedRealtime() +
                    lockScreenInterval), lockScreenInterval, pendingIntent);
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
