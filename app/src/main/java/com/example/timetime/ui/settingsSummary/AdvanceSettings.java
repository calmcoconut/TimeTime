package com.example.timetime.ui.settingsSummary;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import androidx.preference.*;

public class AdvanceSettings extends PreferenceFragmentCompat {
    Context context;
    PreferenceScreen screen;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        context = getPreferenceManager().getContext();
        screen = getPreferenceManager().createPreferenceScreen(context);

        setNotificationSettings();
        setOtherSettings();

        setPreferenceScreen(screen);
    }

    private void setNotificationSettings() {
        PreferenceCategory notificationCategory = new PreferenceCategory(context);
        notificationCategory.setKey("notification_category");
        notificationCategory.setTitle("Notifications");
        screen.addPreference(notificationCategory);

        SwitchPreferenceCompat pushNotification = new SwitchPreferenceCompat(context);
        pushNotification.setKey("push_notifications");
        pushNotification.setTitle("Enable push notifications");
        pushNotification.setChecked(true);
        notificationCategory.addPreference(pushNotification);

        EditTextPreference pushNotificationInterval = new EditTextPreference(context);
        pushNotificationInterval.setKey("push_notifications_interval");
        pushNotificationInterval.setTitle("Push notification frequency");
        pushNotificationInterval.setSummaryProvider(provider -> pushNotificationInterval.getText() + " minutes");
        pushNotificationInterval.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER));
        pushNotificationInterval.setDefaultValue("30");
        notificationCategory.addPreference(pushNotificationInterval);

        SwitchPreferenceCompat lockScreenNotification = new SwitchPreferenceCompat(context);
        lockScreenNotification.setKey("lock_screen_notifications");
        lockScreenNotification.setTitle("Enable Lock Screen notifications");
        notificationCategory.addPreference(lockScreenNotification);

        EditTextPreference lockScreenNotificationInterval = new EditTextPreference(context);
        lockScreenNotificationInterval.setKey("lock_screen_notifications_interval");
        lockScreenNotificationInterval.setTitle("Lock screen notification frequency");
        lockScreenNotificationInterval.setSummaryProvider(provider -> lockScreenNotificationInterval.getText() + " minutes");
        lockScreenNotificationInterval.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER));
        lockScreenNotificationInterval.setDefaultValue("30");
        notificationCategory.addPreference(lockScreenNotificationInterval);
    }

    private void setOtherSettings() {
        PreferenceCategory otherCategory = new PreferenceCategory(context);
        otherCategory.setKey("other_category");
        otherCategory.setTitle("Other");
        screen.addPreference(otherCategory);

        Preference timeFormat = new Preference(context);

        Preference exportDatabase = new Preference(context);
        exportDatabase.setKey("export_data");
        exportDatabase.setTitle("Export Database");
        exportDatabase.setSummary("export database to a local file for use externally (excel, backup, etc.)");

        Preference deleteDatabase = new Preference(context);
        deleteDatabase.setKey("delete_data");
        deleteDatabase.setTitle("Delete Database");
    }

    public AdvanceSettings() {
    }
}
