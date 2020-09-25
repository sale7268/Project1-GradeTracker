package com.example.project1_gradetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.example.project1_gradetracker.AssignmentActivity.COURSE_ID;
import static com.example.project1_gradetracker.LoginActivity.USER_NAME;

public class CategoriesActivity extends AppCompatActivity {

    Button quizzes, homework, projects, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        quizzes = findViewById(R.id.btnQuizes);
        homework = findViewById(R.id.btnHomework);
        projects = findViewById(R.id.btnProjects);
        back = findViewById(R.id.btnBack);

        Bundle bundle = getIntent().getExtras();
        final String user_name = bundle.getString(USER_NAME);
        final int course_id = bundle.getInt(COURSE_ID);

        quizzes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AssignmentActivity.getIntent(getApplicationContext(), user_name, course_id);
                startActivity(intent);
            }
        });

        homework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AssignmentActivity.getIntent(getApplicationContext(), user_name, course_id);
                startActivity(intent);
            }
        });

        projects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AssignmentActivity.getIntent(getApplicationContext(), user_name, course_id);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AssignmentActivity.getIntent(getApplicationContext(), user_name, course_id);
                startActivity(intent);
            }
        });
    }

    public static Intent getIntent(Context context, String username, int courseID){
        Bundle bundle = new Bundle();
        bundle.putString(USER_NAME, username);
        bundle.putInt(COURSE_ID, courseID);

        Intent intent = new Intent(context, CategoriesActivity.class);
        intent.putExtras(bundle);

        return intent;
    }
}