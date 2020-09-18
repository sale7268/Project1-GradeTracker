package com.example.project1_gradetracker;

import android.content.Context;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.project1_gradetracker.DB.Course;
import com.example.project1_gradetracker.DB.CourseDAO;
import com.example.project1_gradetracker.DB.RoomDB;
import com.example.project1_gradetracker.DB.User;
import com.example.project1_gradetracker.DB.UserDAO;
import com.example.project1_gradetracker.DB.Assignment;
import com.example.project1_gradetracker.DB.AssignmentDAO;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AssignmentDAO_Test {
    @Test
    public void AssignmentInsertTest(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        //AssignmentDAO assignmentDAO = RoomDB.getInstance(appContext).assignmentDAO();
        AssignmentDAO myTestDB = Room.databaseBuilder(appContext, RoomDB.class, RoomDB.DB_Name)
                .allowMainThreadQueries()
                .build()
                .assignmentDAO();

        // Ceate an assignment
        Assignment assignment = new Assignment(438, 101, "Homework101", "9/18/2020", 100, 100, "Homework");
        // Delet the assignmnet in the lsit, if it already exit
        myTestDB.delete(assignment);
        // Insert the assignment
        myTestDB.insert(assignment);
        // Create another assignment same id with first one
        Assignment assignment2 = myTestDB.getAssignmentByID(assignment.getAssignmentID());
        // Assignment are equal assignment2
        assertEquals(assignment, assignment2);
        // Change the title of assignment2
        assignment2.setTitle("Project202");
        // Update assignment in dataabse
        myTestDB.update(assignment2);
        // Assignment3 has the new title from database
        Assignment assignment3 = myTestDB.getAssignmentByID(assignment.getAssignmentID());
        // Assignment1 has the original password and assignment3 has the password we update
        assertNotEquals(assignment, assignment3);
        // Assignment2 has the new title and assignment3 has the new title because it pull out of the database
        assertEquals(assignment2, assignment3);

    }

}
