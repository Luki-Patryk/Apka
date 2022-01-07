package com.example.ofiicial.WORKOUT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        dataBaseAccess.open();
        dataBaseAccess.AddExercisesToWorkoutCustom(workout_id, exerciseIDtoAdd, ExerciseCustomAttributesInAddExerciseToWorkoutRecViewAdapter.exerciseByWorkouts);
        dataBaseAccess.close();
        ExercisesInAddExerciseToWorkoutRecViewAdapter.exerciseIDtoAdd.clear();
        setResult(1);
        finish();
    }
}