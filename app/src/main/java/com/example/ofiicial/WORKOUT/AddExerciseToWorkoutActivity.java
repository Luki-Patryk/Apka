package com.example.ofiicial.WORKOUT;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.example.ofiicial.EXERCISES.Exercises;
import com.example.ofiicial.EXERCISES.ExercisesDataBaseAccess;
import com.example.ofiicial.R;

import java.util.ArrayList;

public class AddExerciseToWorkoutActivity extends AppCompatActivity
{
    private Intent intent;
    private int workout_id;

    private Context context;
    private ArrayList<Exercises> exercises;
    private ExercisesDataBaseAccess dataBaseAccess;
    private RecyclerView exercisesRecView;
    private ExercisesInAddExerciseToWorkoutRecViewAdapter adapter;
    private SearchView searchView;
    private Button addBtn;
    private ArrayList<Integer> exerciseIDtoAdd = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise_to_workout);

        initViews();

        exercisesRecView.setAdapter(adapter);
        exercisesRecView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        dataBaseAccess = ExercisesDataBaseAccess.getInstance(this);
        dataBaseAccess.open();
        exercises = dataBaseAccess.getAllExercises();
        dataBaseAccess.close();
        adapter.setExercises(exercises);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                dataBaseAccess.open();
                exercises = dataBaseAccess.filterExercisesInSearchField(newText);
                dataBaseAccess.close();
                adapter.setExercises(exercises);
                return false;
            }
        });

        //Adding all selected exercises to workout and exiting this activity
        addBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addExercisesButtonPress();
            }
        });
    }

    void initViews()
    {
        intent = getIntent();
        workout_id = intent.getIntExtra("WORKOUT_ID",-1);
        exercisesRecView = findViewById(R.id.add_exercise_to_workout_recView);
        searchView = findViewById(R.id.add_exercise_to_workout_searchView);
        addBtn = findViewById(R.id.add_exercise_to_workout_addBtn);

        adapter = new ExercisesInAddExerciseToWorkoutRecViewAdapter();
    }

    void addExercisesButtonPress()
    {
        //Display warning when user don't have any exercises selected
        if(ExercisesInAddExerciseToWorkoutRecViewAdapter.exerciseIDtoAdd.size()==0)
        {
            new AlertDialog.Builder(AddExerciseToWorkoutActivity.this)
                    .setTitle("Warning")
                    .setMessage("You don't have any selected exercises to add")
                    .setNeutralButton("Okay", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }

        else
        {
            new AlertDialog.Builder(AddExerciseToWorkoutActivity.this)
                    .setTitle("Warning")
                    .setMessage("Do you want to add selected exercises with default amount of " +
                            "sets and reps or define your own?")
                    .setNegativeButton("Default", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {

                            AddExercisesDefault();
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("Custom", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            //TODO: Define method for adding exercises with custom amounts of reps and sets
                            AddExercisesCustom();
                            dialog.dismiss();
                        }
                    })
                    .show();
        }

        exerciseIDtoAdd = ExercisesInAddExerciseToWorkoutRecViewAdapter.exerciseIDtoAdd;
    }

    //Adding exercises with default values of sets and reps
    void AddExercisesDefault()
    {
        dataBaseAccess.open();
        dataBaseAccess.AddExercisesToWorkoutDefault(workout_id, exerciseIDtoAdd);
        dataBaseAccess.close();
        finish();
    }

    //Adding exercises with custom values of sets and reps
    void AddExercisesCustom()
    {
        Intent addCustomExerciseIntent = new Intent(getApplicationContext(), AddExerciseToWorkoutWIthCustomAttributesActivity.class);
        addCustomExerciseIntent.putExtra("EXERCISES_IDS", exerciseIDtoAdd);
        addCustomExerciseIntent.putExtra("WORKOUT_ID", workout_id);
        startActivityForResult(addCustomExerciseIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }
}
