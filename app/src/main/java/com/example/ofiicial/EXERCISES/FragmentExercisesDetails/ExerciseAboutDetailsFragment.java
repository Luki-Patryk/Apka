package com.example.ofiicial.EXERCISES.FragmentExercisesDetails;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ofiicial.EXERCISES.Exercises;
import com.example.ofiicial.R;

import java.util.ArrayList;


public class ExerciseAboutDetailsFragment extends Fragment {

    private Context mContext;

    private ArrayList<Exercises> exercises;

    public ExerciseAboutDetailsFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercise_about_details, container, false);
    }
}