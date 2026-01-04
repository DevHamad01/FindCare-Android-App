package com.example.findcare.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.findcare.models.Appointment;

import java.util.List;

@Dao
public interface AppointmentDao {
    @Insert
    void insert(Appointment appointment);

    @Query("SELECT * FROM appointments WHERE userId = :userId ORDER BY id DESC")
    List<Appointment> getAppointmentsForUser(int userId);
}
