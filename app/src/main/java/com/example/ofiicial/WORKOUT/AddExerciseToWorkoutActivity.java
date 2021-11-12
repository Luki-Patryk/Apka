package com.example.ofiicial.WORKOUT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.ofiicial.R;

public class AddExerciseToWorkoutActivity extends AppCompatActivity
{
    private Intent intent;
    private int workout_id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise_to_workout);

        initViews();
    }

    void initViews()
    {
        intent = getIntent();
        workout_id = intent.getIntExtra("WORKOUT_ID",-1);
    }
}