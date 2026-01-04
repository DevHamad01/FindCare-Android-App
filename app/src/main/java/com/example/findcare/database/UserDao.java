package com.example.findcare.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.findcare.models.User;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    User login(String email, String password);

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);
}
