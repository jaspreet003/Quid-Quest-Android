package com.androidclass2023.quidquest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class InviteEmployeeActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edEmail;
    Button btnInvite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_employee);
        intialize();
    }

    private void intialize() {
        edEmail = findViewById(R.id.edEmail);
        btnInvite = findViewById(R.id.btnInvite);
        btnInvite.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Snackbar.make(this.getCurrentFocus(), "Email has been sent!", Snackbar.LENGTH_LONG).show();

    }
}