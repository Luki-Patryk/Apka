package com.example.ofiicial.EXERCISES;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.ofiicial.Fragment.ExercisesFragment;
import com.example.ofiicial.R;

import java.util.ArrayList;

public class ExercisesFilterMenu extends AppCompatActivity
{

    private RadioButton A_Z_radioBtn;
    private ExercisesFiltersRecViewAdapter adapter;
    private RecyclerView exercises_types_RecView;
    private ArrayList<ExercisesTypes> exercises_types;
    private com.example.ofiicial.EXERCISES.ExercisesDataBaseAccess dataBaseAccess;
    private Button filterBtn;
    private RadioGroup type_filter;
    private ExercisesFragment.SortFilter exercises_filter_sorter;
    public static CheckBox show_all_checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_filter_menu);

        //Initializing all objects
        init();



        A_Z_radioBtn.setChecked(true);

        exercises_types_RecView.setAdapter(adapter);
        exercises_types_RecView.setLayoutManager(new LinearLayoutManager(this));

        //get all exercise types from database and display it in A to Z order
        dataBaseAccess = ExercisesDataBaseAccess.getInstance(getApplicationContext());
        dataBaseAccess.open();
        exercises_types = dataBaseAccess.getExercisesTypesAtoZ();
        dataBaseAccess.close();
        adapter.setExercisesTypes(exercises_types);

        //filter exercises on click
        filterBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                //ExercisesFragment.SortFilter sorter;

                //setting sorter numer
                switch (type_filter.getCheckedRadioButtonId())
                {
                    case R.id.filter_sorter_name_A_Z:
                        exercises_filter_sorter = ExercisesFragment.SortFilter.NAME_A_Z;
                        break;
                    case R.id.filter_sorter_name_Z_A:
                        exercises_filter_sorter = ExercisesFragment.SortFilter.NAME_Z_A;
                        break;
                    case R.id.filter_sorter_type_A_Z:
                        exercises_filter_sorter = ExercisesFragment.SortFilter.TYPE_A_Z;
                        break;
                    case R.id.filter_sorter_type_Z_A:
                        exercises_filter_sorter = ExercisesFragment.SortFilter.TYPE_Z_A;
                        break;
                    default:
                        exercises_filter_sorter = ExercisesFragment.SortFilter.NAME_A_Z;
                        break;
                }

                Intent intent = new Intent();
                intent.putExtra("EXERCISES_SORTER_BACK", exercises_filter_sorter);
                //intent.putExtra("");
                setResult(2, intent);
                finish();
            }
        });


        //There is onClick instead of onChange listener, because in case of onChange, there was a problem when any other box was unchecked all other boxes were unchecked too
        show_all_checkBox.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ExercisesFiltersRecViewAdapter.setSelectAll(show_all_checkBox.isChecked());
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void init()
    {
        exercises_types_RecView = findViewById(R.id.exercises_types_RecView);
        A_Z_radioBtn = findViewById(R.id.filter_sorter_name_A_Z);
        filterBtn = findViewById(R.id.filter_exercises_btn);
        type_filter = findViewById(R.id.exercises_filter_sort_radioGroup);
        show_all_checkBox = findViewById(R.id.show_all_checkbox);

        //getting extra from intent as a serializable data
        Intent intent_extra = getIntent();
        exercises_filter_sorter = (ExercisesFragment.SortFilter) intent_extra.getSerializableExtra("EXERCISES_SORTER");

        adapter = new ExercisesFiltersRecViewAdapter();
    }
}
