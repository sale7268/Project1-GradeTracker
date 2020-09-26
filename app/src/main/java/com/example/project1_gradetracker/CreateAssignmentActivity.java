package com.example.project1_gradetracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project1_gradetracker.DB.Assignment;
import com.example.project1_gradetracker.DB.AssignmentDAO;
import com.example.project1_gradetracker.DB.Course;
import com.example.project1_gradetracker.DB.CourseDAO;
import com.example.project1_gradetracker.DB.User;

import java.util.List;

import static com.example.project1_gradetracker.AssignmentActivity.COURSE_ID;
import static com.example.project1_gradetracker.LoginActivity.USER_NAME;
import static com.example.project1_gradetracker.LoginActivity.database;
import static com.example.project1_gradetracker.LoginActivity.userDAO;

public class CreateAssignmentActivity extends AppCompatActivity {

    private EditText Title;
    private EditText ID;
    private EditText DueDate;
    private EditText Points;
    private EditText Grade;
    private RadioGroup Category;
    private Button Add;

    List<Assignment> assignmentList;
    public static AssignmentDAO assignmentDAO;
    List<Course> courseList;
    public static CourseDAO courseDAO;
    String cate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);

        Bundle bundle = getIntent().getExtras();
        final String user_name = bundle.getString(USER_NAME);
        final int course_id = bundle.getInt(COURSE_ID);

        Title = (EditText)findViewById(R.id.etATitle);
        ID = (EditText)findViewById(R.id.etAID);
        DueDate = (EditText)findViewById(R.id.etADue);
        Points = (EditText)findViewById(R.id.etAPoints);
        Grade = (EditText)findViewById(R.id.etAGrade);
        Category = findViewById(R.id.radioGroup);
        Add = (Button)findViewById(R.id.btAddAssignment);

        assignmentDAO = database.assignmentDAO();
        assignmentList = assignmentDAO.getAllAssignments();
        courseDAO = database.courseDAO();
        courseList = courseDAO.getAllCourses();

        // Set Radiobutton for Category
        Category.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioButton){
                    cate = "Homework";
                }else if(checkedId == R.id.radioButton2){
                    cate = "Project";
                }else if(checkedId == R.id.radioButton3){
                    cate = "Quiz";
                }else{
                    cate = "Exam";
                }
            }
        });

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Need check assignment exist or not
                boolean assignmentExists = false;
                boolean switchActivity = true;

                if(ID.getText().toString().isEmpty()){
                    ID.setError("ID field cannot be empty");
                    Toast.makeText(CreateAssignmentActivity.this, "ID cannot be empty", Toast.LENGTH_SHORT).show();
                    switchActivity = false;
                }
                if(Title.getText().toString().isEmpty()){
                    Title.setError("Title field cannot be empty");
                    Toast.makeText(CreateAssignmentActivity.this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
                    switchActivity = false;
                }
                if(Points.getText().toString().isEmpty()){
                    Points.setError("Points field cannot be empty");
                    Toast.makeText(CreateAssignmentActivity.this, "Points cannot be empty", Toast.LENGTH_SHORT).show();
                    switchActivity = false;
                }
                if(Grade.getText().toString().isEmpty()){
                    Grade.setError("Grade field cannot be empty");
                    Toast.makeText(CreateAssignmentActivity.this, "Grade cannot be empty", Toast.LENGTH_SHORT).show();
                    switchActivity = false;
                }

                if(!switchActivity) return;

                User user = null;

                for(User u : database.userDAO().getAllUsers()) {
                    if (u.getUsername().equals(user_name)) {
                        user = u;
                        break;
                    }
                }
                if(user == null){
                    Toast.makeText(CreateAssignmentActivity.this, "no user found", Toast.LENGTH_SHORT).show();
                }

                String user_id = user.getUSER_ID();
                Assignment assignment = createNewAssignment(cate, course_id, user_id);

                Course course = null;

                for(Course c : courseList){
                    // course exists, add course to user course-list
                    if(c.getCourseID() == course_id){
                        course = c;
                        for(Assignment a : assignmentList){
                            if(a.getAssignmentID() == assignment.getAssignmentID()){
                                assignmentExists = true;
                                course.getAssignmentList().add(assignment);
                                course.calculateTotalGrade();
                                courseDAO.update(course);
                                userDAO.update(user);
                                Toast.makeText(CreateAssignmentActivity.this, "Assignment Already Added to Assignmnet List!", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                    }
                }

                if(!assignmentExists){
                    assignmentDAO.insert(assignment);
                    course.addAssignment(assignment);
                    Toast.makeText(CreateAssignmentActivity.this, "Assignment Added to Database", Toast.LENGTH_SHORT).show();
                }
                course.calculateTotalGrade();
                courseDAO.update(course);
                userDAO.update(user);

                // After success add new assignment, back to upper page.
                if(switchActivity) {
                    Intent intent = AssignmentActivity.getIntent(getApplicationContext(), user_name, course_id, cate);
                    startActivity(intent);
                } else {
                    return;
                }
            }
        });
    }

    private Assignment createNewAssignment(String category, int course_id, String user_id){
        Assignment assignment = new Assignment(
            user_id,
            course_id,
            Integer.parseInt(ID.getText().toString()),
            Title.getText().toString(),
            DueDate.getText().toString(),
            Integer.parseInt(Points.getText().toString()),
            Integer.parseInt(Grade.getText().toString()),
            category);

        return assignment;
    }

    public static Intent getIntent(Context context, String username, int course){
        Bundle bundle = new Bundle();
        bundle.putString(USER_NAME, username);
        bundle.putInt(COURSE_ID, course);

        Intent intent = new Intent(context, CreateAssignmentActivity.class);
        intent.putExtras(bundle);

        return intent;
    }
}