package com.example.timetime.notifications;

import android.app.Activity;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import androidx.core.app.NotificationManagerCompat;

public class BroadCastNotificationReceiver extends BroadcastReceiver {
    public static final String NOTIFICATION_PUSH_REPEATING = "repeating_interval_push_notification";
    public static final int NOTIFICATION_PUSH_REPEATING_ID = 1;
    public static final String NOTIFICATION_LOCKSCREEN_REPEATING = "repeating_interval_lockscreen_notification";
    public static final int NOTIFICATION_LOCKSCREEN_REPEATING_ID = 2;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Notification pushNotification = intent.getParcelableExtra(NOTIFICATION_PUSH_REPEATING);
        Notification lockScreenNotification = intent.getParcelableExtra(NOTIFICATION_LOCKSCREEN_REPEATING);

        if (lockScreenNotification != null && isScreenLocked(context)) {
            notificationManager.notify(NOTIFICATION_LOCKSCREEN_REPEATING_ID, lockScreenNotification);
        }

        if (pushNotification != null) {
            notificationManager.notify(NOTIFICATION_PUSH_REPEATING_ID, pushNotification);
        }

    }

    private boolean isScreenLocked(Context context) {
        PowerManager k =(PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (k != null) {
            return !k.isInteractive();
        }
        return false;
    }

    private void turnKeyGuardOff(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            activity.setShowWhenLocked(true);
        }
    }

}
