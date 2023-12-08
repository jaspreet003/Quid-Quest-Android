package com.androidclass2023.quidquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OnboardWelcome extends AppCompatActivity {
    Button btnBegin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard_welcome);

        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();

        if (Intent.ACTION_VIEW.equals(action) && data != null) {
            String employeeId = data.getLastPathSegment();
        }

        btnBegin = findViewById(R.id.btnBeginOnboarding);
        btnBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int employeeId = 1;
                Intent newAct = new Intent(OnboardWelcome.this, OnboardingPhoneNumber.class);
                newAct.putExtra("employeeId", employeeId);
                startActivity(newAct);
            }
        });

    }
}