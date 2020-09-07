package com.example.project1_gradetracker.DB;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity
public class Course {

    @PrimaryKey
    private int courseID;

    private String title;
    private String instructor;
    private String description;
    private String catagory;

    private LocalDate dateStart;
    private LocalDate dateEnd;
}
