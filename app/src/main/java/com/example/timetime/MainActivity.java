package com.example.timetime;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.timetime.database.entity.Activity;
import com.example.timetime.viewModels.ActivityViewModel;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityViewModel mActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivityViewModel = new ViewModelProvider(this).get(ActivityViewModel.class);

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

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        final CategoryListAdapter adapter = new CategoryListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        ActivityViewModel activityViewModel = new ActivityViewModel(getApplication());
        List<Activity> activityList = activityViewModel.getAllActivities().getValue();

        if (activityList != null) {
            for(Activity activity: activityList){
                System.out.println("TESTING DATABASE " + activity.getActivity());
            }
        }
    }
}
