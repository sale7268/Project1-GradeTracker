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
import com.example.project1_gradetracker.DB.Course;


import java.util.List;

import static com.example.project1_gradetracker.LoginActivity.USER_NAME;
import static com.example.project1_gradetracker.LoginActivity.database;

public class CreateAssignmentActivity extends AppCompatActivity {

    private EditText Title;
    private EditText ID;
    private EditText DueDate;
    private EditText Points;
    private EditText Category;
    private Button Add;

    List<Assignment> assignmentList;
    public static AssignmentDAO assignmentDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);

        Title = (EditText)findViewById(R.id.etAssignmentTitle);
        ID = (EditText)findViewById(R.id.etAssignmentID);
        DueDate = (EditText)findViewById(R.id.etAssignmentDue);
        Points = (EditText)findViewById(R.id.etAssignmentPoints);
        Category = (EditText)findViewById(R.id.etAssignmentCategory);
        Add = (Button)findViewById(R.id.btAddAssignment);

        assignmentDAO = database.assignmentDAO();
        assignmentList = assignmentDAO.getAllAssignments();

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Need check assignment exist or not
                boolean assignmentExists = false;

                Assignment assignment = createNewAssignment();

                // Check if Assignment already exist or not
                for(int i=0; i < assignmentList.size(); i++){
                    Assignment inList = assignmentList.get(i);
                    if(inList.getAssignmentID() == assignment.getAssignmentID()){
                        assignmentExists = true;
                        Toast.makeText(CreateAssignmentActivity.this, "Assignment Already Added to Assignmnet List!", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                if(!assignmentExists){
                    database.assignmentDAO().insert(assignment);
                    Toast.makeText(CreateAssignmentActivity.this, "Assignment Add to Database", Toast.LENGTH_SHORT).show();
                }


                Intent i = getIntent();
                String user_name = i.getStringExtra(USER_NAME);
                // After success add new assignment, back to upper page.
                Intent intent = OverallGradeActivity.getIntent(getApplicationContext(), user_name); //(getApplicationContext(), user_name)
                startActivity(intent);
            }
        });
    }

    private Assignment createNewAssignment(){
        Assignment assignment = new Assignment();

        assignment.setTitle(Title.getText().toString());
        assignment.setAssignmentID(Integer.parseInt(ID.getText().toString()));
        assignment.setDueDate(DueDate.getText().toString());
        assignment.setPoints(Integer.parseInt(Points.getText().toString()));
        assignment.setCategory(Category.getText().toString());

        return assignment;
    }

    public static Intent getIntent(Context context, String username){
        Intent intent = new Intent(context, CreateAssignmentActivity.class);
        intent.putExtra(USER_NAME, username);

        return intent;
    }

}