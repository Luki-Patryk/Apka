package com.example.ofiicial.WORKOUT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private Button backButton, editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_details);

        initViews();

        //go back to previous activity
        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
            }
        });

    }

    private void initViews()
    {
        workoutNameTextView = findViewById(R.id.workoutDetails_name_textView);
        exercisesCountTextView = findViewById(R.id.workoutDetails_exercises_count_textView);
        estimatedTimeTextView = findViewById(R.id.workoutDetails_workout_estimated_time);
        backButton = findViewById(R.id.workoutDetails_back_btn);
        editButton = findViewById(R.id.workoutDetails_edit_btn);

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


        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(exercisesInWorkoutDetailsRecView);
    }


    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT)
    {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target)
        {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction)
        {
            dataBaseAccess.open();
            //get id of exerciseByWorkout by getting a position of dragged item from viewHolder
            dataBaseAccess.deleteExerciseByWorkout(exerciseByWorkouts.get(viewHolder.getAdapterPosition()).getID());
            exerciseByWorkouts = dataBaseAccess.getExercisesByWorkoutAtoZ(workout_id);
            exercisesInWorkoutDetailsAdapter.setExerciseByWorkouts(exerciseByWorkouts);
            dataBaseAccess.close();
        }
    };
}