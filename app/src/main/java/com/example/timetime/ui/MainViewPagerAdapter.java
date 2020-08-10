package com.example.timetime.ui;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.example.timetime.R;
import com.example.timetime.ui.activitySummary.ActivityFragment;
import com.example.timetime.ui.categorysummary.HomeCategoryFragment;
import com.example.timetime.ui.helpSummary.SettingsFragment;
import com.example.timetime.ui.homesummary.HomeFragment;
import com.example.timetime.ui.statsSummary.StatsFragment;

import java.util.ArrayList;
import java.util.List;


public class MainViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<String> fragmentList;
    private String HOME, CATEGORIES, ACTIVITIES, STATS, SETTINGS;

    public MainViewPagerAdapter(@NonNull Context context,@NonNull FragmentManager fragmentManager, int behavior) {
        super(fragmentManager,behavior);
        this.HOME = context.getString(R.string.tab_title_home);
        this.CATEGORIES = context.getString(R.string.tab_title_categories);
        this.ACTIVITIES = context.getString(R.string.tab_title_activities);
        this.STATS = "stats";
        this.SETTINGS = "settings";

        this.fragmentList = new ArrayList<>();
        this.fragmentList.add(this.HOME);
        this.fragmentList.add(this.CATEGORIES);
        this.fragmentList.add(this.ACTIVITIES);
        this.fragmentList.add(this.STATS);
        this.fragmentList.add(this.SETTINGS);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HomeFragment.newInstance();
            case 1:
                return ActivityFragment.newInstance();
            case 2:
                return HomeCategoryFragment.newInstance();
            case 3:
                return StatsFragment.newInstance();
            case 4:
                return SettingsFragment.newInstance();
            default:
                return HomeFragment.newInstance();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return (CharSequence) this.fragmentList.get(position).toString();
    }

    @Override
    public int getCount() {
        return this.fragmentList.size();
    }
}
