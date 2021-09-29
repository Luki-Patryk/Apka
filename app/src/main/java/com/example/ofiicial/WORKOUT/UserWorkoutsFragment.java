package com.example.ofiicial.WORKOUT;

import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ofiicial.EXERCISES.ExercisesDataBaseAccess;
import com.example.ofiicial.R;

import java.util.ArrayList;

public class UserWorkoutsFragment extends Fragment
{

    private Context mContext;
    private ArrayList<Workout> workouts = new ArrayList<>();
    private ExercisesDataBaseAccess dataBaseAccess;
    private RecyclerView workoutsRecView;
    private WorkoutsRecViewAdapter adapter;

    public UserWorkoutsFragment(Context mContext)
    {
        this.mContext = mContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user_workouts, container, false);

        //TODO: Create table in database and display workouts for suggested and user workouts

        workouts.add(new Workout(0, "Pierwszy trening", true, "FBW", 8, null));

        adapter = new WorkoutsRecViewAdapter();
        adapter.setWorkouts(workouts);

        workoutsRecView = view.findViewById(R.id.suggested_workouts_recView);

        workoutsRecView.setAdapter(adapter);
        workoutsRecView.setLayoutManager(new LinearLayoutManager(mContext));

        return view;
    }
}