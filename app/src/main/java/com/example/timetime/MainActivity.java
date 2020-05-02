package com.example.timetime;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import com.example.timetime.ui.MainViewPagerAdapter;
import com.example.timetime.ui.homesummary.TimeLogActivity;
import com.example.timetime.viewmodels.CategoryViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private CategoryViewModel mCategoryViewModel; // used to make sure data updates when db changes
    private MainViewPagerAdapter adapter;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        tabLayout.setupWithViewPager(viewPager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new MainViewPagerAdapter(this,fragmentManager);

        viewPager.setAdapter(adapter);

        fab = findViewById(R.id.main_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TimeLogActivity.class);
                startActivity(intent);
            }
        });
    }
}
