package com.example.ofiicial.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ofiicial.EXERCISES.ExerciseAdd;
import com.example.ofiicial.EXERCISES.Exercises;
import com.example.ofiicial.EXERCISES.ExercisesDataBaseAccess;
import com.example.ofiicial.EXERCISES.ExercisesFilterMenu;
import com.example.ofiicial.EXERCISES.ExercisesListRecViewAdapter;
import com.example.ofiicial.R;

import java.util.ArrayList;


public class ExercisesFragment extends Fragment
{
//    RelativeLayout exercise_template;

    private Context mContext;

    private ArrayList<Exercises> exercises;

    private ExercisesDataBaseAccess dataBaseAccess;

    public ExercisesFragment(Context mContext)
    {
        this.mContext = mContext;
    }

    private RecyclerView exercisesRecView;
    private ExercisesListRecViewAdapter adapter;
    private EditText search;
    private Button addExerciseBtn, filterExercisesBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_exercises, container, false);
        initRecView(view);

        adapter = new ExercisesListRecViewAdapter();
        exercisesRecView = view.findViewById(R.id.exercisesListRecView);
        exercisesRecView.setAdapter(adapter);
        exercisesRecView.setLayoutManager(new LinearLayoutManager(mContext));
        search = view.findViewById(R.id.search_exercises);
        addExerciseBtn = view.findViewById(R.id.btn_add_exercise);
        filterExercisesBtn = view.findViewById(R.id.btn_filter_exercises);

        //create instance of databaseAccess class, open database, copy it to array and close database
        dataBaseAccess = ExercisesDataBaseAccess.getInstance(mContext);

        dataBaseAccess.open();
        exercises = dataBaseAccess.getExercisesAtoZ();
        dataBaseAccess.close();

        adapter.setExercises(exercises);


        //Called when text changes in search
        search.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                dataBaseAccess.open();
                //setting exercises on filtered list with s passed which is actual text converted to string
                exercises = dataBaseAccess.filterExercises(s.toString());
                dataBaseAccess.close();
                adapter.setExercises(exercises);
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        //Adding new exercise
        addExerciseBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //function to open Add Activity
                openAddActivity();
            }
        });


        //Filtering the exercises shown
        filterExercisesBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(mContext, ExercisesFilterMenu.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }


    /*@Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.sort_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_aToz:
                dataBaseAccess = ExercisesDataBaseAccess.getInstance(mContext);

                dataBaseAccess.open();
                exercises = dataBaseAccess.getExercisesAtoZ();
                dataBaseAccess.close();

                adapter.setExercises(exercises);
                adapter.notifyDataSetChanged();
                return true;
            case R.id.menu_zToa:
                dataBaseAccess = ExercisesDataBaseAccess.getInstance(mContext);

                dataBaseAccess.open();
                exercises = dataBaseAccess.getExercisesZtoA();
                dataBaseAccess.close();

                adapter.setExercises(exercises);
                adapter.notifyDataSetChanged();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    private void initRecView(View view)
    {
    }

    private void openAddActivity()
    {
        Intent intent = new Intent(mContext, ExerciseAdd.class);
        //With this function we can track when activity ends and then call the onActivityResult function for refresh our Data
        startActivityForResult(intent, 1);
    }

    //if the result code is correct, then refresh our exercises array
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        dataBaseAccess.open();
        exercises = dataBaseAccess.getAllExercises();
        dataBaseAccess.close();
        adapter.setExercises(exercises);
        adapter.notifyDataSetChanged();
    }
}
