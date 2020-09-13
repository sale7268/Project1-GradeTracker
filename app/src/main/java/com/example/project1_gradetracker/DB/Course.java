package com.example.project1_gradetracker.DB;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Course {
    @NonNull
    @PrimaryKey
    private int courseID;
    @NonNull
    private String title;
    @NonNull
    private String instructor;
    private String description;

    // entered as a date, converted to a string so it can be stored in the DB
    private String dateStart;
    private String dateEnd;

    @Ignore
    private List<Assignment> assignmentList;

    public Course() {
        this.courseID = 0;
        this.title = null;
        this.instructor = null;
        this.description = null;
        this.dateStart = null;
        this.dateEnd = null;
        this.assignmentList = new ArrayList<Assignment>();
    }

    public Course(int COURSE_ID, String title, String instructor, String description, String dateStart, String dateEnd, List<Assignment> assignmentList) {
        this.courseID = COURSE_ID;
        this.title = title;
        this.instructor = instructor;
        this.description = description;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.assignmentList = assignmentList;
    }


    public int getCourseID() { return courseID; }

    public void setCourseID(int courseID) { this.courseID = courseID; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public List<Assignment> getAssignmentList() { return assignmentList; }

    public void setAssignmentList(List<Assignment> assignmentList) { this.assignmentList = assignmentList; }
}
