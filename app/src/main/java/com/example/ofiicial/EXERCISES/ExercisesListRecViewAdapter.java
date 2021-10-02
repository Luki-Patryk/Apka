package com.example.ofiicial.EXERCISES;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ofiicial.R;

import java.util.ArrayList;

public class ExercisesListRecViewAdapter extends RecyclerView.Adapter<ExercisesListRecViewAdapter.ViewHolder>
{

    private ArrayList<Exercises> exercises = new ArrayList<>();

    public ExercisesListRecViewAdapter()
    {
    }

    @NonNull
    @Override
    public ExercisesListRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercises_list, parent, false);
        ExercisesListRecViewAdapter.ViewHolder vHolder = new ExercisesListRecViewAdapter.ViewHolder(view);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExercisesListRecViewAdapter.ViewHolder holder, int position)
    {
        holder.exerciseName.setText(exercises.get(position).getExercise_name());
        holder.exerciseType.setText(exercises.get(position).getExercise_type());
    }

    @Override
    public int getItemCount()
    {
        return exercises.size();
    }

    public void setExercises(ArrayList<Exercises> exercises)
    {
        this.exercises = exercises;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView exerciseName, exerciseType;
        private ImageView exerciseImg;

        //Exercises pop-up part
        private LinearLayout exercise_layout;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exercise_in_workoutDetails_name);
            exerciseImg = itemView.findViewById(R.id.exercise_in_workoutDetails_image);
            exerciseType = itemView.findViewById(R.id.exercise_in_workoutDetails_type);

            //Exercises pop-up part
            exercise_layout = itemView.findViewById(R.id.exercise_list_layout);

            //Clicker taking us to details activity
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ExercisesDetailsActivity.class);
                    intent.putExtra("EX_NAME", exercises.get(getAdapterPosition()).getExercise_name());
                    intent.putExtra("IS_ORIGINAL", exercises.get(getAdapterPosition()).isIs_original());
                    intent.putExtra("EX_TYPE", exercises.get(getAdapterPosition()).getExercise_type());
                    intent.putExtra("EX_ID", exercises.get(getAdapterPosition()).getID());
                    view.getContext().startActivity(intent);
                }
            });




        }
    }
}
