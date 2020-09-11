package com.example.project1_gradetracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project1_gradetracker.DB.User;

import static com.example.project1_gradetracker.LoginActivity.database;

public class OverallGradeActivity extends AppCompatActivity {

    private Button buttonCreateC;
    private Button buttonAddA;

    final static String USER_NAME = "USERNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overallgrade);

        Intent intent = getIntent();
        String user_name = intent.getStringExtra("USER_NAME");
        User user;
        // find the user data
        for(User u : database.userDAO().getAllUsers()){
            if(u.getUsername().equals(user_name)) user = u;
        }

        buttonCreateC = (Button)findViewById(R.id.buttonCreateCourse);
        buttonAddA = (Button)findViewById(R.id.buttonAddAssignment);

        buttonCreateC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OverallGradeActivity.this, CreateCourseActivity.class);
                startActivity(intent);
            }
        });

        buttonAddA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OverallGradeActivity.this, AddAssignmentActicity.class);
                startActivity(intent);
            }
        });
    }

    public static Intent getIntent(Context context, String username){
        Intent intent = new Intent(context, OverallGradeActivity.class);
        intent.putExtra(USER_NAME, username);

        return intent;
    }
}