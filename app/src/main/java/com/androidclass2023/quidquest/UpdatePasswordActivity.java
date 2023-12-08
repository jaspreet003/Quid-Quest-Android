package com.androidclass2023.quidquest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class UpdatePasswordActivity extends AppCompatActivity {

    private EditText edNewPw, edConfirmNewPw;
    private Button btnUpdate;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize FirebaseAuth instance
        auth = FirebaseAuth.getInstance();

        // Initialize views
        edNewPw = findViewById(R.id.edNewPw);
        edConfirmNewPw = findViewById(R.id.edConfirmNewPw);
        btnUpdate = findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
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
    private void updatePassword() {
        String newPassword = edNewPw.getText().toString().trim();
        String confirmNewPassword = edConfirmNewPw.getText().toString().trim();

        if (auth.getCurrentUser() == null) {
            Toast.makeText(this, "No user is logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        if (newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
            Toast.makeText(this, "Please enter and confirm the new password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmNewPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.getCurrentUser().updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UpdatePasswordActivity.this, "Password updated successfully",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(UpdatePasswordActivity.this, "Failed to update password", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }
}
