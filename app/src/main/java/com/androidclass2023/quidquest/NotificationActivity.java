package com.androidclass2023.quidquest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import adapters.NotificationAdapter;
import model.Notification;
public class NotificationActivity extends AppCompatActivity {

    List<Notification> notifications = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        initialize();
    }

    private void initialize() {
        notifications.add(new Notification("New Expense Added", "Sandeep Singh added travel expense", false));
        notifications.add(new Notification("New Expense Added", "Sandeep Singh added travel expense", false));
        RecyclerView recyclerView = findViewById(R.id.notification_recyclerView);
        NotificationAdapter adapter = new NotificationAdapter(notifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

}