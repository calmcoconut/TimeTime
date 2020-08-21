package com.example.timetime.notifications;

import android.app.Activity;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import androidx.core.app.NotificationManagerCompat;

import java.util.concurrent.TimeUnit;

public class PushBroadCastNotificationReceiver extends BroadcastReceiver {
    public static final String NOTIFICATION_PUSH_REPEATING = "repeating_interval_push_notification";
    public static final int NOTIFICATION_PUSH_REPEATING_ID = 1;
    public static final String NOTIFICATION_LOCKSCREEN_REPEATING = "repeating_interval_lockscreen_notification";
    public static final int NOTIFICATION_LOCKSCREEN_REPEATING_ID = 2;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Notification pushNotification = intent.getParcelableExtra(NOTIFICATION_PUSH_REPEATING);
        Notification lockScreenNotification = intent.getParcelableExtra(NOTIFICATION_LOCKSCREEN_REPEATING);

        if (pushNotification != null) {
            notificationManager.notify(NOTIFICATION_PUSH_REPEATING_ID, pushNotification);
        }

        if (lockScreenNotification != null) {
            while (!isScreenLocked(context)) { // only push notification on lockscreen
                try {
                    TimeUnit.SECONDS.sleep(10L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            notificationManager.notify(NOTIFICATION_LOCKSCREEN_REPEATING_ID, lockScreenNotification);
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
