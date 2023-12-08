package com.androidclass2023.quidquest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import model.Expense;

public class AddExpenseActivity extends AppCompatActivity implements View.OnClickListener {


    DatabaseReference databaseRef;

    Spinner categoriesSpinner;

    EditText edExpenseAmount, edDescription;

    Button btnClearData, btnAddExpense;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

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

        edExpenseAmount = findViewById(R.id.edExpenseAmount);

        edDescription = findViewById(R.id.edDescription);

        btnClearData = findViewById(R.id.btnClearData);
        btnClearData.setOnClickListener(this);

        btnAddExpense = findViewById(R.id.btnAddExpense);
        btnAddExpense.setOnClickListener(this);


        databaseRef = FirebaseDatabase.getInstance().getReference("Categories");

        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> categories = new ArrayList<>();
                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    String category = categorySnapshot.getValue(String.class);
                    categories.add(category);
                }

                // Find the spinner in the layout
                categoriesSpinner = findViewById(R.id.categoriesSpinner);
                // Create an ArrayAdapter using the string array and a default spinner layout
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddExpenseActivity.this, android.R.layout.simple_spinner_item, categories);
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Apply the adapter to the spinner
                categoriesSpinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
            }
        });



    }


    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.btnClearData)
            clearData();

        if (id == R.id.btnAddExpense)
            Dialog();




    }

    private void AddExpense() {

        // Assuming you have the current user's email
        //String safeEmail = currentUserEmail.replace('.', '_');
        String currentUserEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail().replace("@", "-")
                .replace(".", "_");
        // Get values from the UI
        String category = categoriesSpinner.getSelectedItem().toString();
        String description = edDescription.getText().toString();
        double amount;
        try {
            amount = Double.parseDouble(edExpenseAmount.getText().toString());
        } catch (NumberFormatException e) {
            amount = 0; // handle invalid double conversion
        }

        // Create a new expense object
        Expense newExpense = new Expense(amount, description);

        // Get reference to the user's expenses in the database
        DatabaseReference categoryRef = FirebaseDatabase.getInstance().getReference("USERS")
                .child(currentUserEmail)
                .child("EXPENSES")
                .child("CATEGORY")
                .child(category);

        double finalAmount = amount;
        categoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long maxIndex = -1;
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    long index = Long.parseLong(child.getKey());
                    if (index > maxIndex) maxIndex = index;
                }
                long newIndex = maxIndex + 1;
                // Create a new expense object
                Expense newExpense = new Expense(finalAmount, description);
                // Add new expense with the newIndex
                categoryRef.child(String.valueOf(newIndex)).setValue(newExpense)
                        .addOnSuccessListener(aVoid -> {
                            // Handle success
                        })
                        .addOnFailureListener(e -> {
                            // Handle failure
                        });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
            }
        });

        BackToExpenseScreen();

    }

    private void clearData() {

        edExpenseAmount.setText("");
        edDescription.setText("");

    }


    private void Dialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to add a new expense?");


        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {


                AddExpense();


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


    private void BackToExpenseScreen() {



        Intent intent = new Intent(AddExpenseActivity.this, Expense_Screen.class);

        startActivity(intent);



    }


}