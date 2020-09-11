package com.example.project1_gradetracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project1_gradetracker.DB.RoomDB;
import com.example.project1_gradetracker.DB.User;
import com.example.project1_gradetracker.DB.UserDAO;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    public static RoomDB database;
    public static UserDAO userDAO;

    final static String USER_NAME = "USERNAME";

    List<User> userList;

    private EditText username, password;
    private Button logInButton, createUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        database = RoomDB.getInstance(this);
        userDAO = database.userDAO();
        userList = userDAO.getAllUsers();

        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        logInButton = findViewById(R.id.btnLogin);
        createUserButton = findViewById(R.id.btnCreateNewUser);

        final AlertDialog.Builder BUILDER = new AlertDialog.Builder(this);

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(logInValidation(username.getText().toString(), password.getText().toString())){
                    BUILDER.setTitle("Success");
                    BUILDER.setMessage("Login Successful");
                    System.out.println(username.getText().toString());
                    Intent intent = OverallGradeActivity.getIntent(getApplicationContext(), username.getText().toString());
                    startActivity(intent);
                } else {
                    BUILDER.setTitle("Fail");
                    BUILDER.setMessage("Incorrect Username or Password\n" + "Try again");
                }

                AlertDialog dialog = BUILDER.create();
                dialog.show();
            }
        });

        createUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = CreateUserActivity.getIntent(getApplicationContext(), username.getText().toString());
                startActivity(intent);
            }
        });

    }

    private boolean logInValidation(String username, String password){
        boolean validUsername = false, validPassword = false;
        for(User user : userList){
            if(user.getUsername().equals(username)){
                validUsername = true;
            }
            if(user.getPassword().equals(password)) {
                validPassword = true;
            }
        }
        return (validUsername && validPassword);
    }

    public static Intent getIntent(Context context, String username){
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(USER_NAME, username);

        return intent;
    }
}