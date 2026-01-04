package com.example.findcare;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.findcare.activities.LoginActivity;
import com.example.findcare.activities.OnboardingActivity;
import com.example.findcare.utils.SessionManager;
import com.example.findcare.utils.DataSeeder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Seed database
        DataSeeder.seedDoctors(this);

        SessionManager session = new SessionManager(this);
        if (session.isLoggedIn()) {
            if (session.isOnboardingCompleted()) {
                startActivity(new Intent(this, com.example.findcare.activities.HomeActivity.class));
            } else {
                startActivity(new Intent(this, OnboardingActivity.class));
            }
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
        finish();
    }
}