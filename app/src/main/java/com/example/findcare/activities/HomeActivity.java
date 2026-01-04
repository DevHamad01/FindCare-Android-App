package com.example.findcare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.findcare.databinding.ActivityHomeBinding;
import com.example.findcare.R;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Ensure data is present
        com.example.findcare.utils.DataSeeder.seedDoctors(this);

        binding.btnMenu.setOnClickListener(v -> {
            View popupView = getLayoutInflater().inflate(R.layout.layout_custom_menu, null);
            final android.widget.PopupWindow popupWindow = new android.widget.PopupWindow(
                    popupView,
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                    true);

            popupView.findViewById(R.id.menuLogout).setOnClickListener(view -> {
                popupWindow.dismiss();
                new com.example.findcare.utils.SessionManager(this).logout();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            });

            popupWindow.setElevation(10);
            popupWindow.showAsDropDown(v, 0, 16);
        });

        binding.btnContinue.setOnClickListener(v -> {
            String condition = binding.etCondition.getText().toString().trim();
            if (condition.isEmpty()) {
                android.widget.Toast.makeText(this, "Please enter a condition", android.widget.Toast.LENGTH_SHORT)
                        .show();
                return;
            }
            // Pass condition to list
            Intent intent = new Intent(HomeActivity.this, DoctorListActivity.class);
            intent.putExtra("CONDITION", condition);
            startActivity(intent);
        });

        setupCategoryListeners();
    }

    private void setupCategoryListeners() {
        android.view.View.OnClickListener categoryListener = v -> {
            String category = "";
            if (v.getId() == binding.catDental.getId())
                category = "Dentist";
            else if (v.getId() == binding.catCardiology.getId())
                category = "Cardiologist";
            else if (v.getId() == binding.catLungs.getId())
                category = "Pulmonologist"; // Lungs
            else if (v.getId() == binding.catBrain.getId())
                category = "Neurologist"; // Brain
            else if (v.getId() == binding.catEar.getId())
                category = "Otology"; // Ear
            else if (v.getId() == binding.catOptometry.getId())
                category = "Opthalmologist"; // Optometry
            else if (v.getId() == binding.catDermatology.getId())
                category = "Dermatologist"; // Skin
            else if (v.getId() == binding.catPediatrics.getId())
                category = "Pediatrician"; // Kids
            else if (v.getId() == binding.catOrthopedics.getId())
                category = "Orthopedist"; // Bones
            else if (v.getId() == binding.catGeneral.getId())
                category = "General Practitioner"; // General

            Intent intent = new Intent(HomeActivity.this, DoctorListActivity.class);
            intent.putExtra("CATEGORY", category);
            startActivity(intent);
        };

        binding.catDental.setOnClickListener(categoryListener);
        binding.catCardiology.setOnClickListener(categoryListener);
        binding.catLungs.setOnClickListener(categoryListener);
        binding.catBrain.setOnClickListener(categoryListener);
        binding.catEar.setOnClickListener(categoryListener);
        binding.catOptometry.setOnClickListener(categoryListener);
        binding.catDermatology.setOnClickListener(categoryListener);
        binding.catPediatrics.setOnClickListener(categoryListener);
        binding.catOrthopedics.setOnClickListener(categoryListener);
        binding.catGeneral.setOnClickListener(categoryListener);
    }
}
