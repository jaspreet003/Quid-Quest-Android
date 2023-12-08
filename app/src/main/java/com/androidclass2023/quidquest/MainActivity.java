package com.androidclass2023.quidquest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the current FirebaseUser
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // If the user is already logged in, start the Dashboard activity
        if (currentUser != null) {
            Intent intent = new Intent(MainActivity.this, OnboardWelcome.class);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        TextView tvLogin = findViewById(R.id.tvLogin);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });
    }

}