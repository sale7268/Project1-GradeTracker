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
import com.example.project1_gradetracker.DB.User;

import java.util.List;

import static com.example.project1_gradetracker.AssignmentActivity.CATEGORY;
import static com.example.project1_gradetracker.AssignmentActivity.COURSE_ID;
import static com.example.project1_gradetracker.LoginActivity.USER_NAME;
import static com.example.project1_gradetracker.LoginActivity.database;
import static com.example.project1_gradetracker.CreateCourseActivity.courseDAO;

public class EditAssignmentActivity extends AppCompatActivity {

    private EditText edTitle, edID, edDue, edPoint, edEarned, edCategory;
    private Button editButton;

    List<Assignment> assignmentList;
    List<Course> courseList;
    public static AssignmentDAO assignmentDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assignment);

        Bundle bundle = getIntent().getExtras();
        final String user_name = bundle.getString(USER_NAME);
        final int course_id = bundle.getInt(COURSE_ID);
        final String category = bundle.getString(CATEGORY);

        courseDAO = database.courseDAO();
        courseList = courseDAO.getAllCourses();
        assignmentDAO = database.assignmentDAO();
        assignmentList = assignmentDAO.getAllAssignments();

        Course currentCourse = null;
        for(Course c : courseList){
            if(c.getCourseID() == course_id){
                currentCourse = c;
                break;
            }
        }
        if(currentCourse == null){
            Toast.makeText(EditAssignmentActivity.this, "Course Not Found", Toast.LENGTH_SHORT).show();
        }

//        Assignment currentAssignment = currentCourse.getAssignmentList().get

        // Initialize edit text, button, radio group
        edTitle = findViewById(R.id.etTitleEd);
        edID = findViewById(R.id.etIDEd);
        edDue = findViewById(R.id.etDueEd);
        edPoint = findViewById(R.id.etPointEd);
        edEarned = findViewById(R.id.etEarnedEd);
        edCategory = findViewById(R.id.etCategoryEd);
        editButton = findViewById(R.id.btEditAssignment);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Find User
                Intent i = getIntent();
                String user_name = i.getStringExtra(USER_NAME);
                User user = null;

                for (User u : database.userDAO().getAllUsers()) {
                    if (u.getUsername().equals(user_name)) {
                        user = u;
                        break;
                    }
                }
                if (user == null) {
                    Toast.makeText(EditAssignmentActivity.this, "no user found", Toast.LENGTH_SHORT).show();
                }

                // Assign editText to local variables
                int id = Integer.parseInt(edID.getText().toString());
                String title = edTitle.getText().toString();
                String due = edDue.getText().toString();
                int point = Integer.parseInt(edPoint.getText().toString());
                int earned = Integer.parseInt(edEarned.getText().toString());
                String category = edCategory.getText().toString();
                boolean checkassignment = false;

                // Check if fields are empty
                if (edDue.getText().toString().isEmpty()) {
                    edDue.setError("Duedate field empty");
//                    due =
                }
                if(edCategory.getText().toString().isEmpty()) {
                    edCategory.setError("Category field empty");
                }
                if(edID.getText().toString().isEmpty()){
                    edID.setError("ID field empty");
                }
                if(edTitle.getText().toString().isEmpty()){
                    edTitle.setError("Title field empty");
                }
                if(edPoint.getText().toString().isEmpty()){
                    edPoint.setError("Points field empty");
                }
                if(edEarned.getText().toString().isEmpty()){
                    edEarned.setError("Grade field empty");
                }

                for(Assignment a: assignmentList){
                    // Check assignmnet's id if assignment exit
                    if(a.getAssignmentID() == id){
                        Assignment assignment = new Assignment(user.getUSER_ID(), course_id, id, title, due, point, earned, category);
                        assignmentDAO.updateAssignment(assignment);
                        Toast.makeText(EditAssignmentActivity.this, "Assignment: " + title + " [" + id +"] " + " Successfully edited", Toast.LENGTH_SHORT).show();
                        checkassignment = true;
                    }
                }
                if(!checkassignment){
                    Toast.makeText(EditAssignmentActivity.this, "Assignment: " + title + " [" + id +"] " + " Doesn't exist!", Toast.LENGTH_SHORT).show();
                }

                //Go back to overall grade activity
                Intent intent = AssignmentActivity.getIntent(getApplicationContext(), user_name, course_id, category);
                startActivity(intent);
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