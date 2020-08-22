package com.example.timetime.notifications;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import androidx.annotation.Nullable;

public class ScreenOnOffService extends Service implements Runnable {
    private LockScreenBroadCastNotificationReceiver lockScreenBroadCastNotificationReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        this.run();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (LockScreenBroadCastNotificationReceiver.NOTIFICATION_LOCKSCREEN_REPEATING.equals(intent.getStringExtra(LockScreenBroadCastNotificationReceiver.NOTIFICATION_LOCKSCREEN_REPEATING))) {
            run();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterScreenStatusReceiver();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Intent intent = new Intent();
        intent.setAction("restart_service");
        intent.setClass(this, LockScreenBroadCastNotificationReceiver.class);
        sendBroadcast(intent);
    }

    private void unregisterScreenStatusReceiver() {
        try {
            if (lockScreenBroadCastNotificationReceiver != null) {
                unregisterReceiver(lockScreenBroadCastNotificationReceiver);
            }
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Override
    public void run() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        // registerScreenStatusReceiver
        lockScreenBroadCastNotificationReceiver = new LockScreenBroadCastNotificationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(lockScreenBroadCastNotificationReceiver, filter);
    }
}
