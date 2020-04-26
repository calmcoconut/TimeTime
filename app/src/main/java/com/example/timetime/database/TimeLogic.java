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

    public Integer minutesSinceLastTimeStamp (Long databaseValue) {
        final Instant databaseInstant = convertLongToInstant(databaseValue);
        final Instant nowInstant = Instant.now().truncatedTo(ChronoUnit.MINUTES);
        Long r = ChronoUnit.MINUTES.between(databaseInstant,nowInstant);
        Integer result = r.intValue();
        return result;
    }
//    public Long getTime();
//    public String getTimeHuman();
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