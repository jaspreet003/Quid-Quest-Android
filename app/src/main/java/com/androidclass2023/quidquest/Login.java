package com.androidclass2023.quidquest;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth auth;
    private EditText emailField, passwordField;
    private TextView txtVForgotPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        emailField = findViewById(R.id.editTxtEmailLogin);
        passwordField = findViewById(R.id.editTxtPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        txtVForgotPassword = findViewById(R.id.txtVForgotPasswordLogin);

        btnLogin.setOnClickListener(this);
        txtVForgotPassword.setOnClickListener(this);
    }
    public void onClick(View v) {
        if(v.getId()==R.id.btnLogin){
            signIn(v);
        }
        if(v.getId()==R.id.txtVForgotPasswordLogin){
            Intent intent = new Intent(Login.this, ForgotPassword.class);
            startActivity(intent);
        }
    }
    public void signIn(View view) {
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Login.this, "Authentication successful.",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, ManagerDashboardActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Login.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}