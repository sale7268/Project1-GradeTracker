package com.example.project1_gradetracker.DB;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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

    private double totalGrade;

    @TypeConverters({ArrayListTypeConverterAssignment.class})
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

    public Course(int COURSE_ID, String title, String instructor, String description) {
        this.courseID = COURSE_ID;
        this.title = title;
        this.instructor = instructor;
        this.description = description;
        this.dateStart = "";
        this.dateEnd = "";
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

    public double getTotalGrade() {
        return totalGrade;
    }

    public void setTotalGrade(double totalGrade) {
        this.totalGrade = totalGrade;
    }

    public List<Assignment> getAssignmentList() { return assignmentList; }

    public void setAssignmentList(List<Assignment> assignmentList) { this.assignmentList = assignmentList; }

    public void addAssignment(Assignment assignment){
        this.assignmentList.add(assignment);
    }

    public void calculateTotalGrade(){
        List<Assignment> assign = getAssignmentList();
        double grades = 0,totalPoints = 0;
        for(Assignment a : assign){
            grades += a.getGrade();
            totalPoints += a.getPoints();
        }
        setTotalGrade((grades/totalPoints)*100);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return courseID == course.courseID &&
                title.equals(course.title) &&
                instructor.equals(course.instructor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseID, title, instructor);
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseID=" + courseID +
                ", title='" + title + '\'' +
                ", instructor='" + instructor + '\'' +
                ", description='" + description + '\'' +
                ", dateStart='" + dateStart + '\'' +
                ", dateEnd='" + dateEnd + '\'' +
                ", assignmentList=" + assignmentList +
                '}';
    }
}
