package com.example.project1_gradetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1_gradetracker.DB.Course;

import java.util.List;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.ViewHolder> {

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mCourse;
        public TextView mGrade;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mCourse = (TextView) itemView.findViewById(R.id.tvCourseName);
            mGrade = (TextView) itemView.findViewById(R.id.tvCourseGrade);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    // Store a member variable for the contacts
    private List<Course> mCourseList;

    // Pass in the contact array into the constructor
    public CourseListAdapter(List<Course> courses) {
        mCourseList = courses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        // we say R.layout.course because course is the name of the layout (xml) we created
        // to hold our course name and grade
        View contactView = inflater.inflate(R.layout.course, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Course currentCourse = mCourseList.get(position);

        String grade = String.format("%.2f", currentCourse.getTotalGrade());
        holder.mCourse.setText(currentCourse.getTitle());
        holder.mGrade.setText("                                           0"); // TODO: once grade is calculated, use getGrade()
    }

    @Override
    public int getItemCount() {
        return mCourseList.size();
    }

}
