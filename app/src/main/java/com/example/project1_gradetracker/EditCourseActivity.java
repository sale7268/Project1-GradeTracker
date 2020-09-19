package com.example.project1_gradetracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project1_gradetracker.DB.Course;
import com.example.project1_gradetracker.DB.CourseDAO;
import com.example.project1_gradetracker.DB.User;

import java.util.List;

import static com.example.project1_gradetracker.AssignmentActivity.COURSE_ID;
import static com.example.project1_gradetracker.LoginActivity.USER_NAME;
import static com.example.project1_gradetracker.LoginActivity.database;
import static com.example.project1_gradetracker.LoginActivity.userDAO;

public class EditCourseActivity extends AppCompatActivity {

    private EditText editTitle, editID, editInstructor;
    private EditText editStart, editEnd, editDescription;

    private Button editButton;

    List<Course> courseList;
    List<Course> userCourseList;
    public static CourseDAO courseDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        Bundle bundle = getIntent().getExtras();
        final String user_name = bundle.getString(USER_NAME);
        final int course_id = bundle.getInt(COURSE_ID);

        //Find User
        User user = null;

        for(User u : database.userDAO().getAllUsers()) {
            if (u.getUsername().equals(user_name)) {
                user = u;
                break;
            }
        }
        if(user == null){
            Toast.makeText(EditCourseActivity.this, "no user found", Toast.LENGTH_SHORT).show();
        }

        //Course Database
        courseDAO = database.courseDAO();
        courseList = courseDAO.getAllCourses();
        userCourseList = user.getCourseList();

        //Initialize edit text and button
        editTitle = findViewById(R.id.etTitle);
        editID = findViewById(R.id.etID);
        editInstructor = findViewById(R.id.etInstructor);
        editStart = findViewById(R.id.etStart);
        editEnd = findViewById(R.id.etEnd);
        editDescription = findViewById(R.id.etDescription);
        editButton = findViewById(R.id.btnCreateC);

        final User finalUser = user;
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Check if fields are empty
                if(editID.getText().toString().isEmpty()){
                    editID.setError("ID field cannot be empty");
                }
                if(editTitle.getText().toString().isEmpty()){
                    editTitle.setError("Title field cannot be empty");
                }
                if(editInstructor.getText().toString().isEmpty()){
                    editInstructor.setError("Instructor field cannot be empty");
                }

                Course course = null;
                int id = Integer.parseInt(editID.getText().toString());
                for(Course c: courseList) {
                    if (c.getCourseID() == id) {
                        course = c;
                        break;
                    }
                }
                //Assign editText to local variables
                course.setTitle(editTitle.getText().toString());
                course.setInstructor(editInstructor.getText().toString());
                course.setDescription(editDescription.getText().toString());
                boolean checkcourse = false;

                //Check for course in database
                for(Course c: courseList){
                    //If title and id is same
                    if(c.getCourseID() == id){
                        //Update the course in database
                        courseDAO.update(course);
                        checkcourse = true;
                        Toast.makeText(EditCourseActivity.this, "Course: " + course.getTitle() + " Successfully edited", Toast.LENGTH_SHORT).show();
                    }
                }

                int index = 0;
                for(Course c: userCourseList){
                    if(c.getCourseID() == id){
                        userCourseList.remove(index);
                        userCourseList.add(index, course);
                        userDAO.update(finalUser);
                        checkcourse = true;
                        Toast.makeText(EditCourseActivity.this, "Course: " + course.getTitle() + " Successfully edited", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    index++;
                }

                if(!checkcourse){
                    Toast.makeText(EditCourseActivity.this, "Course: " + course.getTitle() + " Doesn't exist!", Toast.LENGTH_SHORT).show();
                }

                //Go back to overall grade activity
                Intent intent = OverallGradeActivity.getIntent(getApplicationContext(), user_name);
                startActivity(intent);
            }
        });
    }

    public static Intent getIntent(Context context, String username, int course){
        Bundle bundle = new Bundle();
        bundle.putString(USER_NAME, username);
        bundle.putInt(COURSE_ID, course);

        Intent intent = new Intent(context, EditCourseActivity.class);
        intent.putExtras(bundle);

        return intent;
    }
}