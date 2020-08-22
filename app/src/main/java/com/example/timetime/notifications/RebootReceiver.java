package com.example.timetime.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.example.timetime.utils.DevProperties;

public class RebootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            if (DevProperties.IS_LOCKSCREEN_NOTIFICATION_ENABLED) {
                LockScreenNotification.createRepeatingLockScreenNotification(context);
            }
            if (DevProperties.IS_PUSH_NOTIFICATION_ENABLED) {
                PushNotification.createRepeatingPushNotification(context);
            }
        }
    }
}
