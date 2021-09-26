package com.example.ofiicial.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.ofiicial.R;
import com.example.ofiicial.WORKOUT.PlansFragment;
import com.example.ofiicial.WORKOUT.SuggestedWorkoutsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class WorkoutFragment extends Fragment {
    private static final String TAG = "WorkoutFragment";

    private Context mContext;
    private BottomNavigationView topNavigationView;
    private PlansFragment plansFragment;
    private SuggestedWorkoutsFragment suggestedWorkoutsFragment;

    public WorkoutFragment(Context mContext)
    {
        this.mContext = mContext;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_workout, container, false);

        topNavigationView = view.findViewById(R.id.top_navigation_view);
        topNavigationView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) mContext);
        topNavigationView.setSelectedItemId(R.id.workouts);


        plansFragment = new PlansFragment();
        suggestedWorkoutsFragment = new SuggestedWorkoutsFragment();


        topNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.workouts:
                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.workout_fragment_container, suggestedWorkoutsFragment).commit();
                        Log.i(TAG, " workouts");
                        return true;

                    case R.id.plans:
                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.workout_fragment_container, plansFragment).commit();
                        return true;
                }


                return false;
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    //Function called every time when entering the workout segment, makes sure to load content from workouts
    @Override
    public void onStart()
    {
        super.onStart();
        topNavigationView.setSelectedItemId(R.id.workouts);
    }
}