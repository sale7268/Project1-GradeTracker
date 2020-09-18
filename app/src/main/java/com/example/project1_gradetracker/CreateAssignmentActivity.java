package com.example.project1_gradetracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project1_gradetracker.DB.Assignment;
import com.example.project1_gradetracker.DB.AssignmentDAO;

import java.util.List;

import static com.example.project1_gradetracker.LoginActivity.USER_NAME;
import static com.example.project1_gradetracker.LoginActivity.database;

public class CreateAssignmentActivity extends AppCompatActivity {

    private EditText Title;
    private EditText ID;
    private EditText DueDate;
    private EditText Points;
    private EditText Grade;
    private RadioGroup Category;
    private Button Add;

    List<Assignment> assignmentList;
    public static AssignmentDAO assignmentDAO;
    String cate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);

        Title = (EditText)findViewById(R.id.etATitle);
        ID = (EditText)findViewById(R.id.etAID);
        DueDate = (EditText)findViewById(R.id.etADue);
        Points = (EditText)findViewById(R.id.etAPoints);
        Grade = (EditText)findViewById(R.id.etAGrade);
        Category = findViewById(R.id.radioGroup);
        Add = (Button)findViewById(R.id.btAddAssignment);

        assignmentDAO = database.assignmentDAO();
        assignmentList = assignmentDAO.getAllAssignments();

        // Set Radiobutton for Category
        Category.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioButton){
                    cate = "Homework";
                }else if(checkedId == R.id.radioButton2){
                    cate = "Project";
                }else if(checkedId == R.id.radioButton3){
                    cate = "Quiz";
                }else{
                    cate = "Exam";
                }
            }
        });

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Need check assignment exist or not
                boolean assignmentExists = false;


                Assignment assignment = createNewAssignment(cate);


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
                Intent intent = AssignmentActivity.getIntent(getApplicationContext(), user_name);
                startActivity(intent);
            }
        });
    }

    private Assignment createNewAssignment(String category){
        final Assignment assignment = new Assignment();

        assignment.setTitle(Title.getText().toString());
        assignment.setAssignmentID(Integer.parseInt(ID.getText().toString()));
        assignment.setDueDate(DueDate.getText().toString());
        assignment.setPoints(Integer.parseInt(Points.getText().toString()));
        assignment.setGrade(Integer.parseInt(Grade.getText().toString()));
        assignment.setCategory(category);

        return assignment;
    }

    public static Intent getIntent(Context context, String username){
        Intent intent = new Intent(context, CreateAssignmentActivity.class);
        intent.putExtra(USER_NAME, username);

        return intent;
    }

}