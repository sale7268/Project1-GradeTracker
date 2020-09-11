package com.example.project1_gradetracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project1_gradetracker.DB.User;

import static com.example.project1_gradetracker.LoginActivity.USER_NAME;
import static com.example.project1_gradetracker.LoginActivity.database;

public class OverallGradeActivity extends AppCompatActivity {

    private Button buttonCreateC;
    private Button buttonAddA;
    private TextView course1, course2, course3, course4;
    private TextView courseGrade1, courseGrade2, courseGrade3, courseGrade4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overallgrade);

        Intent intent = getIntent();
        final String user_name = intent.getStringExtra(USER_NAME);
        User user = null;

        // find the user data
        for(User u : database.userDAO().getAllUsers()){
            if(u.getUsername().equals(user_name))
                user = u;
        }
        if(user == null){
            Toast.makeText(OverallGradeActivity.this, "no user found", Toast.LENGTH_SHORT).show();
        }

        buttonCreateC = (Button)findViewById(R.id.buttonCreateCourse);
        course1 = findViewById(R.id.tvCourse1);
        course2 = findViewById(R.id.tvCourse2);
        course3 = findViewById(R.id.tvCourse3);
        course4 = findViewById(R.id.tvCourse4);

//        course1.setText(user.getCourseList().indexOf(0));
//        course1.setText(user.getCourseList().indexOf(1));
//        course1.setText(user.getCourseList().indexOf(2));
//        course1.setText(user.getCourseList().indexOf(3));

        buttonCreateC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = CreateCourseActivity.getIntent(getApplicationContext(), user_name);
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