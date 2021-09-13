package com.example.ofiicial.EXERCISES;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ofiicial.Fragment.ExercisesFragment;
import com.example.ofiicial.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class ExercisesDetailsActivity extends AppCompatActivity {

    private TextView exercise_name_details, exit_details_view;
    private TabLayout tab_layout;
    private TabItem tab_about, tab_history, tab_charts, tab_records;
    private ViewPager viewPager;
    private Button editBtn;
    private ExercisesDataBaseAccess dataBaseAcces;

    String exercise_name, exercise_type;
    int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_details);

        initViews();

        boolean isOriginal= getIntent().getBooleanExtra("IS_ORIGINAL", true);

        //If exercise was originally in application hide edit button
        if(isOriginal)
        {
            editBtn.setVisibility(View.GONE);
        }
        else
        {
            editBtn.setVisibility(View.VISIBLE);
        }

        // This adapter will pass the data from the tabs and display them
        //  into the view pager
        ExercisesDetailsPagerAdapter pagerAdapter = new ExercisesDetailsPagerAdapter(getSupportFragmentManager(), tab_layout.getTabCount());

        viewPager.setAdapter(pagerAdapter);

        //put exercise name and type to display in activity
        editBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), ExerciseEdit.class);
                intent.putExtra("EXERCISE_NAME", exercise_name);
                intent.putExtra("EXERCISE_TYPE", exercise_type);
                startActivityForResult(intent, 0);
            }
        });

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

        exit_details_view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    void initViews()
    {
        Intent intent = getIntent();

        exercise_name = intent.getStringExtra("EX_NAME");
        exercise_type = intent.getStringExtra("EX_TYPE");
        ID = intent.getIntExtra("EX_ID",2);

        exercise_name_details = findViewById(R.id.exercise_name_details);
        exercise_name_details.setText(intent.getStringExtra("EX_NAME") + " ID: " + ID);

        tab_layout = findViewById(R.id.tab_bar);
        tab_about = findViewById(R.id.tab_about);
        tab_history = findViewById(R.id.tab_history);
        tab_charts = findViewById(R.id.tab_charts);
        tab_records = findViewById(R.id.tab_records);
        viewPager = findViewById(R.id.viewPager);
        editBtn = findViewById(R.id.edit_exercise_details);
        exit_details_view = findViewById(R.id.close_exercise_details);

    }


    /*  0 - Cancel
        1 - Edit
        2 - Delete
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode)
        {
            case 0:
                Toast.makeText(this, "Cancelled correctly", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                //Getting new attributes from previous activity and passing them to function which will change it in database
                String newName, newType;
                newName = data.getStringExtra("NEW_EXERCISE_NAME");
                newType = data.getStringExtra("NEW_EXERCISE_TYPE");

                dataBaseAcces = ExercisesDataBaseAccess.getInstance(this);
                dataBaseAcces.open();
                dataBaseAcces.changeExercise(ID, newName, newType);
                dataBaseAcces.close();
                //if name was changed, change it in top of the activity
                if(newName != null)
                {
                    exercise_name_details.setText(newName);
                }
                break;
            case 2:
                dataBaseAcces = ExercisesDataBaseAccess.getInstance(this);
                dataBaseAcces.open();
                dataBaseAcces.deleteExercise(ID);
                dataBaseAcces.close();
                break;
        }
    }
}
