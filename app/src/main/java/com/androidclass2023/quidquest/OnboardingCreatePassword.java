package com.androidclass2023.quidquest;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class OnboardingCreatePassword extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailField;
    private EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_create_password);

        mAuth = FirebaseAuth.getInstance();

        emailField = findViewById(R.id.emailField); // replace with your actual email field ID
        passwordField = findViewById(R.id.passwordField); // replace with your actual password field ID
    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        // updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        // updateUI(null);
                    }
                });
    }
}