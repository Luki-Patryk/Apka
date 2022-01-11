package com.example.ofiicial.WORKOUT;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ofiicial.EXERCISES.Exercises;
import com.example.ofiicial.EXERCISES.ExercisesDataBaseAccess;
import com.example.ofiicial.R;

import java.util.ArrayList;

public class AddExerciseToWorkoutWIthCustomAttributesActivity extends AppCompatActivity
{
    private ExercisesDataBaseAccess dataBaseAccess;
    private RecyclerView exercisesAttributesRecView;
    private ExerciseCustomAttributesInAddExerciseToWorkoutRecViewAdapter adapter;
    private ArrayList<Integer> exerciseIDtoAdd = new ArrayList<>();
    private ArrayList<Exercises> exercisesToAdd = new ArrayList<>();
    private Intent intent;
    private Button submitButton;
    private Integer workout_id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise_to_workout_with_custom_attributes);

        initViews();

        exercisesAttributesRecView.setAdapter(adapter);
        exercisesAttributesRecView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        dataBaseAccess = ExercisesDataBaseAccess.getInstance(this);
        dataBaseAccess.open();
        exercisesToAdd = dataBaseAccess.getExercisesByID(exerciseIDtoAdd);
        dataBaseAccess.close();
        adapter.setExercises(exercisesToAdd);

        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addCustomExercises();
            }
        });
    }

    private void initViews()
    {
        submitButton = findViewById(R.id.submit_custom_exercises_addBtn);
        intent = getIntent();
        exerciseIDtoAdd = intent.getIntegerArrayListExtra("EXERCISES_IDS");
        workout_id = intent.getIntExtra("WORKOUT_ID",-1);
        exercisesAttributesRecView = findViewById(R.id.add_exercise_to_workout_with_custom_attributes_recView);

        adapter = new ExerciseCustomAttributesInAddExerciseToWorkoutRecViewAdapter();
    }

    //TODO: Make sure there is no empty exercises
    private void addCustomExercises()
    {
        boolean isEmpty = checkIfIsntEmpty();

        //If any of sets and reps fields is empty, warn the user
        if(isEmpty)
        {
            new AlertDialog.Builder(this)
                    .setTitle("Warning")
                    .setMessage("You must fill all the Sets and Reps fields before adding exercises to workout")
                    .setNeutralButton("Okay", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {

                        }
                    })
                    .show();

            return;
        }

        //checkIfFilled();

        dataBaseAccess.open();
        dataBaseAccess.AddExercisesToWorkoutCustom(workout_id, exerciseIDtoAdd, ExerciseCustomAttributesInAddExerciseToWorkoutRecViewAdapter.exerciseByWorkouts);
        dataBaseAccess.close();
        ExercisesInAddExerciseToWorkoutRecViewAdapter.exerciseIDtoAdd.clear();
        setResult(1);
        finish();
    }

    //Check if any of editText's are empty
    private boolean checkIfIsntEmpty()
    {
        if(ExerciseCustomAttributesInAddExerciseToWorkoutRecViewAdapter.isRepEmpty.contains(false)
        || ExerciseCustomAttributesInAddExerciseToWorkoutRecViewAdapter.isSetEmpty.contains(false))
        {
            return true;
        }

        return false;
    }

    //If any of exercises wasn't edited by user, update it with default values;
    private void checkIfFilled()
    {
        int i = 0;
        for(ExerciseByWorkout exercise: ExerciseCustomAttributesInAddExerciseToWorkoutRecViewAdapter.exerciseByWorkouts)
        {
            if(exercise.getReps() == 0 && exercise.getSets() != 0)
            {
                ExerciseCustomAttributesInAddExerciseToWorkoutRecViewAdapter.exerciseByWorkouts.set(i,new ExerciseByWorkout(exercise.getID(), exercise.getExercise_name(), exercise.getExercise_type(), exercise.getImg_URL(), exercise.isIs_original(), exercise.getSets(), 8));
            }
            else if(exercise.getSets() == 0 && exercise.getReps() != 0)
            {
                ExerciseCustomAttributesInAddExerciseToWorkoutRecViewAdapter.exerciseByWorkouts.set(i,new ExerciseByWorkout(exercise.getID(), exercise.getExercise_name(), exercise.getExercise_type(), exercise.getImg_URL(), exercise.isIs_original(), 3, exercise.getReps()));
            }
            else if(exercise.getSets() == 0 && exercise.getReps() == 0)
            {
                ExerciseCustomAttributesInAddExerciseToWorkoutRecViewAdapter.exerciseByWorkouts.set(i,new ExerciseByWorkout(exercise.getID(), exercise.getExercise_name(), exercise.getExercise_type(), exercise.getImg_URL(), exercise.isIs_original(), 3, 8));
            }
        }
    }
}