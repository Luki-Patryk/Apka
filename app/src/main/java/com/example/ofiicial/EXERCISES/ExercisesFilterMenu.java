package com.example.ofiicial.EXERCISES;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioButton;

import com.example.ofiicial.R;

public class ExercisesFilterMenu extends AppCompatActivity
{

    RadioButton A_Z_radioBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_filter_menu);

        A_Z_radioBtn = findViewById(R.id.A_Z_radioBtn);
        A_Z_radioBtn.setChecked(true);

        //TODO: Create a RecyclerView adapter
    }
}