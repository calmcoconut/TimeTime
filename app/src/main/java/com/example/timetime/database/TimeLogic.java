package com.example.timetime.database;

/*
* Provide a way to get time in both computer and human readable formats
* Provide a way to calculate elapsed time in both computer and human readable formats
* Make as thread safe as possible
 */

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class TimeLogic {
    public static final int MINUTES_DAY = 1440;
    public static final int MINUTES_HOUR = 60;


    public Long getDateTimeForDatabaseStorage () {
        Instant timeNow = getCurrentInstant();
        return convertInstantToLong(timeNow);
    }

    public ZonedDateTime getDateTimeFromDatabaseLong (Long databaseLong) {
        Instant timeNow = convertLongToInstant(databaseLong);
        return ZonedDateTime.ofInstant(timeNow, ZoneId.systemDefault());
    }

    public Instant getCurrentInstant () {
        // Truncated to Minutes
        return Instant.now().truncatedTo(ChronoUnit.MINUTES);
    }
    public ZonedDateTime getCurrentZonedDateTime () {
        return ZonedDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    }

    public Long convertInstantToLong (Instant instant) {
        return instant.truncatedTo(ChronoUnit.SECONDS).getEpochSecond();
    }

    public Instant convertLongToInstant (Long longInstant) {
        return Instant.ofEpochSecond(longInstant);
    }

    public Integer minutesSinceLastTimeStamp (Long databaseValue) {
        final Instant databaseInstant = convertLongToInstant(databaseValue);
        final Instant nowInstant = Instant.now().truncatedTo(ChronoUnit.MINUTES);
        Long r = ChronoUnit.MINUTES.between(databaseInstant,nowInstant);
        Integer result = r.intValue();
        return result;
    }

    public Integer minutesBetweenTwoTimeStamps (Long databaseValueOlder, Long databaseValueNewer) {
        final Instant databaseInstantOld = convertLongToInstant(databaseValueOlder);
        final Instant databaseInstantNew = convertLongToInstant(databaseValueNewer);
        Long r = ChronoUnit.MINUTES.between(databaseInstantOld,databaseInstantNew);
        Integer result = r.intValue();
        return result;
    }

    public String formatedTimeBetweenTwoTimeSpans (Long databaseValueOlder, Long databaseValueNewer) {
        String days = "";
        String hours = "";
        String minutes = "";
        Integer totalMinutes = minutesBetweenTwoTimeStamps(databaseValueOlder,databaseValueNewer);
        if ((totalMinutes / 1440) > 0) {
            days = String.valueOf(totalMinutes/MINUTES_DAY) + "D ";
            totalMinutes = totalMinutes % MINUTES_DAY;
        }
        if ((totalMinutes / MINUTES_HOUR) > 0) {
            hours = String.valueOf(totalMinutes/MINUTES_HOUR) + "h ";
            totalMinutes = totalMinutes % MINUTES_HOUR;
        }
        if (totalMinutes > 0) {
            minutes = String.valueOf(totalMinutes) + "min";
        }
        return days + hours + minutes;
    }

    public ZoneId getZoneId () {
        return ZoneId.systemDefault();
    }

    public String getLocalTimeFromDatabase (Long dataBaseValue) {
        Instant instant = convertLongToInstant(dataBaseValue);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant,getZoneId());
        return (String) zonedDateTime.format(DateTimeFormatter.ofPattern("HH:mm a"));
    }

    public static TimeLogic newInstance () {
        return new TimeLogic();
    }
}


//    ZonedDateTime zonedDateTime = ZonedDateTime.now();
//    String timeNow = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).toString();
//
//    Instant fromZDT = zonedDateTime.toInstant().truncatedTo(ChronoUnit.MINUTES);  //.truncatedTo(ChronoUnit.MINUTES);
//    String timeFromInstant = fromZDT.atZone(ZoneId.of("Asia/Tokyo")).format(DateTimeFormatter.ofPattern("yyyy-MM" +
//            "-dd HH:mm")).toString();
//
//    Long longFromInstant = (Long) fromZDT.getEpochSecond();
//    Instant instantFromLong = Instant.ofEpochSecond(longFromInstant);
//    String timeFromInstant2 = instantFromLong.atZone(ZoneId.of("America/Chicago")).format(DateTimeFormatter.ofPattern(
//            "yyyy-MM" +
//                    "-dd HH:mm:ss:nn")).toString();
//
//        System.out.println("TESTING DATE AND TIME current date and time " + timeNow);
//                System.out.println("TESTING DATE AND TIME current date and time " + timeFromInstant);
//                System.out.println("TESTING DATE AND TIME current date and time " + longFromInstant);
//                System.out.println("TESTING DATE AND TIME current date and time final " + timeFromInstant2);