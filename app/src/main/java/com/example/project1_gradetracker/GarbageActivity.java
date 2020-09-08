package com.example.project1_gradetracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class GarbageActivity extends AppCompatActivity {

    final static String USER_NAME = "USERNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garbage);
    }

    public static Intent getIntent(Context context, String username){
        Intent intent = new Intent(context, GarbageActivity.class);
        intent.putExtra(USER_NAME, username);

        return intent;
    }
}