package com.example.findcare.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.findcare.models.Appointment;
import com.example.findcare.models.Doctor;
import com.example.findcare.models.User;

@Database(entities = { User.class, Doctor.class, Appointment.class }, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract DoctorDao doctorDao();

    public abstract AppointmentDao appointmentDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "findcare_db")
                            .allowMainThreadQueries() // Simplification for prototype
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
