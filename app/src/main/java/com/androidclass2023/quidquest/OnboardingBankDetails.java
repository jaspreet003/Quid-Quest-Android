package com.androidclass2023.quidquest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import model.User;
public class OnboardingBankDetails extends AppCompatActivity {
    User user;
    EditText accNumEditText, transNumEditText, insNumEditText;
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_bank_details);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = (User) extras.getSerializable("user");
        }

        accNumEditText = findViewById(R.id.ediTxtEnterAccountNumber);
        transNumEditText = findViewById(R.id.ediTxtEnterTransitNumber);
        insNumEditText = findViewById(R.id.ediTxtEnterInstituteNumber);
        nextButton = findViewById(R.id.btnNextOnbBankDetails);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int accNum = Integer.parseInt(accNumEditText.getText().toString());
                int transNum = Integer.parseInt(transNumEditText.getText().toString());
                int insNum = Integer.parseInt(insNumEditText.getText().toString());

                user.setAccountNumber(accNum);
                user.setTransitNumber(transNum);
                user.setInstituteNumber(insNum);

                updateUser(user);
            }
        });
    }

    private void updateUser(User user) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("USERS");
        String encodedEmail = User.encodeEmail(user.getEmail());
        usersRef.child(String.valueOf(encodedEmail)).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent newAct = new Intent(OnboardingBankDetails.this, OnboardingVerifyDetails.class);
                        newAct.putExtra("user", user);
                        startActivity(newAct);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("OnboardingBankDetails", "Error updating employee: " + e.getMessage());
                    }
                });
    }
}