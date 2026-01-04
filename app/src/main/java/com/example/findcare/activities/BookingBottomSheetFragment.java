package com.example.findcare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.findcare.database.AppDatabase;
import com.example.findcare.databinding.FragmentBookingBottomSheetBinding;
import com.example.findcare.models.Appointment;
import com.example.findcare.utils.SessionManager;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BookingBottomSheetFragment extends BottomSheetDialogFragment {

    private FragmentBookingBottomSheetBinding binding;
    private int doctorId;
    private String doctorName;
    private AppDatabase db;
    private SessionManager session;

    public static BookingBottomSheetFragment newInstance(int doctorId, String doctorName) {
        BookingBottomSheetFragment fragment = new BookingBottomSheetFragment();
        Bundle args = new Bundle();
        args.putInt("DOCTOR_ID", doctorId);
        args.putString("DOCTOR_NAME", doctorName);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentBookingBottomSheetBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            doctorId = getArguments().getInt("DOCTOR_ID");
            doctorName = getArguments().getString("DOCTOR_NAME");
        }

        db = AppDatabase.getDatabase(requireContext());
        session = new SessionManager(requireContext());

        binding.btnConfirm.setOnClickListener(v -> confirmBooking());
        binding.btnCancel.setOnClickListener(v -> dismiss());
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().findViewById(com.google.android.material.R.id.design_bottom_sheet)
                    .setBackgroundResource(android.R.color.transparent);
        }
    }

    private void confirmBooking() {
        int userId = session.getUserId();
        if (userId == -1) {
            Toast.makeText(getContext(), "Error: User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        Appointment appointment = new Appointment(userId, doctorId, "Tomorrow", "10:00 AM", "General", "Confirmed");
        db.appointmentDao().insert(appointment);

        Intent intent = new Intent(requireContext(), SuccessActivity.class);
        intent.putExtra("doctorName", doctorName);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
