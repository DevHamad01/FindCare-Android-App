package com.example.findcare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.findcare.database.AppDatabase;
import com.example.findcare.databinding.ActivityLoginBinding;
import com.example.findcare.models.User;
import com.example.findcare.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private AppDatabase db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = AppDatabase.getDatabase(this);
        session = new SessionManager(this);

        if (session.isLoggedIn()) {
            if (session.isOnboardingCompleted()) {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            } else {
                startActivity(new Intent(LoginActivity.this, OnboardingActivity.class));
            }
            finish();
        }

        binding.btnLogin.setOnClickListener(v -> validateLogin());
        binding.tvSignup.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        });
    }

    private void validateLogin() {
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            binding.etEmail.setError("Email required");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            binding.etPassword.setError("Password required");
            return;
        }

        User user = db.userDao().login(email, password);
        if (user != null) {
            session.createLoginSession(user.id, user.name);
            startActivity(new Intent(LoginActivity.this, OnboardingActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
        }
    }
}