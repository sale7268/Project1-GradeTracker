package com.example.project1_gradetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1_gradetracker.DB.Assignment;

import java.util.List;

public class AssignmentListAdapter extends RecyclerView.Adapter<AssignmentListAdapter.ViewHolder> {

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mAssignment;
        public TextView mGrade;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mAssignment = (TextView) itemView.findViewById(R.id.tvAssignmentName);
            mGrade = (TextView) itemView.findViewById(R.id.tvAssignmentGrade);

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
    private List<Assignment> mAssignmentList;

    // Pass in the contact array into the constructor
    public AssignmentListAdapter(List<Assignment> assignments) {
        mAssignmentList = assignments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        // we say R.layout.assignment because assignment is the name of the layout (xml) we created
        // to hold our assignment name and grade
        View contactView = inflater.inflate(R.layout.assignment, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Assignment currentAssignment = mAssignmentList.get(position);
        String grade = String.valueOf(currentAssignment.getGrade());
        String totalPoints = String.valueOf(currentAssignment.getPoints());

        holder.mAssignment.setText(currentAssignment.getTitle());
        holder.mGrade.setText(grade + '/' + totalPoints);
    }

    @Override
    public int getItemCount() {
        return mAssignmentList.size();
    }

}
