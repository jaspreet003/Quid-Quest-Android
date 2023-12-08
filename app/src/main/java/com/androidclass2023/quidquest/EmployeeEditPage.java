package com.androidclass2023.quidquest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import adapters.EmployeeAdapter;

public class EmployeeEditPage extends AppCompatActivity implements View.OnClickListener {
    TextView tvName, tvEmail, tvShowTotalExpenses, tvShowAccountNumber, tvShowTransitNumber, tvShowInstituteNumber;
    Spinner spnDepartment;
    Button btnRemoveEmployee, btnUpdate;
    String userEmail;
    DatabaseReference employeeDatabase, departmentDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_edit_page);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initialize();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void initialize() {
        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmployeeEmail);
        spnDepartment = findViewById(R.id.spnDepartment);
        tvShowTotalExpenses = findViewById(R.id.tvShowTotalExpenses);
        tvShowAccountNumber = findViewById(R.id.tvShowAccountNumber);
        tvShowTransitNumber = findViewById(R.id.tvShowTransitNumber);
        tvShowInstituteNumber = findViewById(R.id.tvShowInstituteNumber);
        btnRemoveEmployee = findViewById(R.id.btnRemove);
        btnRemoveEmployee.setOnClickListener(this);
        userEmail = getIntent().getStringExtra("user_email");
        String email = userEmail.replace("-", "@").replace("_", ".");
        tvEmail.setText(email);
        departmentDatabase = FirebaseDatabase.getInstance().getReference().child("Departments");
        departmentDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> departments = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String department = snapshot1.getValue(String.class);
                    departments.add(department);
                }
                populateSpinner(departments);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        setContent();

    }

    private void populateSpinner(ArrayList<String> departments) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, departments);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spnDepartment.setAdapter(adapter);
    }

    private void setContent() {
        employeeDatabase = FirebaseDatabase.getInstance().getReference().child("USERS").child(userEmail);
        employeeDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String userName = snapshot.child("Name").getValue(String.class);
                    Integer totalExpense = snapshot.child("Total Expenses").getValue(Integer.class);
                    Integer accountNumber = snapshot.child("Account Number").getValue(Integer.class);
                    Integer transitNumber = snapshot.child("Transit Number").getValue(Integer.class);
                    Integer instituteNumber = snapshot.child("Institute Number").getValue(Integer.class);

                    tvShowTotalExpenses.setText(totalExpense != null ? "$" + totalExpense : "N/A");
                    tvShowAccountNumber.setText(accountNumber != null ? String.valueOf(accountNumber) : "N/A");
                    tvShowTransitNumber.setText(transitNumber != null ? String.valueOf(transitNumber) : "N/A");
                    tvShowInstituteNumber.setText(instituteNumber != null ? String.valueOf(instituteNumber) : "N/A");
                    tvName.setText(userName != null ? userName : "N/A");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnUpdate) {
            employeeDatabase.child("Department").setValue(spnDepartment.getSelectedItem().toString());
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Employee Updated!");
            builder.setMessage(
                    "You have updated the employee's department to : " + spnDepartment.getSelectedItem().toString());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete Employee");
            builder.setMessage("Are you sure you want to remove the employee?");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Call a method to delete the record from the database
                    deleteRecordFromDatabase();
                    finish();

                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing or handle the case where "No" is clicked
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    positiveButton.setTextColor(Color.RED);
                }
            });

            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                    negativeButton.setTextColor(Color.GRAY);
                }
            });
            alertDialog.show();
        }

    }

    private void deleteRecordFromDatabase() {
        employeeDatabase.removeValue();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Employee Removed Successfully!");
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}