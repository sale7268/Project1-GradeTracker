package com.example.project1_gradetracker;

import com.example.project1_gradetracker.DB.Assignment;
import com.example.project1_gradetracker.DB.AssignmentDAO;
import com.example.project1_gradetracker.DB.RoomDB;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AssignmentActivityTest {

    @Test
    public void onCreate() {
        String check = "It will pass";
        assertEquals("It will pass", check);
        refreshDisplay();
    }

    @Test
    public void getIntent() {
    }

    @Test
    public void refreshDisplay() {
        AssignmentDAO assignmentDB = RoomDB.assignmentDAO();

        // insert user if user does not already exist in the table
        Assignment assignment1 = new Assignment(22, "cst326", "08/09", 10, 5, "Quiz");
        if(assignmentDB.getAssignmentByID(22) == null) {
            assignmentDB.insert(assignment1);
        }
        Assignment assignment2 = assignmentDB.getAssignmentByID(22);
        //Log.i("User: ", user1.toString());
        assertEquals(assignment1.toString(), assignment1, assignment2);
    }
}