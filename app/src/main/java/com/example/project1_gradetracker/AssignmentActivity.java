package com.example.project1_gradetracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.project1_gradetracker.LoginActivity.USER_NAME;

public class AssignmentActivity extends AppCompatActivity {

    private Button buttonAddA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        buttonAddA = findViewById(R.id.buttonAddAssignment);

        buttonAddA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssignmentActivity.this, CreateAssignmentActivity.class);
                startActivity(intent);
            }
        });

    }

    public static Intent getIntent(Context context, String username){
        Intent intent = new Intent(context, AssignmentActivity.class);
        intent.putExtra(USER_NAME, username);

        return intent;
    }
}