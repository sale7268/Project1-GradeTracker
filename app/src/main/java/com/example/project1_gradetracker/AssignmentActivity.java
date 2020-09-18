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

    final static String COURSE_ID = "course_id";

    //Declaring variables
    TextView assignmentDisplay, gradeDisplay;
    private Button buttonAddA, buttonDeleteA, buttonEdit;
    List<Assignment> assignmentList;
    public static AssignmentDAO assignmentDAO;
    public double grades = 0.0, totalPoints = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        //Initiating display method and scrolling movement
        assignmentDisplay = findViewById(R.id.tvAssignments);
        assignmentDisplay.setMovementMethod(new ScrollingMovementMethod());

        //Setting up grade display:
        gradeDisplay = findViewById(R.id.tvGrades);

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

        buttonDeleteA = findViewById(R.id.buttonDeleteAssignment);
        buttonDeleteA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = DeleteAssignmentActivity.getIntent(getApplicationContext(), USER_NAME);
                startActivity(intent);
            }
        });

        buttonEdit = findViewById(R.id.btnEditAssignment);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = EditAssignmentActivity.getIntent(getApplicationContext(), USER_NAME);
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
            gradeDisplay.setText("0");
        }

        //Checking all the assignments and then printing them one by one using StringBuilder
        StringBuilder sb = new StringBuilder();
        StringBuilder sbGrades = new StringBuilder();
        for(Assignment a : assignmentList){
            sb.append("Assignment ID: [" + a.getAssignmentID() + "]\n" + "Category: " + a.getCategory() + "\n" + "Title: " + a.getTitle() + "\n");
            sb.append("Points: " + a.getGrade() + "/" + a.getPoints() + "\n");
            sb.append("=-=-=-=-=-=-");
            sb.append("\n");
            grades += a.getGrade();
            totalPoints += a.getPoints();
        }
        sbGrades.append(((grades/totalPoints) * 100) + "%");
        gradeDisplay.setText(sbGrades.toString());
        assignmentDisplay.setText(sb.toString());
    }

    public static Intent getIntent(Context context, String username, int course){
        Bundle bundle = new Bundle();
        bundle.putString(USER_NAME, username);
        bundle.putInt(COURSE_ID, course);

        Intent intent = new Intent(context, AssignmentActivity.class);
        intent.putExtras(bundle);

        return intent;
    }
}