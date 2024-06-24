package com.example.task;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    private TextView tvUsername, tvEmail, tvFName, tvLName;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);
        tvFName = findViewById(R.id.tvFName);
        tvLName = findViewById(R.id.tvLName);

        databaseHelper = new DatabaseHelper(this);

        String usernameEmail = getIntent().getStringExtra("USERNAME_EMAIL");

        User user = databaseHelper.getUser(usernameEmail);

        if (user != null) {
            tvUsername.setText("Username: " + user.getUsername());
            tvEmail.setText("Email: " + user.getEmail());
            tvFName.setText("First Name: " + user.getFirstName());
            tvLName.setText("Last Name: " + user.getLastName());
        }
    }
}
