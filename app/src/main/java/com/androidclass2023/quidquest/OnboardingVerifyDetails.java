package com.androidclass2023.quidquest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import model.Employee;

public class OnboardingVerifyDetails extends AppCompatActivity {
    private static final String EMPLOYEES_DB = "employees";
    private Employee employee;
    private EditText firstNameEditText, lastNameEditText, phoneNumberEditText, accNumEditText, transNumEditText, insNumEditText;
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
            employee = (Employee) extras.getSerializable("employee");
            if (employee != null) {
                populateEmployeeDetails();
            }
        }
    }

    private void populateEmployeeDetails() {
        firstNameEditText.setText(employee.getFirstName());
        lastNameEditText.setText(employee.getLastName());
        phoneNumberEditText.setText(String.valueOf(employee.getPhoneNumber()));
        accNumEditText.setText(String.valueOf(employee.getAccNum()));
        transNumEditText.setText(String.valueOf(employee.getTransNum()));
        insNumEditText.setText(String.valueOf(employee.getInsNum()));
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
        btnContinue.setOnClickListener(v -> saveEmployeeDetails());
        btnEdit.setOnClickListener(v -> setFieldsEnabled(true));
    }

    private void saveEmployeeDetails() {
        updateEmployeeFromFields();
        DatabaseReference employeesDB = FirebaseDatabase.getInstance().getReference(EMPLOYEES_DB);
        employeesDB.child(String.valueOf(employee.getId())).setValue(employee)
                .addOnSuccessListener(aVoid -> startNextActivity())
                .addOnFailureListener(this::handleDatabaseWriteFailure);
    }

    private void updateEmployeeFromFields() {
        employee.setFirstName(firstNameEditText.getText().toString());
        employee.setLastName(lastNameEditText.getText().toString());
        employee.setPhoneNumber(phoneNumberEditText.getText().toString());
        employee.setAccNum(Integer.parseInt(accNumEditText.getText().toString()));
        employee.setTransNum(Integer.parseInt(transNumEditText.getText().toString()));
        employee.setInsNum(Integer.parseInt(insNumEditText.getText().toString()));
    }

    private void startNextActivity() {
        Intent intent = new Intent(this, OnboardingCreatePassword.class);
        startActivity(intent);
    }

    private void handleDatabaseWriteFailure(Exception e) {
        // TODO: Implement error handling logic here
    }
}
