package com.example.ofiicial.EXERCISES;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ofiicial.R;

import java.util.ArrayList;

public class ExerciseEdit extends AppCompatActivity
{

    private EditText exerciseName, exerciseType;
    private Button btnCancel, btnConfirm, btnDelete;
    private String exercise_name_string, exercise_type_string;
    private Spinner typesSpinner;
    private ExercisesDataBaseAccess dataBaseAccess;
    private ArrayList<ExercisesTypes> exercisesTypesArrayList;
    private ArrayList<String> exercisesTypesString = new ArrayList<>(); //Array list used for displaying exercise types in spinner
    private boolean isClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_edit);

        initViews();

        //Initializing types spinner
        initTypesSpinner();

        Intent intent = new Intent();

        typesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(isClicked)
                {
                    exerciseType.setText(typesSpinner.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        typesSpinner.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                isClicked = true;
                return false;
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setResult(0, intent);
                finish();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String newName, newType;
                newName = exerciseName.getText().toString();
                newType = exerciseType.getText().toString();

                intent.putExtra("NEW_EXERCISE_NAME", newName);
                intent.putExtra("NEW_EXERCISE_TYPE", newType);

                setResult(1, intent);
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setResult(2, intent);
                finish();
            }
        });
    }

    private void initViews()
    {
        exerciseName = findViewById(R.id.editTxt_exercise_edit_name);
        exerciseType = findViewById(R.id.editTxt_exercise_edit_type);

        btnCancel = findViewById(R.id.btn_exercise_edit_cancel);
        btnConfirm = findViewById(R.id.btn_exercise_edit_confirm);
        btnDelete = findViewById(R.id.btn_exercise_edit_delete);
        typesSpinner = findViewById(R.id.exercise_edit_types_spinner);

        Intent intent = getIntent();
        exercise_name_string = intent.getStringExtra("EXERCISE_NAME");
        exercise_type_string = intent.getStringExtra("EXERCISE_TYPE");

        exerciseName.setText(exercise_name_string);
    }

    //Initializing spinner with exercises types
    private void initTypesSpinner()
    {
        dataBaseAccess = ExercisesDataBaseAccess.getInstance(this);
        dataBaseAccess.open();
        exercisesTypesArrayList = dataBaseAccess.getExercisesTypesAtoZ();
        dataBaseAccess.close();

        //converting list of ExercisesTypes to String list
        for(ExercisesTypes type: exercisesTypesArrayList)
        {
            exercisesTypesString.add(type.getType());
        }

        //Setting adapter for the spinner
        ArrayAdapter<String> typesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, exercisesTypesString);
        typesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typesSpinner.setAdapter(typesAdapter);

        exerciseType.setText(exercise_type_string);
    }
}