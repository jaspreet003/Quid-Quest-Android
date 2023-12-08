package com.androidclass2023.quidquest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class Settings_Menu extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_menu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set up click listeners
        findViewById(R.id.tvLogOut).setOnClickListener(this);
        findViewById(R.id.tvProfile).setOnClickListener(this);
        findViewById(R.id.tvDepartment).setOnClickListener(this);
        findViewById(R.id.tvCategories).setOnClickListener(this);
        findViewById(R.id.tvPassword).setOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tvLogOut) {
            showLogoutConfirmation();
        } else if (id == R.id.tvProfile) {
            navigateToProfile();
        } else if (id == R.id.tvDepartment) {
            navigateToDepartment();
        } else if (id == R.id.tvCategories) {
            navigateToCategories();
        } else if (id == R.id.tvPassword) {
            navigateToPassword();
        }
    }

    private void showLogoutConfirmation() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes", (dialog, which) -> logout())
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // To clear all
        startActivity(intent);
        finish();
    }

    private void navigateToProfile() {
        Intent intent = new Intent(this, ProfilePage.class);
        startActivity(intent);
    }

    private void navigateToDepartment() {
        Intent intent = new Intent(this, ManageDepartmentActivity.class);
        startActivity(intent);
    }

    private void navigateToCategories() {
        Intent intent = new Intent(this, ManageCategoryActivity.class);
        startActivity(intent);
    }

    private void navigateToPassword() {
        Intent intent = new Intent(this, UpdatePasswordActivity.class);
        startActivity(intent);
    }

}
