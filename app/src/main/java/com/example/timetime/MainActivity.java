package com.example.timetime;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FirstDatabase test = new FirstDatabase();

        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        String timeNow = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).toString();

        Instant fromZDT = zonedDateTime.toInstant().truncatedTo(ChronoUnit.MINUTES);  //.truncatedTo(ChronoUnit.MINUTES);
        String timeFromInstant = fromZDT.atZone(ZoneId.of("Asia/Tokyo")).format(DateTimeFormatter.ofPattern("yyyy-MM" +
                "-dd HH:mm")).toString();

        Long longFromInstant = (Long) fromZDT.getEpochSecond();
        Instant instantFromLong = Instant.ofEpochSecond(longFromInstant);
        String timeFromInstant2 = instantFromLong.atZone(ZoneId.of("America/Chicago")).format(DateTimeFormatter.ofPattern(
                "yyyy-MM" +
                "-dd HH:mm:ss:nn")).toString();

        System.out.println("TESTING DATE AND TIME current date and time " + timeNow);
        System.out.println("TESTING DATE AND TIME current date and time " + timeFromInstant);
        System.out.println("TESTING DATE AND TIME current date and time " + longFromInstant);
        System.out.println("TESTING DATE AND TIME current date and time final " + timeFromInstant2);
    }
}
