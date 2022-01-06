package com.example.ofiicial.WORKOUT;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ofiicial.EXERCISES.Exercises;
import com.example.ofiicial.R;

import java.util.ArrayList;

public class ExerciseCustomAttributesInAddExerciseToWorkoutRecViewAdapter extends RecyclerView.Adapter<ExerciseCustomAttributesInAddExerciseToWorkoutRecViewAdapter.ViewHolder>
{
    private ArrayList<Exercises> exercises = new ArrayList<>();

    public ExerciseCustomAttributesInAddExerciseToWorkoutRecViewAdapter(){}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_add_custom_attributes_list,
                parent, false);
        ViewHolder vHolder = new ViewHolder(view);
        return vHolder;
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
        private EditText exerciseSets;
        private EditText exerciseReps;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exercise_name_in_exercise_custom_add_TextView);
            exerciseType = itemView.findViewById(R.id.exercise_type_in_exercise_custom_add_TextView);
            exerciseSets = itemView.findViewById(R.id.exercise_sets_in_exercise_custom_add_EditText);
            exerciseReps = itemView.findViewById(R.id.exercise_reps_in_exercise_custom_add_EditText);
        }
    }
}
