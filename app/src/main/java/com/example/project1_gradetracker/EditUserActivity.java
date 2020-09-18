package com.example.project1_gradetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project1_gradetracker.DB.Course;
import com.example.project1_gradetracker.DB.CourseDAO;
import com.example.project1_gradetracker.DB.User;
import com.example.project1_gradetracker.DB.UserDAO;

import java.util.List;

import static com.example.project1_gradetracker.LoginActivity.USER_NAME;
import static com.example.project1_gradetracker.LoginActivity.database;

public class EditUserActivity extends AppCompatActivity {
    private EditText edID, edUsername, edPassword;
    private Button editUser;

    List<User> userList;
    public static UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        userDAO = database.userDAO();
        userList = userDAO.getAllUsers();

        //Initialize edit text and button
        edID = findViewById(R.id.etUserIdEd);
        edUsername = findViewById(R.id.etUserNameEd);
        edPassword = findViewById(R.id.etUserPasswordEd);
        editUser = findViewById(R.id.btnEditUser);

        editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Find User
                Intent i = getIntent();
                String user_name = i.getStringExtra(USER_NAME);
                User user = null;

                for(User u : database.userDAO().getAllUsers()) {
                    if (u.getUsername().equals(user_name)) {
                        user = u;
                        break;
                    }
                }
                if(user == null){
                    Toast.makeText(EditUserActivity.this, "no user found", Toast.LENGTH_SHORT).show();
                }

                // Check if fields are empty
                if(edID.getText().toString().isEmpty()){
                    edID.setError("User ID field cannot be empty");
                }
                if(edUsername.getText().toString().isEmpty()){
                    edUsername.setError("User Name field cannot be empty");
                }
                if(edPassword.getText().toString().isEmpty()){
                    edPassword.setError("Password field cannot be empty");
                }

                // Assign editText to local variables
                String id = edID.getText().toString();
                String userName = edUsername.getText().toString();
                String passWord = edPassword.getText().toString();
                boolean checkUser = false;

                //User userEd = new User(id, userName, passWord);

                // Check for User in database
                for(User u: userList){
                    if(u.getUSER_ID().equals(id)){
                        userDAO.updateUsername(userName, id);
                        userDAO.updatePassword(passWord, id);
                        //userDAO.update(userEd);
                        checkUser = true;
                        Toast.makeText(EditUserActivity.this, "User: (" + id + ") " + userName + " " +
                                " Successfully edited your Username and Password!", Toast.LENGTH_SHORT).show();
                    }
                }
                if(!checkUser){
                    Toast.makeText(EditUserActivity.this, "User ID: " + id +
                            " Doesn't exist!", Toast.LENGTH_SHORT).show();
                }
                Intent intent = OverallGradeActivity.getIntent(getApplicationContext(), user_name);
                startActivity(intent);
            }
        });



    }

    public static Intent getIntent(Context context, String username){
        Intent intent = new Intent(context, EditUserActivity.class);
        intent.putExtra(USER_NAME, username);

        return intent;
    }
}