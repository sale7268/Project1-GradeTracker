package com.example.project1_gradetracker.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDAO {
    // insert query
    @Insert
    void insert(User user);
    // update query
    @Update
    void update(User user);
    @Delete
    void delete(User user);
    // delete all query
    @Delete
    void deleteAll(List<User> users);
    // update username
    @Query("UPDATE User SET username = :sUname WHERE userID = :sID")
    void updateUsername(String sUname, String sID);
    // update password
    @Query("UPDATE User SET password = :sPassword WHERE userID = :sID")
    void updatePassword(String sPassword, String sID);
    //get all query
    @Query("SELECT * FROM User")
    List<User> getAllUsers();
    //get user by ID
    @Query("SELECT * FROM User WHERE userID = :sID")
    User getUserByID(String sID);
}
