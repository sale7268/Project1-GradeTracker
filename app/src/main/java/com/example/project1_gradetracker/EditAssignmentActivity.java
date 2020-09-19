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

import static com.example.project1_gradetracker.AssignmentActivity.COURSE_ID;
import static com.example.project1_gradetracker.LoginActivity.USER_NAME;
import static com.example.project1_gradetracker.LoginActivity.database;

public class EditAssignmentActivity extends AppCompatActivity {

    private EditText edTitle, edID, edDue, edPoint, edEarned, edCategory;
    private Button editButton;

    List<Assignment> assignmentList;
    public static AssignmentDAO assignmentDAO;
    List<Course> courseList;
    List<Assignment> assignmentCourseList;
    public static CourseDAO courseDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assignment);

        Bundle bundle = getIntent().getExtras();
        final String user_name = bundle.getString(USER_NAME);
        final int course_id = bundle.getInt(COURSE_ID);

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
            Toast.makeText(EditAssignmentActivity.this, "no course found", Toast.LENGTH_SHORT).show();
        }

        assignmentDAO = database.assignmentDAO();
        assignmentList = assignmentDAO.getAllAssignments();
        assignmentCourseList = course.getAssignmentList();

        // Initialize edit text, button, radio gourp
        edTitle = findViewById(R.id.etTitleEd);
        edID = findViewById(R.id.etIDEd);
        edDue = findViewById(R.id.etDueEd);
        edPoint = findViewById(R.id.etPointEd);
        edEarned = findViewById(R.id.etEarnedEd);
        edCategory = findViewById(R.id.etCategoryEd);
        editButton = findViewById(R.id.btEditAssignment);

        final Course finalCourse = course;
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Check if fields are empty
                if (edID.getText().toString().isEmpty()) {
                    edID.setError("ID field cannot be empty");
                }
                if (edTitle.getText().toString().isEmpty()) {
                    edTitle.setError("Title field cannot be empty");
                }
                if (edDue.getText().toString().isEmpty()) {
                    edDue.setError("Duedate field cannot be empty");
                }
                if(edPoint.getText().toString().isEmpty()){
                    edPoint.setError("Points field cannot be empty");
                }
                if(edEarned.getText().toString().isEmpty()){
                    edEarned.setError("Earned Point field cannot be empty");
                }
                if(edCategory.getText().toString().isEmpty()) {
                    edCategory.setError("Category field cannot be empty");
                }

                Assignment assignment = null;
                int id = Integer.parseInt(edID.getText().toString());
                // find the Assignment data
                for(Assignment a: assignmentList) {
                    if (a.getAssignmentID() == id) {
                        assignment = a;
                        break;
                    }
                }
                // Assign editText to local variables
                assignment.setAssignmentID(Integer.parseInt(edID.getText().toString()));
                assignment.setTitle(edTitle.getText().toString());
                assignment.setDueDate(edDue.getText().toString());
                assignment.setPoints(Integer.parseInt(edPoint.getText().toString()));
                assignment.setGrade(Integer.parseInt(edEarned.getText().toString()));
                assignment.setCategory(edCategory.getText().toString());
                boolean checkassignment = false;

                for(Assignment a: assignmentList){
                    // Check assignmnet's id if assignment exit
                    if(a.getAssignmentID() == id){
                        assignmentDAO.updateAssignment(assignment);
                        Toast.makeText(EditAssignmentActivity.this, "Assignment: " + assignment.getTitle() + " [" + assignment.getAssignmentID() +"] " + " Successfully edited", Toast.LENGTH_SHORT).show();
                        checkassignment = true;
                    }
                }

                //Updating list in the course database
                int index = 0;
                for(Assignment a: assignmentCourseList){
                    if(a.getAssignmentID() == id){
                        assignmentCourseList.remove(index);
                        assignmentCourseList.add(index, assignment);
                        courseDAO.update(finalCourse);
                        checkassignment = true;
                        break;
                    }
                    index++;
                }
                if(!checkassignment){
                    Toast.makeText(EditAssignmentActivity.this, "Assignment: " + assignment.getTitle() + " [" + assignment.getAssignmentID() +"] " + " Doesn't exist!", Toast.LENGTH_SHORT).show();
                }

                //Go back to overall grade activity
                Intent intent = AssignmentActivity.getIntent(getApplicationContext(), user_name, course_id);
                startActivity(intent);
            }
        });
    }

    public static Intent getIntent(Context context, String username, int course){
        Bundle bundle = new Bundle();
        bundle.putString(USER_NAME, username);
        bundle.putInt(COURSE_ID, course);

        Intent intent = new Intent(context, EditAssignmentActivity.class);
        intent.putExtras(bundle);

        return intent;
    }
}