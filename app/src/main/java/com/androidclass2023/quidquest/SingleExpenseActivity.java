package com.androidclass2023.quidquest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import adapters.ExpenseAdapter;

public class SingleExpenseActivity extends AppCompatActivity implements View.OnClickListener {

    EditText tvUserName, tvAmount, tvDepartment, tvCategory, tvDescription;

    String userName, userDepartment, category, description, email, id;
    double amount;

    Button btnDecline, btnApprove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_expense);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Retrieve data from the Intent
        Intent intent = getIntent();
        category = intent.getStringExtra("category");
        description = intent.getStringExtra("description");
        userName = intent.getStringExtra("userName");
        userDepartment = intent.getStringExtra("userDepartment");
        amount = intent.getDoubleExtra("amount", 0);
        email = intent.getStringExtra("email");
        id = intent.getStringExtra("ID");



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


        btnApprove = findViewById(R.id.btnApprove);
        btnApprove.setOnClickListener(this);


        btnDecline = findViewById(R.id.btnDecline);
        btnDecline.setOnClickListener(this);


        tvUserName = findViewById(R.id.textViewUserName);
        tvUserName.setText(userName);
        tvUserName.setFocusable(false);
        tvUserName.setCursorVisible(false);

        tvAmount = findViewById(R.id.textViewAmount);
        tvAmount.setText(String.valueOf(amount));
        tvAmount.setFocusable(false);
        tvAmount.setCursorVisible(false);

        tvDepartment = findViewById(R.id.textViewUserDepartment);
        tvDepartment.setText(userDepartment);
        tvDepartment.setFocusable(false);
        tvDepartment.setCursorVisible(false);

        tvCategory = findViewById(R.id.textViewCategory);
        tvCategory.setText(category);
        tvCategory.setFocusable(false);
        tvCategory.setCursorVisible(false);

        tvDescription = findViewById(R.id.textViewDescription);
        tvDescription.setText(description);
        tvDescription.setFocusable(false);
        tvDescription.setCursorVisible(false);


    }

    private void showAlert(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(SingleExpenseActivity.this);

        builder.setTitle("Notification")
                .setMessage(message)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                })
                .show();

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.btnApprove)
            Approve();

        if (id == R.id.btnDecline)
            Decline();



    }

    private void BackToExpenseScreen() {



        Intent intent = new Intent(SingleExpenseActivity.this, Expense_Screen.class);

        startActivity(intent);



    }

    private void Decline() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to decline the expense?");


        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {



                DeclineExpense();


            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                dialog.dismiss();
            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }

    private void DeclineExpense() {

        String userEmail = email;
        String expenseCategory = category;
        String expenseId = id;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


        DatabaseReference expenseRef = databaseReference.child("USERS")
                .child(userEmail)
                .child("EXPENSES")
                .child("CATEGORY")
                .child(expenseCategory)
                .child(expenseId);


        expenseRef.child("Approved").setValue(false)
                .addOnSuccessListener(aVoid -> {

                    Log.d("UpdateExpense", "Expense successfully declined");

                })
                .addOnFailureListener(e -> {

                    Log.d("UpdateExpense", "Failed to decline expense: " + e.getMessage());
                });

        BackToExpenseScreen();


    }

    private void Approve() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to approve the expense?");


        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {


                ApproveExpense();


            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                dialog.dismiss();
            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }


    private void ApproveExpense() {


        String userEmail = email;
        String expenseCategory = category;
        String expenseId = id;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


        DatabaseReference expenseRef = databaseReference.child("USERS")
                .child(userEmail)
                .child("EXPENSES")
                .child("CATEGORY")
                .child(expenseCategory)
                .child(expenseId);


        expenseRef.child("Approved").setValue(true)
                .addOnSuccessListener(aVoid -> {

                    Log.d("UpdateExpense", "Expense successfully approved");

                })
                .addOnFailureListener(e -> {

                    Log.d("UpdateExpense", "Failed to approve expense: " + e.getMessage());
                });


        BackToExpenseScreen();


    }




}

