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
import com.example.project1_gradetracker.DB.User;

import java.util.List;

import static com.example.project1_gradetracker.AssignmentActivity.COURSE_ID;
import static com.example.project1_gradetracker.LoginActivity.USER_NAME;
import static com.example.project1_gradetracker.LoginActivity.database;

public class EditAssignmentActivity extends AppCompatActivity {

    private EditText edTitle, edID, edDue, edPoint, edEarned, edCategory;
    private Button editButton;

    List<Assignment> assignmentList;
    public static AssignmentDAO assignmentDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assignment);

        Bundle bundle = getIntent().getExtras();
        final String user_name = bundle.getString(USER_NAME);
        final int course_id = bundle.getInt(COURSE_ID);

        assignmentDAO = database.assignmentDAO();
        assignmentList = assignmentDAO.getAllAssignments();

        // Initialize edit text, button, radio gourp
        edTitle = findViewById(R.id.etTitleEd);
        edID = findViewById(R.id.etIDEd);
        edDue = findViewById(R.id.etDueEd);
        edPoint = findViewById(R.id.etPointEd);
        edEarned = findViewById(R.id.etEarnedEd);
        edCategory = findViewById(R.id.etCategoryEd);
        editButton = findViewById(R.id.btEditAssignment);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Find User
                Intent i = getIntent();
                String user_name = i.getStringExtra(USER_NAME);
                User user = null;

                for (User u : database.userDAO().getAllUsers()) {
                    if (u.getUsername().equals(user_name)) {
                        user = u;
                        break;
                    }
                }
                if (user == null) {
                    Toast.makeText(EditAssignmentActivity.this, "no user found", Toast.LENGTH_SHORT).show();
                }

                // Check if fields are empty
                if (edID.getText().toString().isEmpty()) {
                    edID.setError("ID field cannot be empty");
                }
                if (edTitle.getText().toString().isEmpty()) {
                    edTitle.setError("Title field cannot be empty");
                }
                if (edDue.getText().toString().isEmpty()) {
                    edDue.setError("Duedate field cannot be empty");
                }
                if(edPoint.getText().toString().isEmpty()){
                    edPoint.setError("Points field cannot be empty");
                }
                if(edEarned.getText().toString().isEmpty()){
                    edEarned.setError("Earned Point field cannot be empty");
                }
                if(edCategory.getText().toString().isEmpty()) {
                    edCategory.setError("Category field cannot be empty");
                }

                // Assign editText to local variables
                int id = Integer.parseInt(edID.getText().toString());
                String title = edTitle.getText().toString();
                String due = edDue.getText().toString();
                int point = Integer.parseInt(edPoint.getText().toString());
                int earned = Integer.parseInt(edEarned.getText().toString());
                String category = edCategory.getText().toString();
                boolean checkassignment = false;

                for(Assignment a: assignmentList){
                    // Check assignmnet's id if assignment exit
                    if(a.getAssignmentID() == id){
                        Assignment assignment = new Assignment(course_id, id, title, due, point, earned, category);
                        assignmentDAO.updateAssignment(assignment);
                        Toast.makeText(EditAssignmentActivity.this, "Assignment: " + title + " [" + id +"] " + " Successfully edited", Toast.LENGTH_SHORT).show();
                        checkassignment = true;
                    }
                }
                if(!checkassignment){
                    Toast.makeText(EditAssignmentActivity.this, "Assignment: " + title + " [" + id +"] " + " Doesn't exist!", Toast.LENGTH_SHORT).show();
                }

                //Go back to overall grade activity
                Intent intent = AssignmentActivity.getIntent(getApplicationContext(), user_name, course_id);
                startActivity(intent);
            }
        });



    }

    public static Intent getIntent(Context context, String username, int course){
        Bundle bundle = new Bundle();
        bundle.putString(USER_NAME, username);
        bundle.putInt(COURSE_ID, course);

        Intent intent = new Intent(context, EditAssignmentActivity.class);
        intent.putExtras(bundle);

        return intent;
    }
}