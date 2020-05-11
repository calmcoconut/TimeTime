package com.example.timetime;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import com.example.timetime.ui.MainViewPagerAdapter;
import com.example.timetime.ui.homesummary.LogTimeToActivity;
import com.example.timetime.viewmodels.CategoryViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    private CategoryViewModel mCategoryViewModel; // used to make sure data updates when db changes
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
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            public void onTabSelected(@NotNull TabLayout.Tab tab) {
                super.onTabSelected(tab);
                int position = tab.getPosition();
                makeToast(position);
            }
        });
        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new MainViewPagerAdapter(this,fragmentManager);
        viewPager.setAdapter(adapter);
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

    private void makeToast(int pos) {
        switch (pos) {
            case 1:
                break;
            case 2:
                break;
            default:
                setDefaultFabAction();
                break;
        }
    }
}
