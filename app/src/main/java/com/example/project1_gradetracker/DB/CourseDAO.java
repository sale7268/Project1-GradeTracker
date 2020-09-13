package com.example.project1_gradetracker.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CourseDAO {
    // insert query
    @Insert
    void insert(Course course);
    // delete query
    @Delete
    void delete(Course course);
    // delete all query
    @Delete
    void deleteAll(List<Course> courses);
    // update query
    @Query("UPDATE Course SET title = :cTitle WHERE courseID = :cID") // TODO: fix this
    void update(String cTitle, String cID);
    //get all query
    @Query("SELECT * FROM Course")
    List<Course> getAllCourses();
}
