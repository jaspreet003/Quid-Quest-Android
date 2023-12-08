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

public class OnboardingPhoneNumber extends AppCompatActivity {
    EditText firstNameEditText, lastNameEditText, phoneNumberEditText;
    Button nextButton;

    String userEmail, decodedEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_phone_number);

        initialize();
    }

    private void initialize() {
        Bundle extras = getIntent().getExtras();
        userEmail = extras.getString("userEmail");
        decodedEmail = User.decodeEmail(userEmail);
        firstNameEditText = findViewById(R.id.editTxtFirstNameOnB);
        lastNameEditText = findViewById(R.id.editTxtLastNameOnB);
        phoneNumberEditText = findViewById(R.id.ediTxtEnterPhoneNumber);
        nextButton = findViewById(R.id.btnNextOnBPhoneNumber);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String phoneNumber = phoneNumberEditText.getText().toString();

                updateUser(firstName, lastName, phoneNumber);
            }
        });
    }

    private void updateUser(String firstName, String lastName, String phoneNumber) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("USERS");

        User newUser = new User(
                firstName,
                lastName,
                decodedEmail,
                phoneNumber,
                0,
                0,
                0);

        newUser.setName(firstName+" "+lastName);

        Log.d("OnboardingPhoneNumber", "updateUser: " + newUser.toString());
        Log.d("OnboardingPhoneNumber", "updateUser: " + userEmail);

        userRef.child(userEmail).setValue(newUser)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = new Intent(OnboardingPhoneNumber.this, OnboardingBankDetails.class);
                        intent.putExtra("user", newUser);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // The write failed, log the error
                        Log.e("OnboardingPhoneNumber", "Error writing new employee", e);
                    }
                });
    }
}