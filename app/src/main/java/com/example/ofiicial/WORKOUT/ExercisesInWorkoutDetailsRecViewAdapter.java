package com.example.ofiicial.WORKOUT;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ofiicial.EXERCISES.Exercises;
import com.example.ofiicial.R;

import java.util.ArrayList;

public class ExercisesInWorkoutDetailsRecViewAdapter extends RecyclerView.Adapter<ExercisesInWorkoutDetailsRecViewAdapter.ViewHolder>
{

    private ArrayList<Exercises> exercises = new ArrayList<>();

    public ExercisesInWorkoutDetailsRecViewAdapter(){}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercises_in_workout_details_list , parent, false);
        ExercisesInWorkoutDetailsRecViewAdapter.ViewHolder vHolder = new ExercisesInWorkoutDetailsRecViewAdapter.ViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {

    }

    @Override
    public int getItemCount()
    {
        return exercises.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
        }
    }
}
