package com.androidclass2023.quidquest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    private TextView txtEmailSent;
    private Button btnSubmit;
    private EditText editTxtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initialize();
    }

    private void initialize() {
        txtEmailSent = findViewById(R.id.txtVEmailSent);
        btnSubmit = findViewById(R.id.btnSubmitForgotPassword);
        editTxtEmail = findViewById(R.id.editTxtEmailForgotPassword);
        btnSubmit.setOnClickListener(this);

        txtEmailSent.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSubmitForgotPassword) {
            String email = editTxtEmail.getText().toString();

            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPassword.this, "Email sent.",
                                    Toast.LENGTH_SHORT).show();
                            txtEmailSent.setVisibility(View.VISIBLE);
                            finish();

                        } else {
                            Toast.makeText(ForgotPassword.this, "Failed to send email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}