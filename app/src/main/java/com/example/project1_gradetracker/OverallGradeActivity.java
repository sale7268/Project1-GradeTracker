package com.example.project1_gradetracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1_gradetracker.DB.Course;
import com.example.project1_gradetracker.DB.User;
import com.example.project1_gradetracker.DB.UserDAO;

import java.util.ArrayList;
import java.util.List;

import static com.example.project1_gradetracker.LoginActivity.USER_NAME;
import static com.example.project1_gradetracker.LoginActivity.database;

public class OverallGradeActivity extends AppCompatActivity {

    private Button buttonCreateC, buttonAssignments;
    private Button buttonAddA;
    private TextView course1, course2, course3, course4;
    private TextView courseGrade1, courseGrade2, courseGrade3, courseGrade4;

    List<User> users;
    public static UserDAO userDAO;

    private RecyclerView recyclerView;
    // The adapter is the bridge between our data and the recycler view
    // it improves performance by only loading as many items as we need
    private RecyclerView.Adapter adapter;
    // responsible for aligning the items in the list
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overallgrade);

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
            Toast.makeText(OverallGradeActivity.this, "no user found", Toast.LENGTH_SHORT).show();
        }

        ArrayList<Course> courseList = new ArrayList<>();
//        courseList.add()

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new CourseListAdapter(courseList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        buttonCreateC = (Button)findViewById(R.id.buttonCreateCourse);
        buttonAssignments = findViewById(R.id.btnAssignments);

        buttonCreateC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = CreateCourseActivity.getIntent(getApplicationContext(), user_name);
                startActivity(intent);
            }
        });

        buttonAssignments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AssignmentActivity.getIntent(getApplicationContext(), USER_NAME);
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