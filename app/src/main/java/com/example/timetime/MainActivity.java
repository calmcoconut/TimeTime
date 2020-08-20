package com.example.timetime;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import com.example.timetime.notifications.LockScreenNotification;
import com.example.timetime.notifications.PushNotification;
import com.example.timetime.ui.MainViewPagerAdapter;
import com.example.timetime.ui.activitySummary.CreateActivityActivity;
import com.example.timetime.ui.categorysummary.CreateCategoryActivity;
import com.example.timetime.ui.homesummary.LogTimeToActivity;
import com.example.timetime.ui.settingsSummary.AdvanceSettings;
import com.example.timetime.utils.DevProperties;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    private Drawable addDrawable;
    private Drawable cogDrawable;
    private FragmentManager fragmentManager;
    private MainViewPagerAdapter adapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private static CoordinatorLayout mainView;
    private int tabNumber;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        fab = findViewById(R.id.main_fab);
        mainView = findViewById(R.id.activity_main_view);

        addDrawable = ContextCompat.getDrawable(this, R.drawable.ic_add_black_24dp);
        cogDrawable = ContextCompat.getDrawable(this, R.drawable.ic_settings_black_24dp);

        // tab logic
        initTabPageViewer();
        fragmentManager = getSupportFragmentManager();
        adapter = new MainViewPagerAdapter(this, fragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);


        // if started by another activity, update tab
        viewPager.setCurrentItem(getIntent().getIntExtra("tab", 0));

        // init notification
        if (DevProperties.IS_PUSH_NOTIFICATION_ENABLED) {
            PushNotification.createRepeatingPushNotification(this);
        }

        if (DevProperties.IS_LOCKSCREEN_NOTIFICATION_ENABLED) {
            LockScreenNotification.createRepeatingLockScreenNotification(this);
        }
    }

    private void initTabPageViewer() {
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            public void onTabSelected(@NotNull TabLayout.Tab tab) {
                super.onTabSelected(tab);
                tabNumber = tab.getPosition();
                setFabForTab(tabNumber);
            }
        });
    }

    private void setDefaultFabAction() {
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LogTimeToActivity.class);
            startActivity(intent);
        });
    }

    private void setLaunchCreateActivityFab() {
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateActivityActivity.class);
            startActivity(intent);
        });
    }

    private void setLaunchCreateCategoryFab() {
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateCategoryActivity.class);
            startActivity(intent);
        });
    }

    private void setLaunchAdvanceSettingsFab() {
        fab.setOnClickListener(v -> {
            mainView.setVisibility(View.GONE);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.activity_root_view, new AdvanceSettings());
            transaction
                    .addToBackStack(null)
                    .commit();
        });
    }


    private void setFabForTab(int pos) {
        setFabIcon(pos);
        switch (pos) {
            case 1:
                setLaunchCreateActivityFab();
                break;
            case 2:
                setLaunchCreateCategoryFab();
                break;
            case 4:
                setLaunchAdvanceSettingsFab();
                break;
            default:
                setDefaultFabAction();
                break;
        }
    }

    private void setFabIcon(int pos) {
        if (pos != 4 && !fab.getDrawable().equals(addDrawable)) {
            fab.setImageDrawable(addDrawable);
        }
        if (pos == 4 && !fab.getDrawable().equals(cogDrawable)) {
            fab.setImageDrawable(cogDrawable);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setViewVisibleCallBack();
    }

    public static void setViewVisibleCallBack() {
        mainView.setVisibility(View.VISIBLE);
    }
}
