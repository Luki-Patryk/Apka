package com.example.ofiicial.WORKOUT;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ofiicial.R;

import java.util.ArrayList;

public class ExercisesInWorkoutDetailsRecViewAdapter extends RecyclerView.Adapter<ExercisesInWorkoutDetailsRecViewAdapter.ViewHolder>
{

    private ArrayList<ExerciseByWorkout> exerciseByWorkouts = new ArrayList<>();

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
        holder.exerciseName.setText(exerciseByWorkouts.get(position).getExercise_name());
        holder.exerciseType.setText(exerciseByWorkouts.get(position).getExercise_type());
        holder.exerciseRepsSets.setText("Reps: " + String.valueOf(exerciseByWorkouts.get(position).getSets()) + "x" + String.valueOf(exerciseByWorkouts.get(position).getReps()));
    }

    @Override
    public int getItemCount()
    {
        return exerciseByWorkouts.size();
    }

    public void setExerciseByWorkouts(ArrayList<ExerciseByWorkout> exerciseByWorkouts)
    {
        this.exerciseByWorkouts = exerciseByWorkouts;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        private ImageView exerciseImage;
        private TextView exerciseType, exerciseName, exerciseRepsSets;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            exerciseImage = itemView.findViewById(R.id.exercise_in_add_exercise_to_workout_image);
            exerciseName = itemView.findViewById(R.id.exercise_in_add_exercise_to_workout_name);
            exerciseType = itemView.findViewById(R.id.exercise_in_add_exercise_to_workout_type);
            exerciseRepsSets = itemView.findViewById(R.id.exercise_in_workoutDetails_reps);
        }
    }
}
