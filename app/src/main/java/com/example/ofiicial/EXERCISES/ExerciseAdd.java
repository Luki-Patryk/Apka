package com.example.ofiicial.EXERCISES;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ofiicial.R;

public class ExerciseAdd extends AppCompatActivity
{

    private EditText name, type;
    private Button btnAdd, btnCancel;

    private ExercisesDataBaseAccess dataBaseAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_add);

        name = findViewById(R.id.add_exercise_name_editText);
        type = findViewById(R.id.add_exercise_type_editText);
        btnAdd = findViewById(R.id.confirm_add_btn);
        btnCancel = findViewById(R.id.cancel_add_btn);

        //Back to previous activity (which is ExercisesFragment) without adding a new exercise to database
        btnCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        //Add new Exercise and back to previous activity
        btnAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String nameString, typeString;
                nameString = name.getText().toString();
                typeString = type.getText().toString();

                dataBaseAccess = ExercisesDataBaseAccess.getInstance(getApplicationContext());
                dataBaseAccess.open();
                dataBaseAccess.addExercise(nameString, typeString, "NULL");
                dataBaseAccess.close();

                //Declaring intent with result so that we can call onActivityResult function inside ExerciseFragment and refresh database
                Intent intent = new Intent();
                setResult(1, intent);
                finish();
            }
        });

    }
}