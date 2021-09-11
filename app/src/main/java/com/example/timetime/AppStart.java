package com.example.timetime;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class AppStart extends Application {
    public static final String CHANNEL_ID_PUSH = "push notification";
    public static final String CHANNEL_ID_LOCKSCREEN = "lockScreen notification";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel pushNotificationChannel = new NotificationChannel(
                    CHANNEL_ID_PUSH,
                    "Push Notification",
                    NotificationManager.IMPORTANCE_LOW
            );

            NotificationChannel lockScreenNotificationChannel = new NotificationChannel(
                    CHANNEL_ID_LOCKSCREEN,
                    "LockScreen Notification",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(pushNotificationChannel);
                manager.createNotificationChannel(lockScreenNotificationChannel);
            }
        }
    }
}
