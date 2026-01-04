package com.example.findcare.activities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findcare.databinding.ItemDoctorCardBinding;
import com.example.findcare.models.Doctor;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {

    private Context context;
    private List<Doctor> doctorList;

    public DoctorAdapter(Context context, List<Doctor> doctorList) {
        this.context = context;
        this.doctorList = doctorList;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDoctorCardBinding binding = ItemDoctorCardBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new DoctorViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        Doctor doctor = doctorList.get(position);
        holder.binding.tvName.setText(doctor.name);
        holder.binding.tvSpecialty.setText(doctor.specialization);
        holder.binding.tvRating.setText("★ " + doctor.rating);
        // holder.binding.tvName.setText(doctor.experience + " Yrs Exp"); // This was
        // overwriting name!
        // Where to show experience? Layout has tvExp? item_doctor_card didn't show
        // distinct tvExp.
        // item_doctor_card has tvName, tvSpecialty, tvRating, tvPrice.
        // It has a "4 Slots Available" text.
        // I will just leave name as name.
        holder.binding.tvPrice.setText("$" + doctor.price + "/hr");

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DoctorDetailActivity.class);
            intent.putExtra("DOCTOR_ID", doctor.id);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    static class DoctorViewHolder extends RecyclerView.ViewHolder {
        ItemDoctorCardBinding binding;

        public DoctorViewHolder(ItemDoctorCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
