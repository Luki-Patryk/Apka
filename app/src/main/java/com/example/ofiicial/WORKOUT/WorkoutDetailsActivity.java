package com.example.ofiicial.WORKOUT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ofiicial.EXERCISES.ExercisesDataBaseAccess;
import com.example.ofiicial.R;

import java.util.ArrayList;

public class WorkoutDetailsActivity extends AppCompatActivity {

    private int workout_id;
    private TextView workoutNameTextView, exercisesCountTextView, estimatedTimeTextView;
    private Intent intent;
    private Workout workout;
    private ExercisesDataBaseAccess dataBaseAccess;
    private ArrayList<ExerciseByWorkout> exerciseByWorkouts = new ArrayList<>();
    private RecyclerView exercisesInWorkoutDetailsRecView;
    private ExercisesInWorkoutDetailsRecViewAdapter exercisesInWorkoutDetailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_details);

        initViews();
    }

    private void initViews()
    {
        workoutNameTextView = findViewById(R.id.workoutDetails_name_textView);
        exercisesCountTextView = findViewById(R.id.workoutDetails_exercises_count_textView);
        estimatedTimeTextView = findViewById(R.id.workoutDetails_workout_estimated_time);

        intent = getIntent();
        workout_id = intent.getIntExtra("WORKOUT_ID", 0);
        //TODO: if workout_id value is 0 create warning because something went wrong

        dataBaseAccess = ExercisesDataBaseAccess.getInstance(this);
        dataBaseAccess.open();
        workout = dataBaseAccess.getWorkoutById(intent.getIntExtra("WORKOUT_ID", 0));
        dataBaseAccess.close();

        workoutNameTextView.setText(workout.getWorkout_name());
        exercisesCountTextView.setText("Exercises count: " + String.valueOf(workout.getExercises_count()));
        estimatedTimeTextView.setText("Estimated time: " + String.valueOf(workout.getEstimated_workout_time()) + " minutes");

        //Initializing RecyclerView
        exercisesInWorkoutDetailsAdapter = new ExercisesInWorkoutDetailsRecViewAdapter();

        exercisesInWorkoutDetailsRecView = findViewById(R.id.exercises_in_workoutDetails_recView);

        exercisesInWorkoutDetailsRecView.setAdapter(exercisesInWorkoutDetailsAdapter);
        exercisesInWorkoutDetailsRecView.setLayoutManager(new LinearLayoutManager(this));

        dataBaseAccess.open();
        exerciseByWorkouts = dataBaseAccess.getExercisesByWorkoutAtoZ(workout_id);
        dataBaseAccess.close();
        exercisesInWorkoutDetailsAdapter.setExerciseByWorkouts(exerciseByWorkouts);



        Toast.makeText(getApplicationContext(), String.valueOf(workout.getID()), Toast.LENGTH_SHORT).show();
    }
}