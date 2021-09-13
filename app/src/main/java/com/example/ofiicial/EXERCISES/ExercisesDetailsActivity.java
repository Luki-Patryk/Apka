package com.example.ofiicial.EXERCISES;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

import com.example.ofiicial.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class ExercisesDetailsActivity extends AppCompatActivity {

    TextView exercise_name_details;
    TabLayout tab_layout;
    TabItem tab_about, tab_history, tab_charts, tab_records;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_details);

        exercise_name_details = findViewById(R.id.exercise_name_details);
        exercise_name_details.setText(getIntent().getStringExtra("EX_NAME"));

        tab_layout = findViewById(R.id.tab_bar);
        tab_about = findViewById(R.id.tab_about);
        tab_history = findViewById(R.id.tab_history);
        tab_charts = findViewById(R.id.tab_charts);
        tab_records = findViewById(R.id.tab_records);
        viewPager = findViewById(R.id.viewPager);


        // This adapter will pass the data from the tabs and display them
        //  into the view pager
        ExercisesDetailsPagerAdapter pagerAdapter = new ExercisesDetailsPagerAdapter(getSupportFragmentManager(), tab_layout.getTabCount());

        viewPager.setAdapter(pagerAdapter);


        // Changing tabs on users click
        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}

