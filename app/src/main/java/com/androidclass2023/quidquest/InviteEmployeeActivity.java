package com.androidclass2023.quidquest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        intialize();
    }

    private void intialize() {
        edEmail = findViewById(R.id.edEmail);
        btnInvite = findViewById(R.id.btnInvite);
        btnInvite.setOnClickListener(this);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Snackbar.make(this.getCurrentFocus(), "Email has been sent!", Snackbar.LENGTH_LONG).show();

    }
}