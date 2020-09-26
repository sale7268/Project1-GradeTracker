package com.example.project1_gradetracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project1_gradetracker.DB.Assignment;
import com.example.project1_gradetracker.DB.AssignmentDAO;
import com.example.project1_gradetracker.DB.Course;
import com.example.project1_gradetracker.DB.CourseDAO;

import java.util.List;

import static com.example.project1_gradetracker.AssignmentActivity.CATEGORY;
import static com.example.project1_gradetracker.AssignmentActivity.COURSE_ID;
import static com.example.project1_gradetracker.LoginActivity.USER_NAME;
import static com.example.project1_gradetracker.LoginActivity.database;


public class DeleteAssignmentActivity extends AppCompatActivity {

    private EditText deleteAssignmentID;
    private Button deleteAssignment;

    public static AssignmentDAO assignmentDAO;
    List<Assignment> assignmentList;
    public static CourseDAO courseDAO;
    List<Course> courseList;
    List<Assignment> assignmentCourseList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_assignment);

        Bundle bundle = getIntent().getExtras();
        final String user_name = bundle.getString(USER_NAME);
        final int course_id = bundle.getInt(COURSE_ID);
        final String category = bundle.getString(CATEGORY);


        //Getting course database
        courseDAO = database.courseDAO();
        courseList = courseDAO.getAllCourses();
        Course course = null;

        // find the course data
        for(Course c : courseList) {
            if (c.getCourseID() == course_id) {
                course = c;
                break;
            }
        }
        if(course == null){
            Toast.makeText(DeleteAssignmentActivity.this, "no course found", Toast.LENGTH_SHORT).show();
        }

        deleteAssignmentID = findViewById(R.id.tvDeleteAssignmentID);
        deleteAssignment = findViewById(R.id.btDeleteA);

        // Assignment Database
        assignmentDAO = database.assignmentDAO();
        assignmentList = assignmentDAO.getAllAssignments();
        assignmentCourseList = course.getAssignmentList();

        final Course finalCourse = course;

        if(deleteAssignmentID.getText().toString().isEmpty()){
            return;
        }

        deleteAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(deleteAssignmentID.getText().toString());
                String title = "";
                boolean assignmentExit = false;
                for(Assignment a: assignmentList){
                    if(a.getAssignmentID() == id){
                        title = a.getTitle();
                        assignmentDAO.delete(a);
                        assignmentExit =true;
                        Toast.makeText(DeleteAssignmentActivity.this, "Assignment: " + title + " Successfully deleted", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

                int index = 0;
                for(Assignment a: assignmentCourseList){
                    if(a.getAssignmentID() == id){
                        assignmentCourseList.remove(index);
                        courseDAO.update(finalCourse);
                        assignmentExit = true;
                        Toast.makeText(DeleteAssignmentActivity.this, "Assignment: " + title + " Successfully deleted", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    index++;
                }

                if(!assignmentExit) {
                    Toast.makeText(DeleteAssignmentActivity.this, "Assignment: " + title + " doesn't exist", Toast.LENGTH_SHORT).show();
                }

                Intent i = AssignmentActivity.getIntent(getApplicationContext(), user_name, course_id, category);
                startActivity(i);
            }
        });
    }



    public static Intent getIntent(Context context, String username, int course, String category){
        Bundle bundle = new Bundle();
        bundle.putString(USER_NAME, username);
        bundle.putString(CATEGORY, category);
        bundle.putInt(COURSE_ID, course);

        Intent intent = new Intent(context, DeleteAssignmentActivity.class);
        intent.putExtras(bundle);

        return intent;
    }
}