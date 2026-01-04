package com.example.findcare.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.findcare.databinding.ActivitySuccessBinding;

public class SuccessActivity extends AppCompatActivity {

    private ActivitySuccessBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySuccessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Assuming 'doctorName' is passed via Intent or retrieved elsewhere
        // For demonstration, let's use a placeholder or retrieve from intent if
        // available
        String doctorName = getIntent().getStringExtra("doctorName"); // Example of retrieving doctorName
        if (doctorName == null) {
            doctorName = "[Doctor Name]"; // Default placeholder if not found
        }
        binding.tvSuccessMessage.setText("Your appointment with " + doctorName + " has been successfully booked.");

        binding.btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(SuccessActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void sendEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Appointment Confirmation - FindCare");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "Your appointment has been booked successfully with [Doctor Name]. Time: Tomorrow 10:00 AM.");

        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        }
    }
}
