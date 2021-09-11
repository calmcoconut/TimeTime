package com.example.timetime.database;

/*
 * Provide a way to get time in both computer and human readable formats
 * Provide a way to calculate elapsed time in both computer and human readable formats
 * Make as thread safe as possible
 */

import android.util.Log;
import androidx.annotation.NonNull;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;

public class TimeLogic {
    public static final int MINUTES_DAY = 1440;
    public static final int MINUTES_HOUR = 60;


    public Long getCurrentDateTimeForDatabaseStorage() {
        Instant timeNow = getCurrentInstant();
        return convertInstantToLong(timeNow);
    }

    public ZonedDateTime getZonedDateTimeFromDatabaseLong(Long databaseLong) {
        Instant timeNow = convertLongToInstant(databaseLong);
        return ZonedDateTime.ofInstant(timeNow, ZoneId.systemDefault());
    }

    public Instant getCurrentInstant() {
        // Truncated to Minutes
        return Instant.now().truncatedTo(ChronoUnit.MINUTES);
    }

    public ZonedDateTime getCurrentZonedDateTime() {
        return ZonedDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    }

    public Long convertInstantToLong(Instant instant) {
        return instant.truncatedTo(ChronoUnit.SECONDS).getEpochSecond();
    }

    public Instant convertLongToInstant(Long longInstant) {
        return Instant.ofEpochSecond(longInstant);
    }

    public Integer getMinutesBetweenNowAndLastDatabaseTime(Long databaseValue) {
        final Instant databaseInstant = convertLongToInstant(databaseValue);
        final Instant nowInstant = Instant.now().truncatedTo(ChronoUnit.MINUTES);
        long r = ChronoUnit.MINUTES.between(databaseInstant, nowInstant);
        return (int) r;
    }

    public Integer getMinutesBetweenTwoTimeStamps(Long databaseValueOlder, Long databaseValueNewer) {
        final Instant databaseInstantOld = convertLongToInstant(databaseValueOlder);
        final Instant databaseInstantNew = convertLongToInstant(databaseValueNewer);
        long r = ChronoUnit.MINUTES.between(databaseInstantOld, databaseInstantNew);
        return (int) r;
    }

    public String getHumanFormattedTimeBetweenTwoTimeSpans(Long databaseValueOlder, Long databaseValueNewer) {
        String days = "";
        String hours = "";
        String minutes = "";
        Integer totalMinutes = getMinutesBetweenTwoTimeStamps(databaseValueOlder, databaseValueNewer);
        if (totalMinutes < 1) {
            minutes = "0 min";
        }
        else {
            if ((totalMinutes / 1440) > 0) {
                days = (totalMinutes / MINUTES_DAY) + "D ";
                totalMinutes = totalMinutes % MINUTES_DAY;
            }
            if ((totalMinutes / MINUTES_HOUR) > 0) {
                hours = (totalMinutes / MINUTES_HOUR) + "h ";
                totalMinutes = totalMinutes % MINUTES_HOUR;
            }
            if (totalMinutes > 0) {
                minutes = totalMinutes + "min";
            }
        }
        return days + hours + minutes;
    }

    public String getHumanFormattedTimeBetweenDbValueAndNow(@NonNull Long databaseValue) {
        String days = "";
        String hours = "";
        String minutes = "";
        Integer totalMinutes = getMinutesBetweenTwoTimeStamps(databaseValue, convertInstantToLong(getCurrentInstant()));
        if ((totalMinutes / 1440) > 0) {
            days = (totalMinutes / MINUTES_DAY) + "D ";
            totalMinutes = totalMinutes % MINUTES_DAY;
        }
        if ((totalMinutes / MINUTES_HOUR) > 0) {
            hours = (totalMinutes / MINUTES_HOUR) + "h ";
            totalMinutes = totalMinutes % MINUTES_HOUR;
        }
        if (totalMinutes > 0) {
            minutes = totalMinutes + "min";
        }
        else if (totalMinutes == 0) {
            minutes = "0MIN";
        }
        return days + hours + minutes;
    }

    public ZoneId getZoneId() {
        return ZoneId.systemDefault();
    }

    public String getLocalTimeFromDatabase(Long dataBaseValue) {
        Instant instant = convertLongToInstant(dataBaseValue);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, getZoneId());
        return zonedDateTime.format(DateTimeFormatter.ofPattern("HH:mm a"));
    }

    public String getLocalTimeFromDatabase12HourFormat(Long dataBaseValue) {
        Instant instant = convertLongToInstant(dataBaseValue);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, getZoneId());
        return zonedDateTime.format(DateTimeFormatter.ofPattern("h:mm a"));
    }


    public String getHumanFormattedLongDateFromDatabase(Long databaseLong) {
        String humanLongDate;
        Instant instant = convertLongToInstant(databaseLong);
        ZonedDateTime today = ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, getZoneId()).truncatedTo(ChronoUnit.DAYS);
        Log.d("TODAY and VALUE", today + " value: " + zonedDateTime);

        if (zonedDateTime.equals(today)) {
            humanLongDate = "Today";
        }
        else if (zonedDateTime.equals(today.minusDays(1))) {
            humanLongDate = "Yesterday";
        }
        else {
            humanLongDate = zonedDateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
        }
        return humanLongDate;
    }

    public Long addDayToLong(Long databaseValue) {
        long hours = 23;
        long mins = 59;
        // assure that that value is already at hour 0 and min 0
        ZonedDateTime zonedDateTime =
                ZonedDateTime.ofInstant(convertLongToInstant(databaseValue), getZoneId()).truncatedTo(ChronoUnit.DAYS).plusHours(hours).plusMinutes(mins);
        return zonedDateTime.truncatedTo(ChronoUnit.SECONDS).toEpochSecond();
    }

    public Long getLongForDataBaseFromInts(int year, int month, int day) {
        return convertInstantToLong(getInstantForDataBaseFromInts(year, month, day));
    }

    public Instant getInstantForDataBaseFromInts(int year, int month, int day) {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(year, month, day, 0, 0, 0, 0, getZoneId());
        return zonedDateTime.toInstant();
    }

    public int getIntYear() {
        return getCurrentZonedDateTime().getYear();
    }

    public int getIntMonth() {
        return getCurrentZonedDateTime().getMonthValue();
    }

    public int getIntDayOfMonth() {
        return getCurrentZonedDateTime().getDayOfMonth();
    }

    public static TimeLogic newInstance() {
        return new TimeLogic();
    }

}