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

import java.util.List;

import static com.example.project1_gradetracker.AssignmentActivity.COURSE_ID;
import static com.example.project1_gradetracker.LoginActivity.USER_NAME;
import static com.example.project1_gradetracker.LoginActivity.database;
import static com.example.project1_gradetracker.LoginActivity.userDAO;

public class OverallGradeActivity extends AppCompatActivity {

    private Button buttonCreateC,buttonDeleteCourse, buttonEditCourse;
    private TextView displayUserName;

    List<User> users;
    //public static UserDAO userDAO;

    private RecyclerView recyclerView;
    // The adapter is the bridge between our data and the recycler view
    // it improves performance by only loading as many items as we need
    private CourseListAdapter adapter;
    // responsible for aligning the items in the list
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overallgrade);

        Bundle bundle = getIntent().getExtras();
        final String user_name = bundle.getString(USER_NAME);
        final int course_id = bundle.getInt(COURSE_ID);

        userDAO = database.userDAO();
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

        buildRecyclerView(user.getUsername(), user.getCourseList());

        buttonCreateC = findViewById(R.id.buttonCreateCourse);
        buttonDeleteCourse = findViewById(R.id.btnDeleteCourse);
        buttonEditCourse = findViewById(R.id.btnEditCourse);

        displayUserName = findViewById(R.id.tvUserName);
        displayUserName.setText(user_name);

        displayUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = EditUserActivity.getIntent(getApplicationContext(), user_name);
                startActivity(intent);
            }
        });

        buttonCreateC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = CreateCourseActivity.getIntent(getApplicationContext(), user_name);
                startActivity(intent);
            }
        });

        buttonDeleteCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = DeleteCourseActivity.getIntent(getApplicationContext(), user_name);
                startActivity(intent);
            }
        });

        buttonEditCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = EditCourseActivity.getIntent(getApplicationContext(), user_name);
                startActivity(intent);
            }
        });
    }

    public void goToAssignmentActivity(int position) {
        adapter.notifyItemChanged(position);
    }

    public void buildRecyclerView(final String user_name, final List<Course> userCourseList) {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new CourseListAdapter(userCourseList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        if(userCourseList != null) {
            // when we click the course in the recycler view, go to assignmentActivity
            adapter.setOnItemClickListener(new CourseListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Course currentCourse = userCourseList.get(position);
                    int course_id = currentCourse.getCourseID();

                    goToAssignmentActivity(position);

                    // pass in the courseID to get the course when we get into assignmentActivity
                    // so we can access activities attached to the course
                    Intent intent = AssignmentActivity.getIntent(getApplicationContext(), user_name, course_id);
                    startActivity(intent);
                }
            });
        }
    }

    public static Intent getIntent(Context context, String username){
        Intent intent = new Intent(context, OverallGradeActivity.class);
        intent.putExtra(USER_NAME, username);

        return intent;
    }
}