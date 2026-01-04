package com.example.findcare.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "doctors")
public class Doctor {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String specialization;
    public String hospital;
    public float rating;
    public int experience; // Years
    public double price; // Consultation fee
    public int imageResId; // Resource ID for placeholder
    public String imageUrl; // Optional: URL for network image
    public String about; // Professional About section

    public Doctor(String name, String specialization, String hospital, float rating, int experience, double price,
            int imageResId, String about) {
        this.name = name;
        this.specialization = specialization;
        this.hospital = hospital;
        this.rating = rating;
        this.experience = experience;
        this.price = price;
        this.imageResId = imageResId;
        this.about = about;
    }
}
