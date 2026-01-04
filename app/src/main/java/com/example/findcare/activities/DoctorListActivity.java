package com.example.findcare.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.findcare.database.AppDatabase;
import com.example.findcare.databinding.ActivityDoctorListBinding;
import com.example.findcare.models.Doctor;

import java.util.List;

public class DoctorListActivity extends AppCompatActivity {

    private ActivityDoctorListBinding binding;
    private AppDatabase db;
    private DoctorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = AppDatabase.getDatabase(this);

        setupRecyclerView();
        loadDoctors();

        binding.btnBack.setOnClickListener(v -> finish());
    }

    private void setupRecyclerView() {
        binding.rvDoctors.setLayoutManager(new LinearLayoutManager(this));
    }

    private void updateUI(List<Doctor> doctors) {
        if (doctors.isEmpty()) {
            binding.rvDoctors.setVisibility(android.view.View.GONE);
            binding.layoutEmptyState.setVisibility(android.view.View.VISIBLE);
            binding.tvCount.setText("0 Doctors found");
        } else {
            binding.rvDoctors.setVisibility(android.view.View.VISIBLE);
            binding.layoutEmptyState.setVisibility(android.view.View.GONE);
            binding.tvCount.setText(doctors.size() + " Doctors found");
        }

        adapter = new DoctorAdapter(this, doctors);
        binding.rvDoctors.setAdapter(adapter);
    }

    private void loadDoctors() {
        String condition = getIntent().getStringExtra("CONDITION");
        String category = getIntent().getStringExtra("CATEGORY");

        List<Doctor> doctors;

        String queryParam = (condition != null && !condition.isEmpty()) ? condition : null;
        String categoryParam = (category != null && !category.isEmpty()) ? category : null;

        if (queryParam != null) {
            binding.etSearch.setText(queryParam);
        } else if (categoryParam != null) {
            binding.etSearch.setText(categoryParam);
        }

        binding.etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH ||
                    actionId == android.view.inputmethod.EditorInfo.IME_ACTION_DONE ||
                    (event != null && event.getKeyCode() == android.view.KeyEvent.KEYCODE_ENTER
                            && event.getAction() == android.view.KeyEvent.ACTION_DOWN)) {

                String newQuery = binding.etSearch.getText().toString().trim();
                List<Doctor> newDoctors;
                if (newQuery.isEmpty()) {
                    newDoctors = db.doctorDao().getAllDoctors();
                } else {
                    newDoctors = db.doctorDao().filterDoctors(null, newQuery);
                }

                updateUI(newDoctors);
                return true;
            }
            return false;
        });

        // If both are null, it returns all
        if (queryParam == null && categoryParam == null) {
            doctors = db.doctorDao().getAllDoctors();
        } else {
            doctors = db.doctorDao().filterDoctors(categoryParam, queryParam);
        }

        updateUI(doctors);
    }

}
