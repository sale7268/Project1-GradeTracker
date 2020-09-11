package com.example.project1_gradetracker.DB;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Course {
    @NonNull
    @PrimaryKey
    private final int COURSE_ID;
    @NonNull
    private String title;
    @NonNull
    private String instructor;
    private String description;

    // entered as a date, converted to a string so it can be stored in the DB
    private String dateStart;
    private String dateEnd;

    public Course(int COURSE_ID, String title, String instructor, String description, String dateStart, String dateEnd) {
        this.COURSE_ID = COURSE_ID;
        this.title = title;
        this.instructor = instructor;
        this.description = description;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public int getCOURSE_ID() {
        return COURSE_ID;
    }

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
}
