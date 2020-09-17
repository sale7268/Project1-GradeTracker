package com.example.project1_gradetracker.DB;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*@Entity(foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "courseID",
        childColumns = "COURSE_ID",
        onDelete = ForeignKey.CASCADE))*/
@Entity
public class User {

    // AS wont let me make userID final because it wont build without a setter but
    // you cant set a final field
    @NonNull
    @PrimaryKey
    public String userID;
    @NonNull
    private String username;
    @NonNull
    private String password;

//    @TypeConverter(ArrayListTypeConverter.class)
    @Ignore
    private List<Course> courseList;
// json string to be stored in DB
    public User(){
        this.userID = "USER_ID";
        this.username = "username";
        this.password = "password";
        this.courseList = new ArrayList<Course>();
    }

    public User(String USER_ID, String username, String password) {
        this.userID = USER_ID;
        this.username = username;
        this.password = password;
        this.courseList = new ArrayList<Course>();
    }

    public User(String USER_ID, String username, String password, List<Course> courseList) {
        this.userID = USER_ID;
        this.username = username;
        this.password = password;
        this.courseList = courseList;
    }

    public String getUSER_ID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public void addCourse(Course course){
        this.courseList.add(course);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userID.equals(user.userID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID);
    }

    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", courseList=" + courseList +
                '}';
    }
}
