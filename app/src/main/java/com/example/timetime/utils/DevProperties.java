package com.example.timetime.utils;

public final class DevProperties {
    public static final String WELCOME_TIME_LOG = "Welcome to TimeTime";
    public static final long WELCOME_TIME_LOG_ID = 1;
    public static final boolean IS_DEV_DB = false;
    public static boolean IS_24_HOUR_FORMAT = true; // default true

    public static String IS_NOTIFICATION_EXTRA_KEY = "isNotification";
    public static boolean IS_PUSH_NOTIFICATION_ENABLED = true; // default true
    public static int INTERVAL_PUSH_NOTIFICATION_MINUTES = 30; // default 30

    public static boolean IS_LOCKSCREEN_NOTIFICATION_ENABLED = false; // default false
    public static int INTERVAL_LOCKSCREEN_NOTIFICATION_MINUTES = 45; //default 45
}