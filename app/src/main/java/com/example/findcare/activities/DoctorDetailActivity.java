package com.example.findcare.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.findcare.database.AppDatabase;
import com.example.findcare.databinding.ActivityDoctorDetailBinding;
import com.example.findcare.models.Doctor;
import com.example.findcare.R;

public class DoctorDetailActivity extends AppCompatActivity {

    private ActivityDoctorDetailBinding binding;
    private AppDatabase db;
    private int doctorId;
    private Doctor doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = AppDatabase.getDatabase(this);
        doctorId = getIntent().getIntExtra("DOCTOR_ID", -1);

        if (doctorId != -1) {
            doctor = db.doctorDao().getDoctorById(doctorId);
            if (doctor != null) {
                populateUI(doctor);
            }
        }

        binding.btnBack.setOnClickListener(v -> finish());

        setupTabs();
        setupAvailability();
    }

    private String selectedDate = null;

    private void setupAvailability() {
        binding.containerAvailability.removeAllViews();

        java.util.Calendar calendar = java.util.Calendar.getInstance();
        java.text.SimpleDateFormat dayFormat = new java.text.SimpleDateFormat("EEE", java.util.Locale.getDefault());
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd", java.util.Locale.getDefault());
        java.text.SimpleDateFormat fullDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd",
                java.util.Locale.getDefault());

        // Ensure future dates (start from tomorrow for better UX or today? Request says
        // "Only future dates allowed")
        // "Already booked dates: BLACK, Not selectable"
        // "Available dates: WHITE"

        calendar.add(java.util.Calendar.DAY_OF_YEAR, 1); // Start tomorrow

        for (int i = 0; i < 7; i++) {
            android.view.View dateView = getLayoutInflater().inflate(com.example.findcare.R.layout.item_date_slot,
                    binding.containerAvailability, false);

            android.widget.TextView tvDay = dateView.findViewById(com.example.findcare.R.id.tvDay);
            android.widget.TextView tvDate = dateView.findViewById(com.example.findcare.R.id.tvDate);
            android.widget.LinearLayout root = dateView.findViewById(com.example.findcare.R.id.rootLayout);

            tvDay.setText(dayFormat.format(calendar.getTime()));
            tvDate.setText(dateFormat.format(calendar.getTime()));
            String dateString = fullDateFormat.format(calendar.getTime());

            // Simulate availability: Random occupied/free
            boolean isFree = Math.random() > 0.3; // 70% chance free

            if (isFree) {
                root.setBackgroundResource(com.example.findcare.R.drawable.bg_date_slot_free); // White background
                tvDay.setTextColor(getResources().getColor(com.example.findcare.R.color.textColorSecondary));
                tvDate.setTextColor(getResources().getColor(com.example.findcare.R.color.textColorPrimary));

                root.setOnClickListener(v -> {
                    // Reset selection visuals
                    for (int j = 0; j < binding.containerAvailability.getChildCount(); j++) {
                        android.view.View child = binding.containerAvailability.getChildAt(j);
                        // Restore original available style if not booked (booked ones are
                        // unclickable/different style)
                        if (child.getTag() != null && child.getTag().equals("BOOKED"))
                            continue;

                        child.setBackgroundResource(com.example.findcare.R.drawable.bg_date_slot_free);
                    }
                    // Select this one
                    root.setBackgroundResource(com.example.findcare.R.drawable.bg_option_card_selected); // Or some
                                                                                                         // selected
                                                                                                         // state
                    selectedDate = dateString;
                });
            } else {
                root.setBackgroundResource(com.example.findcare.R.drawable.bg_date_slot_occupied); // Black background
                tvDay.setTextColor(getResources().getColor(com.example.findcare.R.color.white));
                tvDate.setTextColor(getResources().getColor(com.example.findcare.R.color.white));
                root.setTag("BOOKED");
                root.setOnClickListener(null); // Not selectable
            }

            binding.containerAvailability.addView(dateView);

            // Advance day
            calendar.add(java.util.Calendar.DAY_OF_YEAR, 1);
        }

        // Update booking click
        binding.btnBookAppointment.setOnClickListener(v -> {
            if (selectedDate == null) {
                Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
                // Redirect to tab if needed
                selectTab(2);
                return;
            }
            if (doctor != null) {
                BookingBottomSheetFragment bottomSheet = BookingBottomSheetFragment.newInstance(doctor.id, doctor.name);
                bottomSheet.show(getSupportFragmentManager(), "BookingBottomSheet");
            }
        });
    }

    private void populateUI(Doctor doctor) {
        binding.tvName.setText(doctor.name);
        binding.tvSpecialty.setText(doctor.specialization); // + " - " + doctor.hospital
        binding.tvRating.setText(String.valueOf(doctor.rating));
        binding.tvExp.setText(doctor.experience + " Yrs");

        // Expanded "About" text to demonstrate scrolling
        String expandedAbout = doctor.about + "\n\n" +
                "Dr. " + doctor.name
                + " is a highly skilled medical professional with extensive experience in diagnosing and treating patients. "
                +
                "Committed to providing compassionate care and improving patient outcomes, Dr. " + doctor.name
                + " has a proven track record of accurate diagnosis " +
                "and effective treatment planning. \n\n" +
                "Specializing in " + doctor.specialization
                + ", the doctor stays up-to-date with the latest medical advancements and technologies. " +
                "Patients appreciate the doctor's approachable demeanor and ability to explain complex medical conditions in simple terms. "
                +
                "Whether you need a routine check-up or specialized care, Dr. " + doctor.name
                + " is dedicated to helping you achieve optimal health.\n\n" +
                "Education & Training:\n" +
                "- Medical Degree from Top Medical University\n" +
                "- Residency at General Hospital\n" +
                "- Fellowship in " + doctor.specialization + "\n\n" +
                "Certifications:\n" +
                "- Board Certified in " + doctor.specialization + "\n" +
                "- Member of Medical Association";

        binding.tvAbout.setText(expandedAbout);

        binding.tvHeaderRating.setText(String.valueOf(doctor.rating));
        binding.tvHeaderPrice.setText("$" + doctor.price + "/hr");

        populateReviews();
    }

    private void populateReviews() {
        binding.containerReviews.removeAllViews();
        // Dummy reviews
        String[] users = { "Alice M.", "Bob K.", "Charlie D." };
        String[] comments = { "Great doctor, very understanding and patient.",
                "Helped me a lot with my anxiety. I feel much better now.",
                "Highly recommended! Very professional and kind." };
        float[] ratings = { 5.0f, 4.5f, 5.0f };

        for (int i = 0; i < users.length; i++) {
            // Enhanced Review Card (Text Only)
            androidx.cardview.widget.CardView card = new androidx.cardview.widget.CardView(this);
            android.widget.LinearLayout.LayoutParams cardParams = new android.widget.LinearLayout.LayoutParams(
                    android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                    android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
            cardParams.setMargins(0, 0, 0, 24);
            card.setLayoutParams(cardParams);
            card.setCardBackgroundColor(getResources().getColor(R.color.white));
            card.setRadius(24f);
            card.setCardElevation(2f); // Mild elevation
            card.setContentPadding(40, 40, 40, 40);

            android.widget.LinearLayout item = new android.widget.LinearLayout(this);
            item.setOrientation(android.widget.LinearLayout.VERTICAL);

            // Header Row: Name (Left) ... Rating (Right)
            android.widget.LinearLayout header = new android.widget.LinearLayout(this);
            header.setOrientation(android.widget.LinearLayout.HORIZONTAL);
            header.setGravity(android.view.Gravity.CENTER_VERTICAL);
            header.setLayoutParams(new android.widget.LinearLayout.LayoutParams(
                    android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                    android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));

            // Name
            android.widget.TextView tvUser = new android.widget.TextView(this);
            tvUser.setText(users[i]);
            tvUser.setTypeface(null, android.graphics.Typeface.BOLD);
            tvUser.setTextColor(getResources().getColor(R.color.textColorPrimary));
            tvUser.setTextSize(18f); // Larger
            android.widget.LinearLayout.LayoutParams nameParams = new android.widget.LinearLayout.LayoutParams(
                    0, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            tvUser.setLayoutParams(nameParams);
            header.addView(tvUser);

            // Rating
            android.widget.TextView tvRating = new android.widget.TextView(this);
            tvRating.setText("★ " + ratings[i]);
            tvRating.setTextColor(getResources().getColor(R.color.black));
            tvRating.setTypeface(null, android.graphics.Typeface.BOLD);
            tvRating.setTextSize(16f);
            header.addView(tvRating);

            item.addView(header);

            // Comment
            android.widget.TextView tvComment = new android.widget.TextView(this);
            tvComment.setText(comments[i]);
            tvComment.setTextColor(getResources().getColor(R.color.textColorSecondary));
            tvComment.setTextSize(15f);
            tvComment.setLineSpacing(0, 1.2f); // Better readability
            android.widget.LinearLayout.LayoutParams commentParams = new android.widget.LinearLayout.LayoutParams(
                    android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                    android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
            commentParams.setMargins(0, (int) (12 * getResources().getDisplayMetrics().density), 0, 0);
            tvComment.setLayoutParams(commentParams);

            item.addView(tvComment);
            card.addView(item);

            binding.containerReviews.addView(card);
        }
    }

    private void setupTabs() {
        // Default state: About selected
        selectTab(0);

        binding.tabAbout.setOnClickListener(v -> selectTab(0));
        binding.tabReviews.setOnClickListener(v -> selectTab(1));
        binding.tabAvailability.setOnClickListener(v -> selectTab(2));
    }

    private void selectTab(int index) {
        // Reset all tabs to default style
        binding.tabAbout.setTextColor(getResources().getColor(android.R.color.darker_gray));
        binding.tabReviews.setTextColor(getResources().getColor(android.R.color.darker_gray));
        binding.tabAvailability.setTextColor(getResources().getColor(android.R.color.darker_gray));

        binding.tabAbout.setTypeface(null, android.graphics.Typeface.NORMAL);
        binding.tabReviews.setTypeface(null, android.graphics.Typeface.NORMAL);
        binding.tabAvailability.setTypeface(null, android.graphics.Typeface.NORMAL);

        binding.layoutAbout.setVisibility(android.view.View.GONE);
        binding.layoutReviews.setVisibility(android.view.View.GONE);
        binding.layoutAvailability.setVisibility(android.view.View.GONE);
        binding.tvAvailabilityHint.setVisibility(android.view.View.GONE);
        binding.layoutAvailability.scrollTo(0, 0);

        // Highlight selected tab
        if (index == 0) {
            binding.tabAbout.setTextColor(getResources().getColor(com.example.findcare.R.color.textColorPrimary));
            binding.tabAbout.setTypeface(null, android.graphics.Typeface.BOLD);
            binding.layoutAbout.setVisibility(android.view.View.VISIBLE);
        } else if (index == 1) {
            binding.tabReviews.setTextColor(getResources().getColor(com.example.findcare.R.color.textColorPrimary));
            binding.tabReviews.setTypeface(null, android.graphics.Typeface.BOLD);
            binding.layoutReviews.setVisibility(android.view.View.VISIBLE);
        } else if (index == 2) {
            binding.tabAvailability
                    .setTextColor(getResources().getColor(com.example.findcare.R.color.textColorPrimary));
            binding.tabAvailability.setTypeface(null, android.graphics.Typeface.BOLD);
            binding.layoutAvailability.setVisibility(android.view.View.VISIBLE);
            binding.tvAvailabilityHint.setVisibility(android.view.View.VISIBLE);
        }
    }

}
