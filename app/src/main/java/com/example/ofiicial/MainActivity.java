package com.example.ofiicial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ofiicial.Fragment.ExercisesFragment;
import com.example.ofiicial.Fragment.ProfileFragment;
import com.example.ofiicial.Fragment.WorkoutFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity  implements BottomNavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "MainActivity";

    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        //TODO: CREATE METHOD CHECKING IF USER IS LOGGED

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        //aplikacja zaczyna dzialac od zakladki workout
        bottomNavigationView.setSelectedItemId(R.id.workout);
    }

    ExercisesFragment exercisesFragment = new ExercisesFragment(this);
    ProfileFragment profileFragment = new ProfileFragment();
    WorkoutFragment workoutFragment = new WorkoutFragment();

    // po kliknieciu przyciku na bottom_nav wywoluje sie ta metoda
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.i(TAG, "onNavigationItemSelected: ");

        switch (item.getItemId()){
            case R.id.profile:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, profileFragment).commit();
                return true;
            case R.id.workout:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, workoutFragment).commit();
                return true;
            case R.id.exercises:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, exercisesFragment).commit();
                return true;
        }
        return false;
    }

    // TODO: Override back button!!!!
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }


    public String getUserEmailFromLoginActivity()
    {
        return getIntent().getStringExtra("EMAIL_PASS");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
