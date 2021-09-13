package com.example.ofiicial.EXERCISES;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.ofiicial.EXERCISES.FragmentExercisesDetails.ExerciseAboutDetailsFragment;
import com.example.ofiicial.EXERCISES.FragmentExercisesDetails.ExerciseChartsDetailsFragment;
import com.example.ofiicial.EXERCISES.FragmentExercisesDetails.ExerciseHistoryDetailsFragment;
import com.example.ofiicial.EXERCISES.FragmentExercisesDetails.ExerciseRecordsDetailsFragment;

public class ExercisesDetailsPagerAdapter extends FragmentPagerAdapter {

    int num_of_tabs;

    public ExercisesDetailsPagerAdapter(FragmentManager fragmentManager, int num_of_tabs)
    {
        super(fragmentManager);
        this.num_of_tabs = num_of_tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ExerciseAboutDetailsFragment();
            case 1:
                return new ExerciseHistoryDetailsFragment();
            case 2:
                return new ExerciseChartsDetailsFragment();
            case 3:
                return new ExerciseRecordsDetailsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return num_of_tabs;
    }
}
