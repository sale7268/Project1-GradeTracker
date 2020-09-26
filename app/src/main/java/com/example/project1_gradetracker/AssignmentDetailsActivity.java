package com.example.project1_gradetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project1_gradetracker.DB.Assignment;
import com.example.project1_gradetracker.DB.AssignmentDAO;
import com.example.project1_gradetracker.DB.User;

import java.util.List;

import static com.example.project1_gradetracker.AssignmentActivity.CATEGORY;
import static com.example.project1_gradetracker.AssignmentActivity.COURSE_ID;
import static com.example.project1_gradetracker.LoginActivity.USER_NAME;
import static com.example.project1_gradetracker.LoginActivity.database;
import static com.example.project1_gradetracker.LoginActivity.userDAO;

public class AssignmentDetailsActivity extends AppCompatActivity {

    public static final String ASSIGNMENT_ID = "ASSIGNMENT_ID";

    TextView assignmentDetails;

    AssignmentDAO asDAO;
    List<Assignment> assList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_details);

        Bundle bundle = getIntent().getExtras();
        final String user_name = bundle.getString(USER_NAME);
        final int course_id = bundle.getInt(COURSE_ID);
        final int assignment_id = bundle.getInt(ASSIGNMENT_ID);

        List<User> users = userDAO.getAllUsers();
        User user = null;

        // find the user data
        for(User u : users) {
            if (u.getUsername().equals(user_name)) {
                user = u;
                break;
            }
        }
        if(user == null){
            Toast.makeText(AssignmentDetailsActivity.this, "no user found", Toast.LENGTH_SHORT).show();
        }


        assignmentDetails = findViewById(R.id.tvAssignmentDetails);

        asDAO = database.assignmentDAO();
        assList = asDAO.getAllAssignments();
        for(Assignment a : assList){
            if(a.getUserID().equals(user.getUSER_ID()) && a.getCourseID() == course_id && a.getAssignmentID() == assignment_id){
                assignmentDetails.setText(a.getAssignmentID() + "\n" + a.getTitle() + "\n" + a.getGrade() + "\n" + a.getPoints() + "\n" + a.getDueDate());
            }
        }

    }

    public static Intent getIntent(Context context, String username, int course, int assignmentID){
        Bundle bundle = new Bundle();
        bundle.putString(USER_NAME, username);
        bundle.putInt(COURSE_ID, course);
        bundle.putInt(ASSIGNMENT_ID, assignmentID);

        Intent intent = new Intent(context, AssignmentDetailsActivity.class);
        intent.putExtras(bundle);

        return intent;
    }
}