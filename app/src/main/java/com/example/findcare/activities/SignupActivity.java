package com.example.findcare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.findcare.database.AppDatabase;
import com.example.findcare.databinding.ActivitySignupBinding;
import com.example.findcare.models.User;
import com.example.findcare.utils.SessionManager;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    private AppDatabase db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = AppDatabase.getDatabase(this);
        session = new SessionManager(this);

        binding.btnSignup.setOnClickListener(v -> validateSignup());
        binding.tvLogin.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void validateSignup() {
        String name = binding.etName.getText().toString().trim();
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            binding.etName.setError("Name required");
            return;
        }

        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.setError("Valid email required");
            return;
        }

        if (TextUtils.isEmpty(password) || password.length() < 6) {
            binding.etPassword.setError("Password must be at least 6 characters");
            return;
        }

        // Check if user exists
        if (db.userDao().getUserByEmail(email) != null) {
            Toast.makeText(this, "Email already exists", Toast.LENGTH_SHORT).show();
            return;
        }

        // Register
        User newUser = new User(email, password, name);
        db.userDao().insert(newUser);

        // Auto login
        User insertedUser = db.userDao().getUserByEmail(email); // Get ID
        if (insertedUser != null) {
            session.createLoginSession(insertedUser.id, insertedUser.name);
            // Navigate to Onboarding
            startActivity(new Intent(SignupActivity.this, OnboardingActivity.class));
            finishAffinity();
        }
    }
}