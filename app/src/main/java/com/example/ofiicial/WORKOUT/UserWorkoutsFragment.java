package com.example.ofiicial.WORKOUT;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
    private ArrayList<Workout> suggestedWorkouts = new ArrayList<>(), userCreatedWorkouts = new ArrayList<>();
    private ExercisesDataBaseAccess dataBaseAccess;
    private RecyclerView suggestedWorkoutsRecView, userCreatedWorkoutsRecView;
    private WorkoutsRecViewAdapter suggestedWorkoutsAdapter, userCreatedWorkoutsAdapter;

    public UserWorkoutsFragment(Context mContext)
    {
        this.mContext = mContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user_workouts, container, false);

        //Handling top recview with suggested workouts
        suggestedWorkoutsAdapter = new WorkoutsRecViewAdapter();

        suggestedWorkoutsRecView = view.findViewById(R.id.suggested_workouts_recView);

        suggestedWorkoutsRecView.setAdapter(suggestedWorkoutsAdapter);
        suggestedWorkoutsRecView.setLayoutManager(new LinearLayoutManager(mContext));

        dataBaseAccess = ExercisesDataBaseAccess.getInstance(mContext);
        dataBaseAccess.open();
        suggestedWorkouts = dataBaseAccess.getSuggestedWorkoutsAtoZ();
        dataBaseAccess.close();
        suggestedWorkoutsAdapter.setWorkouts(suggestedWorkouts);


        //Handling bottom recview with user workouts
        userCreatedWorkoutsAdapter = new WorkoutsRecViewAdapter();

        userCreatedWorkoutsRecView = view.findViewById(R.id.user_workouts_recView);

        userCreatedWorkoutsRecView.setAdapter(userCreatedWorkoutsAdapter);
        userCreatedWorkoutsRecView.setLayoutManager(new LinearLayoutManager(mContext));

        dataBaseAccess.open();
        userCreatedWorkouts = dataBaseAccess.getUserCreatedWorkoutsAtoZ();
        dataBaseAccess.close();
        userCreatedWorkoutsAdapter.setWorkouts(userCreatedWorkouts);

        return view;
    }
}