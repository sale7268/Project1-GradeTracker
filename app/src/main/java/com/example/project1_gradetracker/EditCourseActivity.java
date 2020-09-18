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

import static com.example.project1_gradetracker.LoginActivity.USER_NAME;
import static com.example.project1_gradetracker.LoginActivity.database;

public class EditCourseActivity extends AppCompatActivity {

    private EditText editTitle, editID, editInstructor;
    private EditText editStart, editEnd, editDescription;

    private Button editButton;

    List<Course> courseList;
    public static CourseDAO courseDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        courseDAO = database.courseDAO();
        courseList = courseDAO.getAllCourses();

        //Initialize edit text and button
        editTitle = findViewById(R.id.etTitle);
        editID = findViewById(R.id.etID);
        editInstructor = findViewById(R.id.etInstructor);
        editStart = findViewById(R.id.etStart);
        editEnd = findViewById(R.id.etEnd);
        editDescription = findViewById(R.id.etDescription);
        editButton = findViewById(R.id.btnCreateC);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Find User
                Intent i = getIntent();
                String user_name = i.getStringExtra(USER_NAME);
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

                //Assign editText to local variables
                int id = Integer.parseInt(editID.getText().toString());
                String title = editTitle.getText().toString();
                String instructor = editInstructor.getText().toString();
                String desc = editDescription.getText().toString();
                boolean checkcourse = false;

                //Check for course in database
                for(Course c: courseList){
                    //If title and id is same
                    if(c.getCourseID() == id){
                        //Update the course in database
                        courseDAO.updateTitle(title, id);
                        courseDAO.updateDescription(desc, id);
                        courseDAO.updateInstructor(instructor, id);
                        checkcourse = true;
                        Toast.makeText(EditCourseActivity.this, "Course: " + title + " Successfully edited", Toast.LENGTH_SHORT).show();
                    }
                }
                if(!checkcourse){
                    Toast.makeText(EditCourseActivity.this, "Course: " + title + " Doesn't exist!", Toast.LENGTH_SHORT).show();
                }

                //Go back to overall grade activity
                Intent intent = OverallGradeActivity.getIntent(getApplicationContext(), user_name);
                startActivity(intent);
            }
        });
    }

    public static Intent getIntent(Context context, String username){
        Intent intent = new Intent(context, EditCourseActivity.class);
        intent.putExtra(USER_NAME, username);

        return intent;
    }
}