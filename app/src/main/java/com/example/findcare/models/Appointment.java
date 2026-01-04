package com.example.findcare.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "appointments")
public class Appointment {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int userId;
    public int doctorId;
    public String date;
    public String time;
    public String condition;
    public String status; // "Pending" or "Confirmed"

    public Appointment(int userId, int doctorId, String date, String time, String condition, String status) {
        this.userId = userId;
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
        this.condition = condition;
        this.status = status;
    }
}
