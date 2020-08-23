//package com.example.timetime.notifications;
//
//import android.app.Notification;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.hardware.display.DisplayManager;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.Display;
//import androidx.annotation.NonNull;
//import androidx.core.app.NotificationCompat;
//import androidx.core.app.NotificationManagerCompat;
//import com.example.timetime.AppStart;
//import com.example.timetime.R;
//import com.example.timetime.ui.homesummary.LogTimeToActivity;
//import com.example.timetime.utils.DevProperties;
//import org.jetbrains.annotations.NotNull;
//
//public class ScreenOnOffWorker extends Worker {
//    public ScreenOnOffWorker(@NonNull @NotNull Context context, @NonNull @NotNull WorkerParameters workerParams) {
//        super(context, workerParams);
//    }
//
//    Context applicationContext = getApplicationContext();
//
//    @NonNull
//    @NotNull
//    @Override
//    public Result doWork() {
//        Intent logTimeToActivity = new Intent(getApplicationContext(), LogTimeToActivity.class);
//        logTimeToActivity.putExtra(DevProperties.IS_NOTIFICATION_EXTRA_KEY, true);
//        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, logTimeToActivity, 0);
//
//        Bundle bundle = new Bundle();
//        bundle.putBoolean(DevProperties.IS_NOTIFICATION_EXTRA_KEY, true);
//
//        Notification lockScreenNotification =
//                new NotificationCompat.Builder(getApplicationContext(), AppStart.CHANNEL_ID_LOCKSCREEN)
//                .setSmallIcon(R.drawable.ic_access_time_black_24dp)
//                .setContentTitle("Log your time")
//                .setContentText("What have you been up to?")
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setNotificationSilent()
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setCategory(NotificationCompat.CATEGORY_REMINDER)
//                .setAutoCancel(true)
//                .setFullScreenIntent(pendingIntent, true)
//                .build();
//        DisplayManager dm = (DisplayManager) applicationContext.getSystemService(Context.DISPLAY_SERVICE);
//        for (Display display : dm.getDisplays()) {
//            if (display.getState() != Display.STATE_ON) {
//                Log.d("CustomWorkerClass", "ScreenOff");
//                LockScreenBroadCastNotificationReceiver.initNotification(applicationContext);
//                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
//                notificationManager.notify(LockScreenBroadCastNotificationReceiver.NOTIFICATION_LOCKSCREEN_REPEATING_ID, lockScreenNotification);
//                return Result.success();
//            }
//            Log.d("CustomWorkerClass", "ScreenOn");
//        }
//        return Result.retry();
//    }
//}
