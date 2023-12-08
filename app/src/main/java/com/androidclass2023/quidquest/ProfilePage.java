package com.androidclass2023.quidquest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import model.User;

public class ProfilePage extends AppCompatActivity {
    private EditText editTxtFirstNameProfile, editTxtLastNameProfile, editTxtEmailProfile,
            editTxtPhoneNumberProfile, editTxtAccountNumberProfile,
            editTxtTransitNumberProfile, editTxtInstituteNumberProfile;
    private DatabaseReference databaseUsers;
    private String encodedEmail;
    private Button btnUpdateProfile;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTxtFirstNameProfile = findViewById(R.id.editTxtFirstNameProfile);
        editTxtLastNameProfile = findViewById(R.id.editTxtLastNameProfile);
        editTxtEmailProfile = findViewById(R.id.editTxtEmailProfile);
        editTxtPhoneNumberProfile = findViewById(R.id.editTxtPhoneNumberProfile);
        editTxtAccountNumberProfile = findViewById(R.id.editTxtAccountNumberProfile);
        editTxtTransitNumberProfile = findViewById(R.id.editTxtTransitNumberProfile);
        editTxtInstituteNumberProfile = findViewById(R.id.editTxtInstituteNumberProfile);
        btnUpdateProfile = findViewById(R.id.btnUpdateProfile);

        databaseUsers = FirebaseDatabase.getInstance().getReference().child("USERS");

        populateUserData();

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });
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
    private void populateUserData() {
        FirebaseUser firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseuser != null) {
            String email = firebaseuser.getEmail();
            if (email != null) {
                Log.d("ProfilePage", "Firebase email: " + email);
                encodedEmail = User.encodeEmail(email);
                Log.d("ProfilePage", "Encoded email: " + encodedEmail);
            }
        }
        databaseUsers.child(encodedEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        editTxtFirstNameProfile.setText(String.valueOf(user.getFirstName()));
                        editTxtLastNameProfile.setText(String.valueOf(user.getLastName()));
                        editTxtEmailProfile.setText(String.valueOf(user.getEmail()));
                        editTxtPhoneNumberProfile.setText(String.valueOf(user.getPhoneNumber()));
                        editTxtAccountNumberProfile.setText(String.valueOf(user.getAccountNumber()));
                        editTxtTransitNumberProfile.setText(String.valueOf(user.getTransitNumber()));
                        editTxtInstituteNumberProfile.setText(String.valueOf(user.getInstituteNumber()));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ProfilePage.this, "Failed to get user data: " + databaseError.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUser() {
        user.setFirstName(editTxtFirstNameProfile.getText().toString());
        user.setLastName(editTxtLastNameProfile.getText().toString());
        user.setEmail(editTxtEmailProfile.getText().toString());
        user.setPhoneNumber(editTxtPhoneNumberProfile.getText().toString());
        user.setAccountNumber(Integer.parseInt(editTxtAccountNumberProfile.getText().toString()));
        user.setTransitNumber(Integer.parseInt(editTxtTransitNumberProfile.getText().toString()));
        user.setInstituteNumber(Integer.parseInt(editTxtInstituteNumberProfile.getText().toString()));

        databaseUsers.child(encodedEmail).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ProfilePage.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(ProfilePage.this,
                                    "Failed to update profile: " + task.getException().getMessage(), Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }

}
