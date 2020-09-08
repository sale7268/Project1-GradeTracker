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

import java.util.List;

import static com.example.project1_gradetracker.LoginActivity.database;
import static com.example.project1_gradetracker.LoginActivity.userDAO;

public class CreateUserActivity extends AppCompatActivity {

    final static String USER_NAME = "USERNAME";

    private Button createUser;
    private EditText username;
    private EditText password;
    private EditText userID;

    List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        createUser = findViewById(R.id.buttonCreateUser);
        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);
        userID = findViewById(R.id.editTextUserID);

        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check if user is valid
                boolean invalid = false;

                User u = new User();
                u.setUsername(username.getText().toString());
                u.setUserID(userID.getText().toString());
                u.setPassword(password.getText().toString());

                //populate user list with current users in the DB
                userList = userDAO.getAllUsers();
                // go through the database and check is username or ID are taken before adding user to database
                // userID is the PK
                for(User user : userList){
                    if(user.getUsername().equals(u.getUsername())){
                        username.setError("Username Taken");
                        Toast.makeText(CreateUserActivity.this, "Username Taken", Toast.LENGTH_SHORT).show();
                        invalid = true;
                        break;
                    }
                    // for now we allow users to enter their own userID in so we have to check if it is valid
                    // TODO: we will auto generate userIDs if we have time
                    if(user.getUserID().equals(u.getUserID())){
                        userID.setError("ID Taken");
                        Toast.makeText(CreateUserActivity.this, "ID Taken", Toast.LENGTH_SHORT).show();
                        invalid = true;
                        break;
                    }
                }

                if(invalid){
                    return;
                }
                else {
                    // for now, I am importing the static database variable from login screen
                    database.userDAO().insert(u);
                    Toast.makeText(CreateUserActivity.this, "User " + username.getText().toString() + " Created", Toast.LENGTH_SHORT).show();

                    // return to login screen
                    // TODO: return to course list
                    Intent intent = LoginActivity.getIntent(getApplicationContext());
                    startActivity(intent);
                }

            }
        });
    }

    public static Intent getIntent(Context context, String username){
        Intent intent = new Intent(context, CreateUserActivity.class);
        intent.putExtra(USER_NAME, username);

        return intent;
    }
}