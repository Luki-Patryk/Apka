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

    @Override
    public ExercisesListRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercises_list, parent, false);
        ViewHolder vHolder = new ViewHolder(view);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
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


        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exerciseName);
            exerciseImg = itemView.findViewById(R.id.exerciseImg);
            exerciseType = itemView.findViewById(R.id.exerciseType);

            //Clicker taking us to details activity
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(view.getContext(), ExercisesDetailsActivity.class);
                    intent.putExtra("EX_NAME", exercises.get(getAdapterPosition()).getExercise_name());
//                    intent.putExtra("EX_TYPE", exercises.get(getAdapterPosition()).getExercise_type());
                    view.getContext().startActivity(intent);

                }
            });




        }
    }
}
