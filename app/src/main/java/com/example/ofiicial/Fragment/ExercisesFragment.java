package com.example.ofiicial.Fragment;

import android.app.Service;
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
import android.widget.Toast;

import com.example.ofiicial.EXERCISES.ExerciseAdd;
import com.example.ofiicial.EXERCISES.Exercises;
import com.example.ofiicial.EXERCISES.ExercisesDataBaseAccess;
import com.example.ofiicial.EXERCISES.ExercisesFilterMenu;
import com.example.ofiicial.EXERCISES.ExercisesFiltersRecViewAdapter;
import com.example.ofiicial.EXERCISES.ExercisesListRecViewAdapter;
import com.example.ofiicial.R;

import java.util.ArrayList;


public class ExercisesFragment extends Fragment
{
//    RelativeLayout exercise_template;

    private Context mContext;
    private ArrayList<Exercises> exercises;
    private com.example.ofiicial.EXERCISES.ExercisesDataBaseAccess dataBaseAccess;
    private RecyclerView exercisesRecView;
    private ExercisesListRecViewAdapter adapter;
    private EditText search;
    private Button addExerciseBtn, filterExercisesBtn;

    //Enum type for state of sorting when filter Activity is called
    public enum SortFilter
    {
        TYPE_A_Z, TYPE_Z_A, NAME_A_Z, NAME_Z_A;
    }

    private static SortFilter exercises_filter_sorter;

    public ExercisesFragment(Context mContext)
    {
        this.mContext = mContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_exercises, container, false);
        initRecView(view);

        adapter = new ExercisesListRecViewAdapter();

        exercisesRecView = view.findViewById(R.id.exercisesListRecView);
        search = view.findViewById(R.id.search_exercises);
        addExerciseBtn = view.findViewById(R.id.btn_add_exercise);
        filterExercisesBtn = view.findViewById(R.id.btn_filter_exercises);

        exercisesRecView.setAdapter(adapter);
        exercisesRecView.setLayoutManager(new LinearLayoutManager(mContext));

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
                exercises = dataBaseAccess.filterExercisesInSearchField(s.toString());
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
                openFilterActivity();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void initRecView(View view)
    {
    }


    private void openAddActivity()
    {
        Intent intent = new Intent(mContext, ExerciseAdd.class);
        //With this function we can track when activity ends and then call the onActivityResult function for refresh our Data
        startActivityForResult(intent, 1);
    }

    private void openFilterActivity()
    {
        //before going into filter activity clear CheckedTypes array, it caused problems with multiplying types and having more than just checked types in it, now it seems to work properly
        ExercisesFiltersRecViewAdapter.CheckedTypes.clear();
        Intent intent = new Intent(mContext, ExercisesFilterMenu.class);
        intent.putExtra("EXERCISES_SORTER", exercises_filter_sorter);
        startActivityForResult(intent, 0);
    }

    //if the result code is correct, then refresh our exercises array
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode)
        {
            //In this case, previous activity was adding exercise so we have to just get all exercises from database
            case 1:
                dataBaseAccess.open();
                exercises = dataBaseAccess.getAllExercises();
                dataBaseAccess.close();
                adapter.setExercises(exercises);
                adapter.notifyDataSetChanged();
                break;
            //In this case, previous activity was filtering exercises, so we have to get types to show from RecyclerView Adapter, and determinate how to sort our array
            case 2:
                Toast.makeText(mContext, ExercisesFiltersRecViewAdapter.CheckedTypes.toString(), Toast.LENGTH_SHORT).show();

                //setting sorter with get extra from previous activity
                exercises_filter_sorter = (ExercisesFragment.SortFilter) data.getSerializableExtra("EXERCISES_SORTER_BACK");

                dataBaseAccess.open();
                exercises = dataBaseAccess.filterExercisesByAttributes(ExercisesFiltersRecViewAdapter.CheckedTypes, exercises_filter_sorter);
                dataBaseAccess.close();
                adapter.setExercises(exercises);
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }

    }
}
