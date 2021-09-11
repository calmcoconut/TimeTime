package com.example.timetime.notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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

public class PushNotification {
    public static final String TAG_PUSH_NOTIFY = "push_notification";
    public static final String WORK_TAG_PUSH_NOTIFY = "work_push_notification";
    public static final int NOTIFICATION_PUSH_REPEATING_ID = 1;

    public static void createRepeatingPushNotification(Context context) {
        initWorkScheduleNotification(context, null);
    }

    public static Notification buildNotification(Context context) {
        Intent logTimeToActivity = new Intent(context, LogTimeToActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, logTimeToActivity, 0);

        return new NotificationCompat.Builder(context, AppStart.CHANNEL_ID_PUSH)
                .setSmallIcon(R.drawable.ic_access_time_black_24dp)
                .setContentTitle("Log your time")
                .setContentText("What have you been up to?")
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_EVENT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
    }

    private static void initWorkScheduleNotification(Context context, @Nullable Integer intervalMinutes) {
        WorkManager workManager = WorkManager.getInstance(context);
        Constraints constraints = new Constraints.Builder()
                .setRequiresDeviceIdle(false)
                .build();

        int interval = intervalMinutes == null ?
                Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(context).getString(
                        DevProperties.PUSH_NOTIFICATION_INTERVAL_SETTINGS_KEY,
                        String.valueOf(DevProperties.INTERVAL_PUSH_NOTIFICATION_MINUTES))) : intervalMinutes;
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(PushNotificationSchedulerWorker.class,
                interval, TimeUnit.MINUTES)
                .setInitialDelay(interval, TimeUnit.MINUTES)
                .addTag(TAG_PUSH_NOTIFY)
                .setConstraints(constraints)
                .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                .build();

        workManager.enqueueUniquePeriodicWork(WORK_TAG_PUSH_NOTIFY,
                ExistingPeriodicWorkPolicy.KEEP,
                periodicWorkRequest);

        Log.d("isNotification", "push interval is " + interval);
    }

    public static void rescheduleNotification(Context context, Integer intervalMinutes) {
        endNotificationWork(context);
        initWorkScheduleNotification(context, intervalMinutes);
    }

    public static void endNotificationWork(Context context) {
        WorkManager workManager = WorkManager.getInstance(context);
        workManager.cancelAllWorkByTag(TAG_PUSH_NOTIFY);
    }

    public static class PushNotificationSchedulerWorker extends Worker {

        public PushNotificationSchedulerWorker(@NonNull @NotNull Context context,
                                               @NonNull @NotNull WorkerParameters workerParams) {
            super(context, workerParams);
        }

        @NonNull
        @NotNull
        @Override
        public Result doWork() {
            Context context = getApplicationContext();
            triggerPushNotification(context);
            return Result.success();
        }

        private void triggerPushNotification(Context context) {
            Log.d("isNotification", "push notification triggered");
            Notification lockScreenNotification = PushNotification.buildNotification(context);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(NOTIFICATION_PUSH_REPEATING_ID, lockScreenNotification);
        }
    }
}
