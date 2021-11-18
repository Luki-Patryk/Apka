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
import com.example.ofiicial.EXERCISES.ExercisesDataBaseAccess;
import com.example.ofiicial.R;

import java.sql.Struct;
import java.util.ArrayList;

public class ExercisesInAddExerciseToWorkoutRecViewAdapter extends RecyclerView.Adapter<ExercisesInAddExerciseToWorkoutRecViewAdapter.ExercisesInWorkoutAddViewHolder>
{
    public ExercisesInAddExerciseToWorkoutRecViewAdapter(){}

    private ArrayList<Exercises> exercises = new ArrayList<>();
    public static ArrayList<Integer> exerciseIDtoAdd = new ArrayList<>();
    private ExercisesDataBaseAccess dataBaseAccess;
    private boolean[] isCheckedArray;


    @NonNull
    @Override
    public ExercisesInWorkoutAddViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercises_in_add_exercise_to_workout_list, parent, false);
        ExercisesInWorkoutAddViewHolder vHolder = new ExercisesInWorkoutAddViewHolder(view);

        //Getting size of exercises table to set size of table that will store data about checkedState
        dataBaseAccess = ExercisesDataBaseAccess.getInstance(parent.getContext());
        dataBaseAccess.open();
        isCheckedArray = new boolean[dataBaseAccess.getExercisesCount()];
        dataBaseAccess.close();

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExercisesInWorkoutAddViewHolder holder, int position)
    {
        holder.exerciseName.setText(exercises.get(holder.getAdapterPosition()).getExercise_name());
        holder.exerciseType.setText(exercises.get(holder.getAdapterPosition()).getExercise_type());

        //Adding exercises to list when state of the checkbox changes
        holder.exerciseCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    exerciseIDtoAdd.add(exercises.get(holder.getAdapterPosition()).getID());

                    //Tracking which exercises was checked by user to prevent any unwanted checks
                    isCheckedArray[holder.getAdapterPosition()] = true;

                }
                else
                {
                    //Checking if exercise is in array, in case of trying to delete object that
                    //doesn't exists
                    if(exerciseIDtoAdd.contains(exercises.get(holder.getAdapterPosition()).getID()))
                    {
                        exerciseIDtoAdd.remove(exercises.get(holder.getAdapterPosition()).getID());
                    }

                    //Tracking which exercises was checked by user to prevent any unwanted checks
                    isCheckedArray[holder.getAdapterPosition()] = false;

                }
            }
        });

        //Setting checked state based on table;
        holder.exerciseCheckbox.setChecked(isCheckedArray[holder.getAdapterPosition()]);
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

    public class ExercisesInWorkoutAddViewHolder extends RecyclerView.ViewHolder
    {
        private TextView exerciseName, exerciseType;
        private ImageView exerciseImg;
        private CheckBox exerciseCheckbox;

        public ExercisesInWorkoutAddViewHolder(@NonNull View itemView)
        {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exercise_in_add_exercise_to_workout_name);
            exerciseType = itemView.findViewById(R.id.exercise_in_add_exercise_to_workout_type);
            exerciseImg = itemView.findViewById(R.id.exercise_in_add_exercise_to_workout_image);
            exerciseCheckbox = itemView.findViewById(R.id.exercise_add_to_workout_checkBox);
        }
    }
}
