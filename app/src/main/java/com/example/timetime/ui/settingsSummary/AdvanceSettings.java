package com.example.timetime.ui.settingsSummary;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.preference.*;
import com.example.timetime.database.AppRepository;
import com.example.timetime.database.database.AppDatabase;
import com.example.timetime.database.database.DatabaseExportTool;
import com.example.timetime.notifications.LockScreenNotification;
import com.example.timetime.notifications.PushNotification;
import com.example.timetime.utils.DevProperties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import static android.app.Activity.RESULT_OK;

public class AdvanceSettings extends PreferenceFragmentCompat {
    Context context;
    SwitchPreferenceCompat pushNotification;
    EditTextPreference pushNotificationInterval;
    SwitchPreferenceCompat lockScreenNotification;
    EditTextPreference lockScreenNotificationInterval;
    ListPreference timeFormat;
    Preference exportDatabase;
    ListPreference deleteDatabase;

    PreferenceScreen screen;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        context = getPreferenceManager().getContext();
        screen = getPreferenceManager().createPreferenceScreen(context);

        setNotificationCategory();
        setOtherCategory();

        setPreferenceScreen(screen);
        setDependencies();
        notificationOptionListeners();
        otherOptionListener();
    }

    private void setDependencies() {
        pushNotificationInterval.setDependency(pushNotification.getKey());
        lockScreenNotificationInterval.setDependency(lockScreenNotification.getKey());
    }

    private void setNotificationCategory() {
        PreferenceCategory notificationCategory = new PreferenceCategory(context);
        notificationCategory.setKey("notification_category");
        notificationCategory.setTitle("Notifications");
        screen.addPreference(notificationCategory);

        pushNotification = new SwitchPreferenceCompat(context);
        pushNotification.setKey("push_notification");
        pushNotification.setTitle("Enable push notifications");
        pushNotification.setChecked(true);
        notificationCategory.addPreference(pushNotification);

        pushNotificationInterval = new EditTextPreference(context);
        pushNotificationInterval.setKey("push_notifications_interval");
        pushNotificationInterval.setTitle("Push notification frequency");
        pushNotificationInterval.setSummaryProvider(provider -> pushNotificationInterval.getText() + " minutes");
        pushNotificationInterval.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER));
        pushNotificationInterval.setDialogTitle("Choose how often to be notified");
        pushNotificationInterval.setDefaultValue("30");
        notificationCategory.addPreference(pushNotificationInterval);

        lockScreenNotification = new SwitchPreferenceCompat(context);
        lockScreenNotification.setKey("lock_screen_notifications");
        lockScreenNotification.setTitle("Enable Lock Screen notifications");
        notificationCategory.addPreference(lockScreenNotification);

        lockScreenNotificationInterval = new EditTextPreference(context);
        lockScreenNotificationInterval.setKey("lock_screen_notifications_interval");
        lockScreenNotificationInterval.setTitle("Lock screen notification frequency");
        lockScreenNotificationInterval.setSummaryProvider(provider -> lockScreenNotificationInterval.getText() + " minutes");
        lockScreenNotificationInterval.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER));
        lockScreenNotificationInterval.setDialogTitle("Choose how often to be notified");
        lockScreenNotificationInterval.setDefaultValue("45");
        notificationCategory.addPreference(lockScreenNotificationInterval);
    }

    private void setOtherCategory() {
        PreferenceCategory otherCategory = new PreferenceCategory(context);
        otherCategory.setKey("other_category");
        otherCategory.setTitle("Other");
        screen.addPreference(otherCategory);

        timeFormat = new ListPreference(context);
        timeFormat.setKey("time_format");
        timeFormat.setTitle("Time format");
        timeFormat.setEntries(new CharSequence[]{"12 HOUR", "24 HOUR"});
        timeFormat.setEntryValues(new CharSequence[]{"12", "24"});
        timeFormat.setSummary("Configure to use a 12 hour format (1:00 PM) or a 24 hour format (13:00 PM)");
        timeFormat.setDialogTitle("Choose a time format");
        timeFormat.setDefaultValue("24");
        otherCategory.addPreference(timeFormat);

        exportDatabase = new Preference(context);
        exportDatabase.setKey("export_data");
        exportDatabase.setTitle("Export database");
        exportDatabase.setSummary("export database to a local file for external use");
        otherCategory.addPreference(exportDatabase);

        deleteDatabase = new ListPreference(context);
        deleteDatabase.setKey("delete_data");
        deleteDatabase.setTitle("Delete database");
        deleteDatabase.setEntries(new CharSequence[]{"CANCEL", "CONFIRM"});
        deleteDatabase.setEntryValues(new CharSequence[]{"0", "1"});
        deleteDatabase.setDialogTitle("Delete the database?");
        deleteDatabase.setDefaultValue("0");

        otherCategory.addPreference(deleteDatabase);
    }

    private void notificationOptionListeners() {
        pushNotification.setOnPreferenceChangeListener((preference, newValue) -> {
            PushNotification.pushNotificationEnabled(context, (boolean) newValue);
            return true;
        });

        pushNotificationInterval.setOnPreferenceChangeListener((preference, newValue) -> {
            try {
                PushNotification.setPushInterval(Long.parseLong(String.valueOf(newValue)));
            } catch (Exception e) {
                return false;
            }
            return true;
        });

        lockScreenNotification.setOnPreferenceChangeListener((preference, newValue) -> {
            LockScreenNotification.lockScreenNotificationEnabled(context, (boolean) newValue);
            return true;
        });

        lockScreenNotificationInterval.setOnPreferenceChangeListener((preference, newValue) -> {
            Log.d("isSetting", "changed lock screen interval");
            try {
                LockScreenNotification.setLockScreenInterval(Long.parseLong(String.valueOf(newValue)));
            } catch (Exception e) {
                return false;
            }
            return true;
        });
    }

    private void otherOptionListener() {
        timeFormat.setOnPreferenceChangeListener((preference, newValue) -> {
            DevProperties.IS_24_HOUR_FORMAT = newValue.equals("24");
            return true;
        });
        exportDatabase.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Log.d("isSetting", "export db");
                Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("text/csv");
                intent.putExtra(Intent.EXTRA_TITLE, DatabaseExportTool.exportName);
                startActivityForResult(intent, DatabaseExportTool.CREATE_FILE);
                return true;
            }
        });
        deleteDatabase.setOnPreferenceChangeListener((preference, newValue) -> {
            if (newValue.equals("1")) {
                Log.d("isSetting", "delete db");
                AppRepository.resetDatabase();
            }
            return false;
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DatabaseExportTool.CREATE_FILE) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                try {
                    OutputStream outputStream = getContext().getContentResolver().openOutputStream(uri);
                    AppDatabase.databaseWriteExecutor.execute(() ->
                            {
                                try {
                                    outputStream.write(DatabaseExportTool.exportDatabaseToCsv(AppDatabase.getDatabase(getContext())).getBytes());
                                    outputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                    );
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public AdvanceSettings() {
    }
}
