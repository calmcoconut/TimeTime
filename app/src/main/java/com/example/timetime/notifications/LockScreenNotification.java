package com.example.timetime.notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.preference.PreferenceManager;
import androidx.work.*;
import com.example.timetime.AppStart;
import com.example.timetime.R;
import com.example.timetime.ui.homesummary.LogTimeToActivity;
import com.example.timetime.utils.DevProperties;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class LockScreenNotification {

    public static final String TAG_LOCKSCREEN_NOTIFY = "lock_screen_notification";
    public static final String WORK_TAG_LOCKSCREEN_NOTIFY = "work_lock_screen_notification";
    public static final int NOTIFICATION_LOCKSCREEN_REPEATING_ID = 2;

    public static void createRepeatingLockScreenNotification(Context context) {
        initWorkScheduleNotification(context, null);
    }

    private static void initWorkScheduleNotification(Context context, @Nullable Integer intervalMinutes) {
        Constraints constraints = new Constraints.Builder()
                .setRequiresDeviceIdle(false) // possible way of only displaying notification on lockscreen
                .build();

        int interval = intervalMinutes == null ?
                Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(context).getString(
                        DevProperties.LOCK_SCREEN_NOTIFICATION_INTERVAL_SETTINGS_KEY, String.valueOf(DevProperties.INTERVAL_PUSH_NOTIFICATION_MINUTES)))
                : intervalMinutes;
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(LockScreenNotificationSchedulerWorker.class,
                interval, TimeUnit.MINUTES)
                .setInitialDelay(interval, TimeUnit.MINUTES)
                .addTag(TAG_LOCKSCREEN_NOTIFY)
                .setConstraints(constraints)
                .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                .build();

        WorkManager workManager = WorkManager.getInstance(context);
        workManager.enqueueUniquePeriodicWork(WORK_TAG_LOCKSCREEN_NOTIFY,
                ExistingPeriodicWorkPolicy.KEEP,
                periodicWorkRequest);

        Log.d("isNotification", "lock screen interval is " + interval);
    }

    public static void rescheduleNotification(Context context, Integer intervalMinutes) {
        endNotificationWork(context);
        initWorkScheduleNotification(context, intervalMinutes);
    }

    public static void endNotificationWork(Context context) {
        WorkManager workManager = WorkManager.getInstance(context);
        workManager.cancelAllWorkByTag(TAG_LOCKSCREEN_NOTIFY);
    }

    private static Notification buildNotification(Context context) {
        Intent logTimeToActivity = new Intent(context, LogTimeToActivity.class);
        logTimeToActivity.putExtra(DevProperties.IS_NOTIFICATION_EXTRA_KEY, true);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, logTimeToActivity, 0);

        Bundle bundle = new Bundle();
        bundle.putBoolean(DevProperties.IS_NOTIFICATION_EXTRA_KEY, true);

        return new NotificationCompat.Builder(context, AppStart.CHANNEL_ID_LOCKSCREEN)
                .setSmallIcon(R.drawable.ic_access_time_black_24dp)
                .setContentTitle("Log your time")
                .setContentText("What have you been up to?")
                .setDefaults(Notification.DEFAULT_ALL)
                .setNotificationSilent()
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setAutoCancel(true)
                .setFullScreenIntent(pendingIntent, true)
                .build();
    }

    public static class LockScreenNotificationSchedulerWorker extends Worker {

        public LockScreenNotificationSchedulerWorker(@NonNull @NotNull Context context,
                                                     @NonNull @NotNull WorkerParameters workerParams) {
            super(context, workerParams);
        }

        @NonNull
        @NotNull
        @Override
        public Result doWork() {
            Context context = getApplicationContext();
            triggerLockScreenNotification(context);

            return Result.success();
        }

        private void triggerLockScreenNotification(Context context) {
            Log.d("isNotification", "lock screen notification triggered");
            Notification pushNotification = LockScreenNotification.buildNotification(context);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(NOTIFICATION_LOCKSCREEN_REPEATING_ID, pushNotification);
        }
    }
}
