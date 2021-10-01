package com.example.ofiicial.WORKOUT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ofiicial.EXERCISES.ExercisesDataBaseAccess;
import com.example.ofiicial.R;

public class WorkoutDetailsActivity extends AppCompatActivity {

    private int workout_id;
    private Intent intent;
    private Workout workout;
    private ExercisesDataBaseAccess dataBaseAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_details);

        intent = getIntent();
        workout_id = intent.getIntExtra("WORKOUT_ID", 0);
        //TODO: if workout_id value is 0 create warning because something went wrong

        dataBaseAccess = ExercisesDataBaseAccess.getInstance(this);
        dataBaseAccess.open();
        workout = dataBaseAccess.getWorkoutById(intent.getIntExtra("WORKOUT_ID", 0));
        dataBaseAccess.close();

        Toast.makeText(getApplicationContext(), String.valueOf(workout.getID()), Toast.LENGTH_SHORT).show();
    }
}