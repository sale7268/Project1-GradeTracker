package com.example.project1_gradetracker.DB;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Assignment {
    @PrimaryKey
    private int AssignmentID;

    private String title;
    private String dueDate;
    private int points;
    private String category;

    public Assignment() {
        this.AssignmentID = 0;
        this.title = null;
        this.dueDate = null;
        this.points = 0;
        this.category = null;
    }

    public Assignment(int assignmentID, String title, String dueDate, int points, String category) {
        AssignmentID = assignmentID;
        this.title = title;
        this.dueDate = dueDate;
        this.points = points;
        this.category = category;
    }

    public int getAssignmentID() { return AssignmentID; }

    public void setAssignmentID(int assignmentID) { AssignmentID = assignmentID; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDueDate() { return dueDate; }

    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

    public int getPoints() { return points; }

    public void setPoints(int points) { this.points = points; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }
}
