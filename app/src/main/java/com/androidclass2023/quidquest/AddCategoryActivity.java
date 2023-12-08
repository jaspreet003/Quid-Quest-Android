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

public class AddCategoryActivity extends AppCompatActivity {
    private EditText editTextCategoryName;
    private Button btnSave;
    private DatabaseReference databaseCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        editTextCategoryName = findViewById(R.id.editTextCategoryName);
        btnSave = findViewById(R.id.btnSave);

        databaseCategories = FirebaseDatabase.getInstance().getReference("Categories");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCategory();
            }
        });
    }

    private void saveCategory() {
        String categoryName = editTextCategoryName.getText().toString().trim();

        if (categoryName.isEmpty()) {
            editTextCategoryName.setError("Category name is required");
            editTextCategoryName.requestFocus();
            return;
        }

        databaseCategories.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Check for duplicates first
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String existingName = snapshot.getValue(String.class);
                    if (categoryName.equalsIgnoreCase(existingName)) {
                        Toast.makeText(AddCategoryActivity.this, "Category already exists", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                List<String> categories = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    categories.add(child.getValue(String.class));
                }
                categories.add(categoryName);

                databaseCategories.setValue(categories)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddCategoryActivity.this, "Category added", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AddCategoryActivity.this, "Failed to add category", Toast.LENGTH_SHORT).show();
                            }
                        });
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AddCategoryActivity.this, "Failed to load category", Toast.LENGTH_SHORT).show();
            }
        });
    }
}