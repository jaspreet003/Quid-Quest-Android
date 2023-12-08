package com.androidclass2023.quidquest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import android.view.MenuItem;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import adapters.DashboardViewPagerAdapter;

public class ManagerDashboardActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnWeekly, btnMonthly, btnLastMonth;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_dashboard);

        TabLayout tabLayout = findViewById(R.id.tabLayoutDashboard);
        ViewPager2 viewPager = findViewById(R.id.viewPagerDashboard);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        viewPager.setAdapter(new DashboardViewPagerAdapter(this));

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(item -> {
            // Handle navigation view item clicks here.
            int id = item.getItemId();

            if (id == R.id.nav_expense) {
                Intent intent = new Intent(ManagerDashboardActivity.this, Expense_Screen.class);
                startActivity(intent);
            } else if (id == R.id.nav_settings) {
                Intent intent = new Intent(ManagerDashboardActivity.this, Settings_Menu.class);
                startActivity(intent);
            } else if (id == R.id.nav_employees) {
                Intent intent = new Intent(ManagerDashboardActivity.this, EmployeeLIstScreen.class);
                startActivity(intent);
            } else if (id == R.id.nav_logout) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(ManagerDashboardActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // To clear all the
                startActivity(intent);
                finish();
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        initialize();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initialize() {
        // Configure the variables
        btnMonthly = findViewById(R.id.btnMonthly);
        btnWeekly = findViewById(R.id.btnWeekly);
        btnLastMonth = findViewById(R.id.btnLastMonth);

        // setting listener to buttons
        btnWeekly.setOnClickListener(this);
        btnMonthly.setOnClickListener(this);
        btnLastMonth.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnWeekly) {

        } else if (v.getId() == R.id.btnMonthly) {

        } else if (v.getId() == R.id.btnLastMonth) {

        }
    }

    public void setTimeButtonStyle(Button b) {

    }

}