package com.example.timetime.database;

/*
* Provide a way to get time in both computer and human readable formats
* Provide a way to calculate elapsed time in both computer and human readable formats
* Make as thread safe as possible
 */

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class TimeLogic {


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

//    public Long getTime();
//    public String getTimeHuman();
}
