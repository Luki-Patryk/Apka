package com.example.ofiicial.EXERCISES;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class ExerciseAdd extends AppCompatActivity
{

    private EditText name, type;
    private Button btnAdd, btnCancel;
    private ExercisesDataBaseAccess dataBaseAccess;
    private ArrayList<ExercisesTypes> exerciseTypes;
    private ArrayList<String> exerciseTypesString = new ArrayList<>();
    private Spinner typesSpinner;
    private boolean isClicked = false;
    private boolean isEmpty = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_add);

        name = findViewById(R.id.add_exercise_name_editText);
        type = findViewById(R.id.add_exercise_type_editText);
        btnAdd = findViewById(R.id.confirm_add_btn);
        btnCancel = findViewById(R.id.cancel_add_btn);
        typesSpinner = findViewById(R.id.exercise_add_types_spinner);

        //Initialize spinner
        initTypesSpinner();

        //Setting text in editText equal to selected type
        typesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                //If activity just started EditText will be clear
                if(isClicked)
                {
                    type.setText(typesSpinner.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        //Checking if spinner was touched to prevent setting text in EditText at start of activity
        typesSpinner.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                isClicked = true;
                return false;
            }
        });


        //Add new Exercise and back to previous activity
        btnAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Tracking if fields are empty and dialog will be shown, if that's true, then we can't finish this activity
                isEmpty = false;

                if(name.getText().toString().matches("") || type.getText().toString().matches(""))
                {
                    isEmpty = true;

                    new AlertDialog.Builder(ExerciseAdd.this)
                            .setTitle("Warning")
                            .setMessage("U can't add an exercise while one of the fields is empty")
                            .setNeutralButton("OKAY", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    dialog.cancel();
                                }
                            })
                            .show();
                }

                String nameString, typeString;
                nameString = name.getText().toString();
                typeString = type.getText().toString();

                dataBaseAccess = ExercisesDataBaseAccess.getInstance(getApplicationContext());

                dataBaseAccess.open();
                dataBaseAccess.addExercise(nameString, typeString, "NULL");
                dataBaseAccess.close();

                //If dialog was shown we cant finish this activity because dialog will disappear immediately
                //Declaring intent with result so that we can call onActivityResult function inside ExerciseFragment and refresh database
                if(!isEmpty)
                {
                    Intent intent = new Intent();
                    setResult(1, intent);
                    finish();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

    }

    //Initializing spinner with exercises types
    private void initTypesSpinner()
    {
        //getting all exercise types from database
        dataBaseAccess = ExercisesDataBaseAccess.getInstance(this);
        dataBaseAccess.open();
        exerciseTypes = dataBaseAccess.getExercisesTypesAtoZ();
        dataBaseAccess.close();

        //converting list of ExercisesTypes to String list
        for (ExercisesTypes type: exerciseTypes)
        {
            exerciseTypesString.add(type.getType());
        }

        //Setting adapter for the spinner
        ArrayAdapter<String> typesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, exerciseTypesString);
        typesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typesSpinner.setAdapter(typesAdapter);

        type.setText(null);
    }
}
