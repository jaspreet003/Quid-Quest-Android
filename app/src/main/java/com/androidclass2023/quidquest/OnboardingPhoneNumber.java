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

import model.Employee;

public class OnboardingPhoneNumber extends AppCompatActivity {
    int employeeId;
    EditText firstNameEditText, lastNameEditText, phoneNumberEditText;
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_phone_number);

        initialize();
    }

    private void initialize() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            employeeId = extras.getInt("employeeId");
        }
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

                updateEmployee(firstName, lastName, phoneNumber);
            }
        });
    }

    private void updateEmployee(String firstName, String lastName, String phoneNumber) {
        // Get a reference to the USERS node
        DatabaseReference employeesDB = FirebaseDatabase.getInstance().getReference("employees");

        // Create a new Employee object
        Employee newEmployee = new Employee(firstName, lastName, phoneNumber, 0, 0, 0);

        // Write the new employee data
        employeesDB.child(String.valueOf(employeeId)).setValue(newEmployee)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // The write was successful, start the next activity
                        Intent intent = new Intent(OnboardingPhoneNumber.this, OnboardingBankDetails.class);
                        intent.putExtra("employeeId", employeeId);
                        intent.putExtra("employee", newEmployee); // Note: Employee class must implement Serializable
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