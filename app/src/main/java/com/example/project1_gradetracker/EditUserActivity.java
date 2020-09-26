package com.example.project1_gradetracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project1_gradetracker.DB.User;
import com.example.project1_gradetracker.DB.UserDAO;

import java.util.List;

import static com.example.project1_gradetracker.LoginActivity.USER_NAME;
import static com.example.project1_gradetracker.LoginActivity.database;

public class EditUserActivity extends AppCompatActivity {
    private EditText edID, edUsername, edPassword;
    private Button editUser, back;

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
        back = findViewById(R.id.btnBack2);

        Intent i = getIntent();
        final String[] user_name = {i.getStringExtra(USER_NAME)};


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = OverallGradeActivity.getIntent(getApplicationContext(), user_name[0]);
                startActivity(intent);
            }
        });

        editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = null;

                for(User u : userList) {
                    if (u.getUsername().equals(user_name[0])) {
                        user = u;
                        break;
                    }
                }
                if(user == null){
                    Toast.makeText(EditUserActivity.this, "no user found", Toast.LENGTH_SHORT).show();
                }

                for(User u : userList){
                    if(u.getUsername().equals(edUsername.getText().toString())){
                        edUsername.setError("Username Taken");
                        Toast.makeText(EditUserActivity.this, "Username Taken", Toast.LENGTH_SHORT).show();
                        break;
                    }
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
                user.setUsername(edUsername.getText().toString());
                user.setPassword(edPassword.getText().toString());
                boolean checkUser = false;
                boolean existingUser = false;

                // Check for User in database
                for(User u: userList){
                    if(u.getUSER_ID().equals(id)){
                        for(User eu : userList){
                            if(eu.getUsername().equals(user.getUsername())){
                                Toast.makeText(EditUserActivity.this, "Username: (" + user.getUsername() + ") already exists!", Toast.LENGTH_SHORT).show();
                                existingUser = true;
                                break;
                            }
                        }
                        if(existingUser){
                            break;
                        }
                        userDAO.update(user);
                        checkUser = true;
                        Toast.makeText(EditUserActivity.this, "User: (" + id + ") " + user.getUsername() + " " +
                                " Successfully edited your Username and Password!", Toast.LENGTH_SHORT).show();
                    }
                }
                if(!checkUser){
                    Toast.makeText(EditUserActivity.this, "User ID: " + id +
                            " Doesn't exist!", Toast.LENGTH_SHORT).show();
                }

                user_name[0] = user.getUsername();
                Intent intent = OverallGradeActivity.getIntent(getApplicationContext(), user_name[0]);
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