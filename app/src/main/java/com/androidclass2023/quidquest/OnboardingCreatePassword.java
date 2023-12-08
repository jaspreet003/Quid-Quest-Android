package com.androidclass2023.quidquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import model.USER;

public class OnboardingCreatePassword extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText passwordField, confirmPasswordField;
    private Button btnNext;
    private USER user;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_create_password);

        Intent intent = getIntent();
        user = (USER) intent.getSerializableExtra("user");

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        passwordField = findViewById(R.id.ediTxtPasswordONB);
        confirmPasswordField = findViewById(R.id.ediTxtConfirmPasswordONB);
        btnNext = findViewById(R.id.btnNextConfirmPassONB);
        btnNext.setOnClickListener(v -> {
            String password = passwordField.getText().toString();
            String confirmPassword = confirmPasswordField.getText().toString();
            if (password.equals(confirmPassword)) {
                createAccount(user.getEmail(), password);
            }
        });
    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser authUser = mAuth.getCurrentUser();

                        user.setFirebaseId(authUser.getUid());

                        String encodedEmail = USER.encodeEmail(email);
                        mDatabase.child("USERS").child(encodedEmail).setValue(user);

                        Toast.makeText(OnboardingCreatePassword.this, "Account created successfully.",
                                Toast.LENGTH_SHORT).show();

                        Intent newAct = new Intent(OnboardingCreatePassword.this, ManagerDashboardActivity.class);
                        startActivity(newAct);
                    } else {
                        Toast.makeText(OnboardingCreatePassword.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}