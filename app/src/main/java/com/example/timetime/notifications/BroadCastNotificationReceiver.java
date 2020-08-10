package com.example.timetime.notifications;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationManagerCompat;

public class BroadCastNotificationReceiver extends BroadcastReceiver {
    public static final String NOTIFICATION_REPEATING = "repeating_interval_notification";
    public static final int NOTIFICATION_REPEATING_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Notification notification = intent.getParcelableExtra(NOTIFICATION_REPEATING);

        if (notification != null) {
            notificationManager.notify(NOTIFICATION_REPEATING_ID, notification);
        }
    }

}
