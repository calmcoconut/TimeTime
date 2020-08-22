package com.example.timetime.notifications;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.util.Log;
import android.view.Display;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import org.jetbrains.annotations.NotNull;

public class ScreenOnOffWorker extends Worker {
    public ScreenOnOffWorker(@NonNull @NotNull Context context, @NonNull @NotNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    Context applicationContext = getApplicationContext();

    @NonNull
    @NotNull
    @Override
    public Result doWork() {
        DisplayManager dm = (DisplayManager) applicationContext.getSystemService(Context.DISPLAY_SERVICE);
        for (Display display : dm.getDisplays()) {
            if (display.getState() != Display.STATE_ON) {
                Log.d("CustomWorkerClass", "ScreenOff");
                LockScreenBroadCastNotificationReceiver.initNotification(applicationContext);
                return Result.success();
            }
            Log.d("CustomWorkerClass", "ScreenOn");
        }
        return Result.retry();
    }
}
