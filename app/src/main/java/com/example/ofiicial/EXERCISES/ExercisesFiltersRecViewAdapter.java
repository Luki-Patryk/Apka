package com.example.ofiicial.EXERCISES;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ofiicial.R;

import java.util.ArrayList;

public class ExercisesFiltersRecViewAdapter extends RecyclerView.Adapter<ExercisesFiltersRecViewAdapter.TypesViewHolder>
{

    private ArrayList<ExercisesTypes> exercisesTypes = new ArrayList<>();
    public static boolean selectAllStateChanged = false;
    public static ArrayList<String> CheckedTypes = new ArrayList<>();

    public ExercisesFiltersRecViewAdapter()
    {

    }

    @NonNull
    @Override
    public ExercisesFiltersRecViewAdapter.TypesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercises_filters_list, parent, false);
        TypesViewHolder vHolder = new TypesViewHolder(view);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TypesViewHolder holder, int position)
    {
        holder.exercise_type_checkBox.setText(exercisesTypes.get(position).getType());
        holder.exercise_type_checkBox.setChecked(selectAllStateChanged);

        //checking if there is show all box checked and if it should be unchecked if not all other boxes are checked
        holder.exercise_type_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(selectAllStateChanged == true && isChecked == false)
                {
                    ExercisesFilterMenu.show_all_checkBox.setChecked(false);
                }

                //by value of actual checkBox add or remove type from an ArrayList which will be passed to filter function
                if(isChecked)
                {
                    CheckedTypes.add(exercisesTypes.get(position).getType());
                }
                else
                {
                    CheckedTypes.remove(exercisesTypes.get(position).getType());
                }
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return exercisesTypes.size();
    }

    //getting state of showAll button when clicked
    public static void setSelectAll(boolean isChecked)
    {
        selectAllStateChanged = isChecked;
    }

    public void setExercisesTypes(ArrayList<ExercisesTypes> exercisesTypes)
    {
        this.exercisesTypes = exercisesTypes;
        notifyDataSetChanged();
    }

    public class TypesViewHolder extends RecyclerView.ViewHolder
    {

        private CheckBox exercise_type_checkBox;

        public TypesViewHolder(@NonNull View itemView)
        {
            super(itemView);

            exercise_type_checkBox = itemView.findViewById(R.id.exercise_type_checkBox);
        }
    }
}
