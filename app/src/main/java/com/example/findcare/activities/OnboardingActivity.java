package com.example.findcare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.findcare.R;
import com.example.findcare.databinding.ActivityOnboardingBinding;

public class OnboardingActivity extends AppCompatActivity {

    private ActivityOnboardingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnboardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Setup click listeners for options (Selection Logic)
        setupOptionSelection(binding.cardQ1Opt1, binding.cardQ1Opt2, binding.cardQ1Opt3);
        setupOptionSelection(binding.cardQ2Opt1, binding.cardQ2Opt2);

        binding.btnNext.setOnClickListener(v -> {
            int current = binding.viewFlipper.getDisplayedChild();
            int count = binding.viewFlipper.getChildCount();

            // Validation: Check if current step has selection
            if (!isOptionSelected(current)) {
                android.widget.Toast.makeText(this, "Please select an option", android.widget.Toast.LENGTH_SHORT)
                        .show();
                return;
            }

            if (current < count - 2) { // Allow next until second last screen
                binding.viewFlipper.showNext();
            } else if (current == count - 2) {
                // Moving to Result Screen (Last index)
                binding.viewFlipper.showNext();
                showLoadingResults();
            }
        });

        binding.btnSeeResults.setOnClickListener(v -> {
            new com.example.findcare.utils.SessionManager(this).setOnboardingCompleted(true);
            startActivity(new Intent(OnboardingActivity.this, HomeActivity.class));
            finish();
        });
    }

    // Helper to simulate radio button behavior for layouts
    private void setupOptionSelection(LinearLayout... options) {
        for (LinearLayout option : options) {
            option.setOnClickListener(v -> {
                // Reset all
                for (LinearLayout opt : options) {
                    opt.setBackgroundResource(R.drawable.bg_option_card);
                }
                // Select clicked
                // Select clicked
                v.setBackgroundResource(R.drawable.bg_option_card_selected);
                v.setTag("SELECTED"); // Mark as selected
            });
        }
    }

    private boolean isOptionSelected(int stepIndex) {
        if (stepIndex == 0) {
            return isSelected(binding.cardQ1Opt1, binding.cardQ1Opt2, binding.cardQ1Opt3);
        } else if (stepIndex == 1) {
            return isSelected(binding.cardQ2Opt1, binding.cardQ2Opt2);
        }
        return true; // No validation needed for other steps (like loading)
    }

    private boolean isSelected(LinearLayout... options) {
        for (LinearLayout opt : options) {
            if (opt.getTag() != null && opt.getTag().equals("SELECTED")) {
                return true;
            }
        }
        return false;
    }

    private void showLoadingResults() {
        // Hide "Next" button
        binding.btnNext.setVisibility(View.GONE);

        // Show Loading
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.layoutResults.setVisibility(View.GONE);

        // Simulate delay
        new Handler().postDelayed(() -> {
            binding.progressBar.setVisibility(View.GONE);
            binding.layoutResults.setVisibility(View.VISIBLE);
            binding.btnSeeResults.setVisibility(View.VISIBLE);
        }, 2000); // 2 seconds
    }
}
