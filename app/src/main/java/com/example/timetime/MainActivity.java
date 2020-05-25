package com.example.timetime;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.example.timetime.notifications.NotificationBroadcastReceiver;
import com.example.timetime.ui.MainViewPagerAdapter;
import com.example.timetime.ui.activitySummary.CreateActivityActivity;
import com.example.timetime.ui.categorysummary.CreateCategoryActivity;
import com.example.timetime.ui.homesummary.LogTimeToActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    public static final boolean IS_DEV_DB = true;

    private MainViewPagerAdapter adapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private int tabNumber;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        fab = findViewById(R.id.main_fab);

        // tab logic
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            public void onTabSelected(@NotNull TabLayout.Tab tab) {
                super.onTabSelected(tab);
                tabNumber = tab.getPosition();
                setFabForTab(tabNumber);
            }
        });
        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new MainViewPagerAdapter(this, fragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        // if started by another activity, update tab
        viewPager.setCurrentItem(getIntent().getIntExtra("tab", 0));
        NotificationBroadcastReceiver.TimeTimeNotificationSetUp.createRepeatingNotification(this);
    }

    private void setDefaultFabAction() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LogTimeToActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setLaunchCreateActivityFab() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateActivityActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setLaunchCreateCategoryFab() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateCategoryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setFabForTab(int pos) {
        switch (pos) {
            case 1:
                setLaunchCreateActivityFab();
                break;
            case 2:
                setLaunchCreateCategoryFab();
                break;
            default:
                setDefaultFabAction();
                break;
        }
    }
}
