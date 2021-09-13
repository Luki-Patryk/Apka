package com.example.ofiicial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ofiicial.EXERCISES.Exercises;

import java.util.ArrayList;

public class ExercisesRecViewAdapter extends RecyclerView.Adapter<ExercisesRecViewAdapter.ViewHolder>
{

    private ArrayList<Exercises> exercises = new ArrayList<>();
    private ArrayList<Exercises> filteredExercises = new ArrayList<>();

    public ExercisesRecViewAdapter()
    {
    }

    @NonNull
    @Override
    public ExercisesRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercises_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.exerciseName.setText(exercises.get(position).getExercise_name());
        holder.exerciseType.setText(exercises.get(position).getExercise_type());
    }

    @Override
    public int getItemCount()
    {
        return exercises.size();
    }

    public void setExercises(ArrayList<Exercises> exercises)
    {
        this.exercises = exercises;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView exerciseName;
        private TextView exerciseType;
        private ImageView exerciseImg;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exerciseName);
            exerciseImg = itemView.findViewById(R.id.exerciseImg);
            exerciseType = itemView.findViewById(R.id.exerciseType);
        }
    }

}
