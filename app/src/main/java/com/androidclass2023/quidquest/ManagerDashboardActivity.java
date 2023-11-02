package com.androidclass2023.quidquest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import adapters.DashboardViewPagerAdapter;

public class ManagerDashboardActivity extends AppCompatActivity implements View.OnClickListener {

   Button btnWeekly, btnMonthly, btnLastMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {

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

        initialize();
    }

    private void initialize() {
        // Configure the variables
        btnMonthly = findViewById(R.id.btnMonthly);
        btnWeekly = findViewById(R.id.btnWeekly);
        btnLastMonth = findViewById(R.id.btnLastMonth);

        //setting listener to buttons
        btnWeekly.setOnClickListener(this);
        btnMonthly.setOnClickListener(this);
        btnLastMonth.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnWeekly){

        }else if (v.getId() == R.id.btnMonthly){

        }else if(v.getId() == R.id.btnLastMonth){

        }
    }

    public void setTimeButtonStyle(Button b){

    }

}