package com.example.timetime.notifications;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationManagerCompat;

public class PushBroadCastNotificationReceiver extends BroadcastReceiver {
    public static final String NOTIFICATION_PUSH_REPEATING = "repeating_interval_push_notification";
    public static final int NOTIFICATION_PUSH_REPEATING_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        Notification pushNotification = intent.getParcelableExtra(NOTIFICATION_PUSH_REPEATING);

        if (pushNotification != null) {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(NOTIFICATION_PUSH_REPEATING_ID, pushNotification);
        }
    }
}
