package com.androidclass2023.quidquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OnboardWelcome extends AppCompatActivity {
    Button btnBegin;
    String userEmail;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard_welcome);

        userId = "sandeep-gmail_com";

        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();

        if (Intent.ACTION_VIEW.equals(action) && data != null) {
            userId = data.getLastPathSegment();
            Log.d("got this email:", userId);
        }

        btnBegin = findViewById(R.id.btnBeginOnboarding);
        btnBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newAct = new Intent(OnboardWelcome.this, OnboardingPhoneNumber.class);
                newAct.putExtra("userEmail", userId);
                startActivity(newAct);
            }
        });

    }
}