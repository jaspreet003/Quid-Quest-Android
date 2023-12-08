package com.androidclass2023.quidquest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddDepartmentActivity extends AppCompatActivity {

    private EditText editTextDepartmentName;
    private Button btnSave;
    private DatabaseReference databaseDepartments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_department);

        editTextDepartmentName = findViewById(R.id.editTextDepartmentName);
        btnSave = findViewById(R.id.btnSave);

        databaseDepartments = FirebaseDatabase.getInstance().getReference("Departments");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDepartment();
            }
        });
    }

    private void saveDepartment() {
        String departmentName = editTextDepartmentName.getText().toString().trim();

        if (departmentName.isEmpty()) {
            editTextDepartmentName.setError("Department name is required");
            editTextDepartmentName.requestFocus();
            return;
        }

        databaseDepartments.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String existingName = snapshot.getValue(String.class);
                    if (departmentName.equalsIgnoreCase(existingName)) {
                        Toast.makeText(AddDepartmentActivity.this, "Department already exists", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                List<String> departments = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    departments.add(child.getValue(String.class));
                }
                departments.add(departmentName);

                databaseDepartments.setValue(departments)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddDepartmentActivity.this, "Department added", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AddDepartmentActivity.this, "Failed to add department", Toast.LENGTH_SHORT).show();
                            }
                        });
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AddDepartmentActivity.this, "Failed to load departments", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
