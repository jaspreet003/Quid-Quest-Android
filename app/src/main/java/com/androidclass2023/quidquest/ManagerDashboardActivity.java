package com.androidclass2023.quidquest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import adapters.DashboardViewPagerAdapter;

public class ManagerDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_dashboard);

        TabLayout tabLayout = findViewById(R.id.tabLayoutDashboard);
        ViewPager2 viewPager = findViewById(R.id.viewPagerDashboard);

        // Set the adapter
        viewPager.setAdapter(new DashboardViewPagerAdapter(this));

        // Link the TabLayout and the ViewPager2 together
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Category");
                            break;
                        case 1:
                            tab.setText("Department");
                            break;
                    }
                }).attach();
    }
}