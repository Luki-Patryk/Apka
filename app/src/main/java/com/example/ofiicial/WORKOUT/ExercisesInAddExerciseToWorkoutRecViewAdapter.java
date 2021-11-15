package com.example.ofiicial.WORKOUT;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ofiicial.EXERCISES.Exercises;
import com.example.ofiicial.R;

import java.util.ArrayList;

public class ExercisesInAddExerciseToWorkoutRecViewAdapter extends RecyclerView.Adapter<ExercisesInAddExerciseToWorkoutRecViewAdapter.ViewHolder>
{
    public ExercisesInAddExerciseToWorkoutRecViewAdapter(){}

    private ArrayList<Exercises> exercises = new ArrayList<>();
    public static ArrayList<Integer> exerciseIDtoAdd = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercises_in_add_exercise_to_workout_list, parent, false);
        ExercisesInAddExerciseToWorkoutRecViewAdapter.ViewHolder vHolder = new ExercisesInAddExerciseToWorkoutRecViewAdapter.ViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.exerciseName.setText(exercises.get(position).getExercise_name());
        holder.exerciseType.setText(exercises.get(position).getExercise_type());

        //Adding exercises to list when state of the checkbox changes
        holder.exerciseCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    exerciseIDtoAdd.add(exercises.get(position).getID());
                }
                else
                {
                    exerciseIDtoAdd.remove(exercises.get(position).getID());
                }
            }
        });
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
        private TextView exerciseName, exerciseType;
        private ImageView exerciseImg;
        private CheckBox exerciseCheckbox;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exercise_in_add_exercise_to_workout_name);
            exerciseType = itemView.findViewById(R.id.exercise_in_add_exercise_to_workout_type);
            exerciseImg = itemView.findViewById(R.id.exercise_in_add_exercise_to_workout_image);
            exerciseCheckbox = itemView.findViewById(R.id.exercise_add_to_workout_checkBox);
        }
    }
}
