package com.example.project1_gradetracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText userName, password;
    private Button logInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        logInButton = findViewById(R.id.btnLogin);

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logInValidation(userName.getText().toString(), password.getText().toString());
            }
        });

    }

    private void logInValidation(String adminUserName, String adminPassword){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if((adminUserName.equals("din_djarin")) && (adminPassword.equals("baby_yoda_ftw"))){
            builder.setTitle("Success");
            builder.setMessage("Login Successful");
            Intent intent = new Intent(LoginActivity.this, GarbageActivity.class);
            startActivity(intent);
        }else{
            builder.setTitle("Fail");
            builder.setMessage("Incorrect username or password\n" + "Try again");
        }

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}