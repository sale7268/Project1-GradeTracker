package com.example.project1_gradetracker.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AssignmentDAO {
    // insert query
    @Insert
    void insert(Assignment assignment);
    // delete query
    @Delete
    void delete(Assignment assignment);
    // delete all query
    @Delete
    void deleteAll(List<Assignment> assignments);
    // update query
    @Update
    void updateAssignment(Assignment assignment);
    //get all query
    @Query("SELECT * FROM Assignment")
    List<Assignment> getAllAssignments();
}
