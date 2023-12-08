package com.androidclass2023.quidquest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import adapters.EmployeeAdapter;

public class EmployeeLIstScreen extends AppCompatActivity
        implements View.OnClickListener, AdapterView.OnItemClickListener {

    EditText edName;
    Spinner spnDepartment;
    ListView lvEmployees;
    Button btnInviteEmployee, btnFilter;
    DatabaseReference employeeDatabase, departmentDatabase;
    ArrayList<String> list = new ArrayList<>();
    EmployeeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list_screen);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initialize();
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

    private void initialize() {
        btnFilter = findViewById(R.id.btnFilter);
        btnFilter.setOnClickListener(this);
        edName = findViewById(R.id.edName);
        spnDepartment = findViewById(R.id.spnDepartment);
        lvEmployees = findViewById(R.id.lvEmployees);
        adapter = new EmployeeAdapter(EmployeeLIstScreen.this, list, 2);

        lvEmployees.setAdapter(adapter);
        lvEmployees.setClickable(true);
        lvEmployees.setOnItemClickListener(this);

        btnInviteEmployee = findViewById(R.id.btnInviteEmployee);
        btnInviteEmployee.setOnClickListener(this);

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

        employeeDatabase = FirebaseDatabase.getInstance().getReference().child("USERS");
        employeeDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                    list.add(snapshot1.getKey());

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        edName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                EmployeeLIstScreen.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });
    }

    private void populateSpinner(ArrayList<String> departments) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, departments);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spnDepartment.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnFilter) {
            String department = spnDepartment.getSelectedItem().toString();

        } else {
            Intent intent = new Intent(this, InviteEmployeeActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String userEmail = parent.getItemAtPosition(position).toString();
        Intent intent = new Intent(this, EmployeeEditPage.class);
        intent.putExtra("user_email", userEmail);
        startActivity(intent);
    }
}
