package com.example.ofiicial.WORKOUT;

import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ofiicial.EXERCISES.Exercises;
import com.example.ofiicial.R;

import java.util.ArrayList;

public class ExerciseCustomAttributesInAddExerciseToWorkoutRecViewAdapter extends RecyclerView.Adapter<ExerciseCustomAttributesInAddExerciseToWorkoutRecViewAdapter.ViewHolder>
{
    private ArrayList<Exercises> exercises = new ArrayList<>();
    public static ArrayList<ExerciseByWorkout> exerciseByWorkouts = new ArrayList<>();

    //Lists for tracking if any of EditText's is currently empty
    public static ArrayList<Boolean> isSetEmpty = new ArrayList<>();
    public static ArrayList<Boolean> isRepEmpty = new ArrayList<>();

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

    //TODO: Jezeli nic nie zmienimy przy cwiczeniu to domyslne wartosci beda 0;
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.exerciseName.setText(exercises.get(holder.getAdapterPosition()).getExercise_name());
        holder.exerciseType.setText(exercises.get(holder.getAdapterPosition()).getExercise_type());

        //when text changes in editText, replace exerciseByWorkout in arrayList with values from both editTexts
        holder.exerciseReps.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                int sets = 0;
                int reps = 0;
                String setsString, repsString;
                //setsString = holder.exerciseSets.getText();
                if(!holder.exerciseSets.getText().toString().isEmpty())
                {
                    sets = Integer.parseInt(holder.exerciseSets.getText().toString());
                    isSetEmpty.set(holder.getAdapterPosition(), true);
                }
                else
                {
                    isSetEmpty.set(holder.getAdapterPosition(), false);
                }

                if(!holder.exerciseReps.getText().toString().isEmpty())
                {
                    reps = Integer.parseInt(holder.exerciseReps.getText().toString());
                    isRepEmpty.set(holder.getAdapterPosition(), true);
                }
                else
                {
                    isRepEmpty.set(holder.getAdapterPosition(), false);
                }

                //loop through all elements of exercises list, and update current with values
                for(ExerciseByWorkout exerc: exerciseByWorkouts)
                {
                    if(exerc.getExercise_name().equals(holder.exerciseName.getText()))
                    {
                        exerciseByWorkouts.set(holder.getAdapterPosition(), new ExerciseByWorkout(exercises.get(holder.getAdapterPosition()),sets, reps));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });

        holder.exerciseSets.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                int sets = 0;
                int reps = 0;
                String setsString, repsString;
                //setsString = holder.exerciseSets.getText();
                if(!holder.exerciseSets.getText().toString().isEmpty())
                {
                    sets = Integer.parseInt(holder.exerciseSets.getText().toString());
                    isSetEmpty.set(holder.getAdapterPosition(), true);
                }
                else
                {
                    isSetEmpty.set(holder.getAdapterPosition(), false);
                }

                if(!holder.exerciseReps.getText().toString().isEmpty())
                {
                    reps = Integer.parseInt(holder.exerciseReps.getText().toString());
                    isRepEmpty.set(holder.getAdapterPosition(), true);
                }
                else
                {
                    isRepEmpty.set(holder.getAdapterPosition(), false);
                }

                for(ExerciseByWorkout exerc: exerciseByWorkouts)
                {
                    if(exerc.getExercise_name().equals(holder.exerciseName.getText()))
                    {
                        exerciseByWorkouts.set(holder.getAdapterPosition(), new ExerciseByWorkout(exercises.get(holder.getAdapterPosition()),sets, reps));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

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

        //Initialize lists with size of exercisesList
        for(int i = 0; i < getItemCount(); i++)
        {
            isRepEmpty.add(true);
            isSetEmpty.add(true);
        }

        //Adding every exercise to array list with sets and reps
        for(Exercises exerc: exercises)
        {
            exerciseByWorkouts.add(new ExerciseByWorkout(exerc));
        }

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
