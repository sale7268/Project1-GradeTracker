package com.example.project1_gradetracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project1_gradetracker.DB.Assignment;
import com.example.project1_gradetracker.DB.AssignmentDAO;

import java.util.List;

import static com.example.project1_gradetracker.AssignmentActivity.COURSE_ID;
import static com.example.project1_gradetracker.LoginActivity.USER_NAME;
import static com.example.project1_gradetracker.LoginActivity.database;


public class DeleteAssignmentActivity extends AppCompatActivity {

    private EditText deleteAssignmentID;
    private Button deleteAssignment;

    public static AssignmentDAO assignmentDAO;
    List<Assignment> assignmentList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_assignment);

        Bundle bundle = getIntent().getExtras();
        final String user_name = bundle.getString(USER_NAME);
        final int course_id = bundle.getInt(COURSE_ID);

        deleteAssignmentID = findViewById(R.id.tvDeleteAssignmentID);
        deleteAssignment = findViewById(R.id.btDeleteA);

        // Assignment Database
        assignmentDAO = database.assignmentDAO();
        assignmentList = assignmentDAO.getAllAssignments();

        deleteAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(deleteAssignmentID.getText().toString());
                String title = "";
                boolean assignmentExit = false;
                for(Assignment a: assignmentList){
                    if(a.getAssignmentID() == id){
                        title = a.getTitle();
                        assignmentDAO.delete(a);
                        assignmentExit =true;
                        Toast.makeText(DeleteAssignmentActivity.this, "Assignment: " + title + " Successfully deleted", Toast.LENGTH_SHORT).show();
                    }
                }

                if(!assignmentExit) {
                    Toast.makeText(DeleteAssignmentActivity.this, "Assignment: " + title + " doesn't exist", Toast.LENGTH_SHORT).show();
                }

                Intent i = AssignmentActivity.getIntent(getApplicationContext(), user_name, course_id);
                startActivity(i);
            }
        });


    }



    public static Intent getIntent(Context context, String username){
        Intent intent = new Intent(context, DeleteAssignmentActivity.class);
        intent.putExtra(USER_NAME, username);

        return intent;
    }
}