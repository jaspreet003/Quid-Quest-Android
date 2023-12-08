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

public class OnboardingBankDetails extends AppCompatActivity {
    int employeeId;
    Employee employee;
    EditText accNumEditText, transNumEditText, insNumEditText;
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_bank_details);

        // Extract the employeeId and Employee object from the intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            employeeId = extras.getInt("employeeId");
            employee = (Employee) extras.getSerializable("employee");
        }

        // Find the EditText fields by their IDs
        accNumEditText = findViewById(R.id.ediTxtEnterAccountNumber);
        transNumEditText = findViewById(R.id.ediTxtEnterTransitNumber);
        insNumEditText = findViewById(R.id.ediTxtEnterInstituteNumber);
        nextButton = findViewById(R.id.btnNextOnbBankDetails);

        // Add a click listener to the nextButton
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the values from the EditText fields
                int accNum = Integer.parseInt(accNumEditText.getText().toString());
                int transNum = Integer.parseInt(transNumEditText.getText().toString());
                int insNum = Integer.parseInt(insNumEditText.getText().toString());

                // Update the Employee object
                employee.setAccNum(accNum);
                employee.setTransNum(transNum);
                employee.setInsNum(insNum);

                // Update the employee in the Firebase Realtime Database
                updateEmployee(employee);
            }
        });
    }

    private void updateEmployee(Employee employee) {
        // Get a reference to the USERS node
        DatabaseReference employeesDB = FirebaseDatabase.getInstance().getReference("employees");

        // Write the updated employee data
        employeesDB.child(String.valueOf(employeeId)).setValue(employee)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // The write was successful, start the next activity
                        Intent intent = new Intent(OnboardingBankDetails.this, OnboardingVerifyDetails.class);
                        intent.putExtra("employeeId", employeeId);
                        intent.putExtra("employee", employee); // Note: Employee class must implement Serializable
                        startActivity(intent);
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