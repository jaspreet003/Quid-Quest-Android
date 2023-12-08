package com.androidclass2023.quidquest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ProfilePage extends AppCompatActivity {

    private EditText editTxtFirstNameProfile, editTxtLastNameProfile, editTxtEmailProfile,
            editTxtPhoneNumberProfile, editTxtAccountNumberProfile,
            editTxtTransitNumberProfile, editTxtInstituteNumberProfile;

    private DatabaseReference databaseUsers;
    private FirebaseAuth auth;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        // Initialize Firebase Auth and Database Reference
        auth = FirebaseAuth.getInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference("USERS");

        // Get the current user's UID
        userId = auth.getCurrentUser().getUid();

        // Initialize views
        editTxtFirstNameProfile = findViewById(R.id.editTxtFirstNameProfile);
        editTxtLastNameProfile = findViewById(R.id.editTxtLastNameProfile);
        editTxtEmailProfile = findViewById(R.id.editTxtEmailProfile);
        editTxtPhoneNumberProfile = findViewById(R.id.editTxtPhoneNumberProfile);
        editTxtAccountNumberProfile = findViewById(R.id.editTxtAccountNumberProfile);
        editTxtTransitNumberProfile = findViewById(R.id.editTxtTransitNumberProfile);
        editTxtInstituteNumberProfile = findViewById(R.id.editTxtInstituteNumberProfile);

        // Fetch and populate user data
        populateUserData();

        // Set up the button to update profile information
        Button btnUpdateProfile = findViewById(R.id.btnUpdateProfile);
        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserData();
            }
        });
    }

    private void populateUserData() {
        // Assuming the user's key is their UID from Firebase Auth
        databaseUsers.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        editTxtFirstNameProfile.setText(user.getFirstName());
                        editTxtLastNameProfile.setText(user.getLastName());
                        editTxtEmailProfile.setText(user.getEmail());
                        editTxtPhoneNumberProfile.setText(user.getPhoneNumber());
                        editTxtAccountNumberProfile.setText(user.getAccountNumber());
                        editTxtTransitNumberProfile.setText(user.getTransitNumber());
                        editTxtInstituteNumberProfile.setText(user.getInstituteNumber());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
            }
        });
    }

    private void updateUserData() {
        String firstName = editTxtFirstNameProfile.getText().toString().trim();
        String lastName = editTxtLastNameProfile.getText().toString().trim();
        String email = editTxtEmailProfile.getText().toString().trim(); // Add validation as needed
        String phoneNumber = editTxtPhoneNumberProfile.getText().toString().trim();
        String accountNumber = editTxtAccountNumberProfile.getText().toString().trim();
        String transitNumber = editTxtTransitNumberProfile.getText().toString().trim();
        String instituteNumber = editTxtInstituteNumberProfile.getText().toString().trim();

        Map<String, Object> updates = new HashMap<>();
        updates.put("FirstName", firstName);
        updates.put("lastName", lastName);
        updates.put("email", email); // Email updating might require specific handling
        updates.put("phoneNumber", phoneNumber);
        updates.put("accountNumber", accountNumber);
        updates.put("transitNumber", transitNumber);
        updates.put("instituteNumber", instituteNumber);
        // ... Add other updates

        // Update children of the user's database entry
        databaseUsers.child(userId).updateChildren(updates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ProfilePage.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ProfilePage.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // A simple User class to match the structure of your Firebase data
    public static class User {
        private String firstName;
        private String lastName;
        // ... Other fields

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }


        // Getters and setters for all fields
    }
}
