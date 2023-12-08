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

import adapters.ManageCategoryAdapter;
import model.Category;

public class ManageCategoryActivity extends AppCompatActivity implements View.OnClickListener{

    private DatabaseReference categoriesDB;
    private ListView listView;
    private List<Category> categories;
    private ManageCategoryAdapter adapter;
    ImageView imgAddCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_category);

        categoriesDB = FirebaseDatabase.getInstance().getReference("Categories");
        listView = findViewById(R.id.lstVManageCategory);
        imgAddCategory = findViewById(R.id.imgAddCategory);
        categories = new ArrayList<>();
        adapter = new ManageCategoryAdapter(this, categories);
        listView.setAdapter(adapter);
        imgAddCategory.setOnClickListener(this);

        categoriesDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                categories.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Category category = new Category(snapshot.getValue(String.class));
                    categories.add(category);
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
        if (v.getId() == R.id.imgAddCategory) {
            Intent intent = new Intent(ManageCategoryActivity.this, AddCategoryActivity.class);
            startActivity(intent);
        }
    }
}