package com.example.project1_gradetracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.project1_gradetracker.DB.Course;

import java.time.LocalDate;

public class CreateCourseActivity extends AppCompatActivity {

    private EditText Title;
    private EditText ID;    // integer
    private EditText Instructor;
    private EditText Start;
    private EditText End;
    private EditText Description;
    //private EditText Catagory;
    private Button Create;
    private TextView Remind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);

        Title = (EditText)findViewById(R.id.etTitle);
        ID = (EditText)findViewById(R.id.etID);
        Instructor = (EditText)findViewById(R.id.etInstructor);
        Start = (EditText)findViewById(R.id.etStart);
        End = (EditText)findViewById(R.id.etEnd);
        Description = (EditText)findViewById(R.id.etDescription);
        //Catagory = (EditText)findViewById(R.id.etCatagory);
        Create = (Button)findViewById(R.id.btnCreate);
        Remind = (TextView)findViewById(R.id.tvRemind);

        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean invalid = false;

                Course course = new Course();
                course.setTitle(Title.getText().toString());
                course.setCourseID(Integer.parseInt(ID.getText().toString()));
                course.setInstructor(Instructor.getText().toString());
                course.setDateStart(Start.getText().toString());
                course.setDateEnd(End.getText().toString());
                course.setDescription(Description.getText().toString());

                // After click Create button and success create a course, it will take u back to garbage page
                backToGarbage();
            }
        });
    }

    /*private void createC(String title, int courseID, String instructor, LocalDate dateStart,
                         LocalDate dateEnd, String description){
        // Check database for courseID, if it doesn’t exist then add course.

    }*/

    public void backToGarbage(){
        Intent intent = new Intent(CreateCourseActivity.this, GarbageActivity.class);
        startActivity(intent);
    }
}