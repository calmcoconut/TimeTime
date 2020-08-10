package com.example.timetime.ui.settingsSummary;

import android.content.Context;
import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;

public class AdvanceSettings extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        Context context = getPreferenceManager().getContext();
        PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(context);

        SwitchPreferenceCompat notificationPreference = new SwitchPreferenceCompat(context);
        notificationPreference.setKey("pushNotifications");
        notificationPreference.setTitle("Enable Push Notifications");

        screen.addPreference(notificationPreference);


        setPreferenceScreen(screen);
    }

    public AdvanceSettings() {
    }
}
