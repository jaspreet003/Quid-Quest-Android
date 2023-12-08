package com.androidclass2023.quidquest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import model.User;

public class OnboardingVerifyDetails extends AppCompatActivity {
    private static final String USER_REF = "USERS";
    private User user;
    private EditText firstNameEditText, lastNameEditText, phoneNumberEditText, accNumEditText, transNumEditText,
            insNumEditText;
    private Button btnContinue, btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_verify_details);
        initializeViews();
        extractIntentExtras();
        setViewListeners();
    }

    private void initializeViews() {
        firstNameEditText = findViewById(R.id.editTxtFirstNameProfileONB);
        lastNameEditText = findViewById(R.id.editTxtLastNameProfileONB);
        phoneNumberEditText = findViewById(R.id.editTxtPhoneNumberProfileONB);
        accNumEditText = findViewById(R.id.editTxtAccountNumberProfileONB);
        transNumEditText = findViewById(R.id.editTxtTransitNumberProfileONB);
        insNumEditText = findViewById(R.id.editTxtInstituteNumberProfileONB);
        btnContinue = findViewById(R.id.btnContinueVerifyDetailsONB);
        btnEdit = findViewById(R.id.btnEditVerifyDetailsONB);
        setFieldsEnabled(false);
    }

    private void extractIntentExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = (User) extras.getSerializable("user");
            if (user != null) {
                populateDetails();
            }
        }
    }

    private void populateDetails() {
        firstNameEditText.setText(user.getFirstName());
        lastNameEditText.setText(user.getLastName());
        phoneNumberEditText.setText(String.valueOf(user.getPhoneNumber()));
        accNumEditText.setText(String.valueOf(user.getAccountNumber()));
        transNumEditText.setText(String.valueOf(user.getTransitNumber()));
        insNumEditText.setText(String.valueOf(user.getInstituteNumber()));
    }

    private void setFieldsEnabled(boolean enabled) {
        firstNameEditText.setEnabled(enabled);
        lastNameEditText.setEnabled(enabled);
        phoneNumberEditText.setEnabled(enabled);
        accNumEditText.setEnabled(enabled);
        transNumEditText.setEnabled(enabled);
        insNumEditText.setEnabled(enabled);
    }

    private void setViewListeners() {
        btnContinue.setOnClickListener(v -> saveDetails());
        btnEdit.setOnClickListener(v -> setFieldsEnabled(true));
    }

    private void saveDetails() {
        updateFromFields();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(USER_REF);
        String encodedEmail = User.encodeEmail(user.getEmail());
        userRef.child(encodedEmail).setValue(user)
                .addOnSuccessListener(aVoid -> startNextActivity())
                .addOnFailureListener(this::handleDatabaseWriteFailure);
    }

    private void updateFromFields() {
        user.setFirstName(firstNameEditText.getText().toString());
        user.setLastName(lastNameEditText.getText().toString());
        user.setPhoneNumber(phoneNumberEditText.getText().toString());
        user.setAccountNumber(Integer.parseInt(accNumEditText.getText().toString()));
        user.setTransitNumber(Integer.parseInt(transNumEditText.getText().toString()));
        user.setInstituteNumber(Integer.parseInt(insNumEditText.getText().toString()));
    }

    private void startNextActivity() {
        Intent intent = new Intent(this, OnboardingCreatePassword.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    private void handleDatabaseWriteFailure(Exception e) {
        Toast.makeText(this, "Failed to save user details", Toast.LENGTH_SHORT).show();
    }
}
