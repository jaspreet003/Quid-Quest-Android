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
    String userId = "1";
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard_welcome);

        dbRef = FirebaseDatabase.getInstance().getReference("USERS");

        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();

        if (Intent.ACTION_VIEW.equals(action) && data != null) {
            userId = data.getLastPathSegment();
        }

        if (!userId.isEmpty()) {
            dbRef.child(String.valueOf(userId)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        userEmail = dataSnapshot.child("email").getValue(String.class);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle possible errors.
                    Log.e("onCancelled", "onCancelled: " + databaseError.getMessage());
                }
            });
        }

        btnBegin = findViewById(R.id.btnBeginOnboarding);
        btnBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newAct = new Intent(OnboardWelcome.this, OnboardingPhoneNumber.class);
                if(!userId.isEmpty() && !userEmail.isEmpty()){
                    newAct.putExtra("userEmail", userEmail);
                    startActivity(newAct);
                }
            }
        });

    }
}