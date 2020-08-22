package com.example.timetime.notifications;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationManagerCompat;

public class LockScreenBroadCastNotificationReceiver extends BroadcastReceiver {
    public static final String NOTIFICATION_LOCKSCREEN_REPEATING = "repeating_interval_lockscreen_notification";
    public static final int NOTIFICATION_LOCKSCREEN_REPEATING_ID = 2;

    @Override
    public void onReceive(Context context, Intent intent) {
        Notification lockScreenNotification = intent.getParcelableExtra(NOTIFICATION_LOCKSCREEN_REPEATING);
        if (lockScreenNotification != null) {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(NOTIFICATION_LOCKSCREEN_REPEATING_ID, lockScreenNotification);
        }
    }
}
