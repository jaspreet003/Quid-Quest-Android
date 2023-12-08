package com.androidclass2023.quidquest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import adapters.ExpenseAdapter;
import model.Expense;

public class Expense_Screen extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener{
    Button btnAdd;

    private ListView lvExpenses;

    DatabaseReference databaseExpenses;

    //FirebaseStorage storage;

    //StorageReference storageReference, sRef;
    LinearLayout btnLayout, mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_screen);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize Firebase Database reference
        databaseExpenses = FirebaseDatabase.getInstance().getReference().child("USERS");

        initialize();

    }



    private void initialize() {
        lvExpenses = findViewById(R.id.lvExpenses);
        lvExpenses.setClickable(true);
        lvExpenses.setOnItemClickListener(this);

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);


        List<Expense> expenses = new ArrayList<>();
        databaseExpenses.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    // Get user name and department
                    String email = userSnapshot.getKey();
                    String userName = userSnapshot.child("name").getValue(String.class);
                    String userDepartment = userSnapshot.child("department").getValue(String.class);

                    DataSnapshot expensesSnapshot = userSnapshot.child("EXPENSES").child("CATEGORY");
                    for (DataSnapshot categorySnapshot : expensesSnapshot.getChildren()) {
                        String category = categorySnapshot.getKey();

                        for (DataSnapshot expenseSnapshot : categorySnapshot.getChildren()) {
                            // Check if the "Approved" field exists
                            if (!expenseSnapshot.hasChild("Approved")) {
                                // Get expense details
                                String description = expenseSnapshot.child("Description").getValue(String.class);
                                Double amount = expenseSnapshot.child("Amount").getValue(Double.class);
                                String ID = expenseSnapshot.getKey();

                                Expense expense = new Expense(category, description, userName, userDepartment, email, ID, amount);
                                expenses.add(expense);
                            }
                        }
                    }
                }

                // Set the adapter for the ListView
                ExpenseAdapter adapter = new ExpenseAdapter(Expense_Screen.this, expenses);
                lvExpenses.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
            }


        });

    }




    private void showAlert(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder( Expense_Screen.this );

        builder.setTitle( "Notification" )
                .setMessage( message )
                .setPositiveButton( "OK", (dialogInterface, i) -> {
                } )
                .show();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Expense clickedExpense = (Expense) parent.getItemAtPosition(position);

        // Create an Intent to start the ExpenseDetailActivity
        Intent intent = new Intent(Expense_Screen.this, SingleExpenseActivity.class);

        // Pass the details of the clicked item to the new activity
        intent.putExtra("category", clickedExpense.getCategory());
        intent.putExtra("description", clickedExpense.getDescription());
        intent.putExtra("userName", clickedExpense.getName());
        intent.putExtra("userDepartment", clickedExpense.getDepartment());
        intent.putExtra("amount", clickedExpense.getAmount());
        intent.putExtra("email", clickedExpense.getEmail());
        intent.putExtra("ID", clickedExpense.getExpenseID());

        // Start the new activity
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.btnAdd)
            addExpense();
    }

    private void addExpense() {

        Intent intent = new Intent( Expense_Screen.this, AddExpenseActivity.class );
        startActivity( intent );



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        btnLayout = findViewById(R.id.btnLayout);
        mLayout = findViewById(R.id.mLayout);
        mLayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    public void expand(View view) {
//        int v = (btnLayout.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;
//
//        TransitionManager.beginDelayedTransition(mLayout, new AutoTransition());
//        btnLayout.setVisibility(v);
//    }
}