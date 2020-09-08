package com.example.project1_gradetracker.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDAO {
    // insert query
    @Insert
    void insert(User user);
    // delete query
    @Delete
    void delete(User user);
    // delete all query
    @Delete
    void deleteAll(List<User> users);
    // update query
    @Query("UPDATE User SET username = :sUname WHERE userID = :sID")
    void update(String sUname, String sID);
    //get all query
    @Query("SELECT * FROM User")
    List<User> getAllUsers();
}
