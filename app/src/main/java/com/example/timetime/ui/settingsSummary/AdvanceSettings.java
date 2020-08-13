package com.example.timetime.ui.settingsSummary;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import androidx.preference.*;

public class AdvanceSettings extends PreferenceFragmentCompat {
    Context context;
    SwitchPreferenceCompat pushNotification;
    EditTextPreference pushNotificationInterval;
    SwitchPreferenceCompat lockScreenNotification;
    EditTextPreference lockScreenNotificationInterval;
    ListPreference timeFormat;
    Preference exportDatabase;
    EditTextPreference deleteDatabase;

    PreferenceScreen screen;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        context = getPreferenceManager().getContext();
        screen = getPreferenceManager().createPreferenceScreen(context);

        setNotificationCategory();
        setOtherCategory();

        setPreferenceScreen(screen);
        setDependencies();
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
        lockScreenNotificationInterval.setDefaultValue("30");
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
        timeFormat.setSummary("Configure to use a 12 hour format (1:00 PM) or a 24 hour format (13:00)");
        timeFormat.setDialogTitle("Choose a time format");
        timeFormat.setDefaultValue("24");
        otherCategory.addPreference(timeFormat);

        exportDatabase = new Preference(context);
        exportDatabase.setKey("export_data");
        exportDatabase.setTitle("Export database");
        exportDatabase.setSummary("export database to a local file for external use");
        otherCategory.addPreference(exportDatabase);

        deleteDatabase = new EditTextPreference(context);
        deleteDatabase.setKey("delete_data");
        deleteDatabase.setTitle("Delete database");
        deleteDatabase.setDialogTitle("TYPE DELETE TO CONFIRM");

        otherCategory.addPreference(deleteDatabase);
    }

    public AdvanceSettings() {
    }
}
