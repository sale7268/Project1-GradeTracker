package com.example.project1_gradetracker.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDAO {
    // insert query
    @Insert
    void insert(Course course);
    // update query
    @Update
    void update(Course course);
    // delete query
    @Delete
    void delete(Course course);
    // delete all query
    @Delete
    void deleteAll(List<Course> courses);
    // update query
    @Query("UPDATE Course SET title = :cTitle WHERE courseID = :cID")
    void updateTitle(String cTitle, int cID);
    @Query("UPDATE Course SET instructor = :cInstructor WHERE courseID = :cID")
    void updateInstructor(String cInstructor, int cID);
    @Query("UPDATE Course SET title = :cDesc WHERE courseID = :cID")
    void updateDescription(String cDesc, int cID);
    //get all query
    @Query("SELECT * FROM Course")
    List<Course> getAllCourses();
    @Query("SELECT * FROM Course WHERE courseID = :cID")
    Course getCourseByID(int cID);
}
