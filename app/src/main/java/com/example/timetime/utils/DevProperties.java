package com.example.timetime.utils;

import com.example.timetime.R;

public final class DevProperties {
    public static final String WELCOME_TIME_LOG = "Welcome to TimeTime";
    public static final long WELCOME_TIME_LOG_ID = 1;
    public static final boolean IS_DEV_DB = false;
    public static final int INITIAL_ICON = R.drawable.icon_activity;
    public static final String TIME_FORMAT_SETTING_KEY = "time_format_24_12";
    public static boolean IS_24_HOUR_FORMAT = false; // default true

    public static String IS_NOTIFICATION_EXTRA_KEY = "isNotification";
    public static final String PUSH_NOTIFICATION_SETTINGS_KEY = "push_notification_setting";
    public static final String PUSH_NOTIFICATION_INTERVAL_SETTINGS_KEY = "push_notification_interval_setting";
    public static boolean IS_PUSH_NOTIFICATION_ENABLED = false; // default false
    public static int INTERVAL_PUSH_NOTIFICATION_MINUTES = 30; // default 30

    public static final String LOCK_SCREEN_NOTIFICATION_SETTINGS_KEY = "lock_screen_notification_setting";
    public static final String LOCK_SCREEN_NOTIFICATION_INTERVAL_SETTINGS_KEY =
            "lock_screen_notification_interval_setting";
    public static boolean IS_LOCKSCREEN_NOTIFICATION_ENABLED = false; // default false
    public static int INTERVAL_LOCKSCREEN_NOTIFICATION_MINUTES = 45; //default 45
}