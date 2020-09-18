package com.example.project1_gradetracker.DB;

import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.util.Objects;

@Entity(
        primaryKeys = {"assignmentID", "courseID"},
        foreignKeys = @ForeignKey(entity = Course.class,
                parentColumns = "courseID",
                childColumns = "courseID",
                onDelete = ForeignKey.CASCADE)
        )
public class Assignment {
    private int assignmentID;
    private int courseID;

    private String title;
    private String dueDate;
    private int points;
    private int grade;
    private String category;

    public Assignment() {
        this.courseID = 0;
        this.assignmentID = 0;
        this.title = null;
        this.dueDate = null;
        this.points = 0;
        this.grade = 0;
        this.category = null;
    }

    public Assignment(int courseID, int assignmentID, String title, String dueDate, int points, int grade, String category) {
        this.courseID = courseID;
        this.assignmentID = assignmentID;
        this.title = title;
        this.dueDate = dueDate;
        this.points = points;
        this.grade = grade;
        this.category = category;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getAssignmentID() { return assignmentID; }

    public void setAssignmentID(int assignmentID) { this.assignmentID = assignmentID; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDueDate() { return dueDate; }

    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

    public int getPoints() { return points; }

    public void setPoints(int points) { this.points = points; }

    public int getGrade() { return grade; }

    public void setGrade(int grade) { this.grade = grade; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignment that = (Assignment) o;
        return assignmentID == that.assignmentID &&
                title.equals(that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignmentID, title);
    }
}
