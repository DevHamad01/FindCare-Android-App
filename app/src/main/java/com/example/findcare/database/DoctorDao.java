package com.example.findcare.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.findcare.models.Doctor;

import java.util.List;

@Dao
public interface DoctorDao {
    @Insert
    void insertAll(List<Doctor> doctors);

    @Query("SELECT * FROM doctors")
    List<Doctor> getAllDoctors();

    @Query("SELECT * FROM doctors WHERE (:category IS NULL OR specialization = :category) AND (:query IS NULL OR name LIKE '%' || :query || '%' OR specialization LIKE '%' || :query || '%' OR hospital LIKE '%' || :query || '%')")
    List<Doctor> filterDoctors(String category, String query);

    @Query("SELECT * FROM doctors WHERE id = :id")
    Doctor getDoctorById(int id);

    @Query("DELETE FROM doctors")
    void deleteAll();
}
