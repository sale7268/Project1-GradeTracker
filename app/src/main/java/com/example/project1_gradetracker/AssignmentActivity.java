package com.example.project1_gradetracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project1_gradetracker.DB.Assignment;
import com.example.project1_gradetracker.DB.AssignmentDAO;

import java.util.List;

import static com.example.project1_gradetracker.LoginActivity.USER_NAME;
import static com.example.project1_gradetracker.LoginActivity.database;


public class AssignmentActivity extends AppCompatActivity {

    //Declaring variables
    TextView assignmentDisplay;
    private Button buttonAddA;
    List<Assignment> assignmentList;
    public static AssignmentDAO assignmentDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        //Initiating display method and scrolling movement
        assignmentDisplay = findViewById(R.id.tvAssignments);
        assignmentDisplay.setMovementMethod(new ScrollingMovementMethod());

        //Calling display function
        refreshDisplay();

        //Starting new activity after clicking "Add assignment" button
        buttonAddA = findViewById(R.id.buttonAddAssignment);
        buttonAddA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = CreateAssignmentActivity.getIntent(getApplicationContext(), USER_NAME);
                startActivity(intent);
            }
        });

    }

    //Display Function
    private void refreshDisplay() {

        //Getting Data from database to local list
        assignmentDAO = database.assignmentDAO();
        assignmentList = assignmentDAO.getAllAssignments();

        //No assignment then display message
        if(assignmentList.size() <= 0){
            assignmentDisplay.setText("No Assignments");
        }

        //Checking all the assignments and then printing them one by one using StringBuilder
        StringBuilder sb = new StringBuilder();
        for(Assignment a : assignmentList){
            sb.append("ID: [" + a.getAssignmentID() + "]\n" + "Title: " + a.getTitle() + "\n");
            sb.append("Points: " + a.getPoints() + "\n");
            sb.append("=-=-=-=-=-=-");
            sb.append("\n");
        }
        assignmentDisplay.setText(sb.toString());
    }

    public static Intent getIntent(Context context, String username){
        Intent intent = new Intent(context, AssignmentActivity.class);
        intent.putExtra(USER_NAME, username);

        return intent;
    }
}