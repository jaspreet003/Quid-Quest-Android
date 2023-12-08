package com.androidclass2023.quidquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import adapters.ManageDepartmentAdapter;
import model.Department;

public class ManageDepartmentActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference departmentsDB;
    private ListView listView;
    private List<Department> departments;
    private ManageDepartmentAdapter adapter;
    ImageView imgAddDepartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_department);

        departmentsDB = FirebaseDatabase.getInstance().getReference("Departments");
        listView = findViewById(R.id.lstVManageDepartments);
        imgAddDepartment = findViewById(R.id.imgAddDepartment);
        departments = new ArrayList<>();
        adapter = new ManageDepartmentAdapter(this, departments);
        listView.setAdapter(adapter);
        imgAddDepartment.setOnClickListener(this);

        departmentsDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                departments.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Department department = new Department(snapshot.getValue(String.class));
                    departments.add(department);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgAddDepartment) {
        Intent intent = new Intent(ManageDepartmentActivity.this, AddDepartmentActivity.class);
        startActivity(intent);
    }
    }
}