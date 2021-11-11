package com.example.ofiicial.WORKOUT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.SearchView;

import com.example.ofiicial.EXERCISES.Exercises;
import com.example.ofiicial.EXERCISES.ExercisesDataBaseAccess;
import com.example.ofiicial.EXERCISES.ExercisesListRecViewAdapter;
import com.example.ofiicial.R;

import java.util.ArrayList;

public class AddExerciseToWorkoutActivity extends AppCompatActivity
{

    private Context mContext;
    private ArrayList<Exercises> exercises;
    private ExercisesDataBaseAccess dataBaseAccess;
    private RecyclerView exercisesRecView;
    private ExercisesListRecViewAdapter adapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise_to_workout);

        //View view =

        initViews();
    }

    void initViews()
    {
        adapter = new ExercisesListRecViewAdapter();

        //exercisesRecView = view.findViewById(R.id.add_exercise_to_workout_recView);
    }
}