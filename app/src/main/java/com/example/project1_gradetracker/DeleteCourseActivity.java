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
import com.example.project1_gradetracker.DB.User;
import com.example.project1_gradetracker.DB.UserDAO;

import java.util.List;

import static com.example.project1_gradetracker.CreateCourseActivity.courseDAO;
import static com.example.project1_gradetracker.LoginActivity.USER_NAME;
import static com.example.project1_gradetracker.LoginActivity.database;

public class DeleteCourseActivity extends AppCompatActivity {

    List<User> users;
    List<Course> courseList;
    public static UserDAO userDAO;

    EditText deleteCourseId, deleteCourseName;
    Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_course);

        Intent intent = getIntent();
        final String user_name = intent.getStringExtra(USER_NAME);
        userDAO= database.userDAO();
        users = userDAO.getAllUsers();
        User user = null;

        // find the user data
        for(User u : users) {
            if (u.getUsername().equals(user_name)) {
                user = u;
                break;
            }
        }
        if(user == null){
            Toast.makeText(DeleteCourseActivity.this, "no user found", Toast.LENGTH_SHORT).show();
        }

        //Course Database
        courseDAO = database.courseDAO();
        courseList = courseDAO.getAllCourses();

        //Initialize buttons
        deleteCourseId = findViewById(R.id.etDeleteCourseId);
        deleteCourseName = findViewById(R.id.etDeleteCourseName);
        deleteButton = findViewById(R.id.btnDelete);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.parseInt(deleteCourseId.getText().toString());
                String title = deleteCourseName.getText().toString();
                boolean courseExist = false;
                for(Course c : courseList){
                    if(c.getCourseID() == id && c.getTitle().equals(title)){
                        courseDAO.delete(c);
                        courseExist = true;
                        Toast.makeText(DeleteCourseActivity.this, "Course: " + title + " Successfully deleted", Toast.LENGTH_SHORT).show();
                    }
                }

                if(!courseExist) {
                    Toast.makeText(DeleteCourseActivity.this, "Course: " + title + " doesn't exist", Toast.LENGTH_SHORT).show();
                }

                Intent i = OverallGradeActivity.getIntent(getApplicationContext(), user_name);
                startActivity(i);
            }
        });
    }

    public static Intent getIntent(Context context, String username){
        Intent intent = new Intent(context, DeleteCourseActivity.class);
        intent.putExtra(USER_NAME, username);

        return intent;
    }
}