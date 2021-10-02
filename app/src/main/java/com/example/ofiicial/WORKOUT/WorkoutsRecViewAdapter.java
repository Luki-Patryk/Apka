package com.example.ofiicial.WORKOUT;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ofiicial.R;

import java.util.ArrayList;

public class WorkoutsRecViewAdapter extends RecyclerView.Adapter<WorkoutsRecViewAdapter.ViewHolder>
{
    private ArrayList<Workout> workouts = new ArrayList<>();

    public WorkoutsRecViewAdapter(){};

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workouts_list, parent, false);
        WorkoutsRecViewAdapter.ViewHolder vHolder = new WorkoutsRecViewAdapter.ViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.workout_name.setText(workouts.get(position).getWorkout_name());
        holder.estimated_workout_time.setText("Estimated time: " + String.valueOf(workouts.get(position).getEstimated_workout_time()) + "min");
        holder.exercises_count.setText("Exercises count: " + String.valueOf(workouts.get(position).getExercises_count()));
    }

    @Override
    public int getItemCount()
    {
        return workouts.size();
    }

    public void setWorkouts(ArrayList<Workout> workouts)
    {
        this.workouts = workouts;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView workout_name, estimated_workout_time, exercises_count;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            workout_name = itemView.findViewById(R.id.workout_name);
            estimated_workout_time = itemView.findViewById(R.id.estimated_workout_time);
            exercises_count = itemView.findViewById(R.id.exercises_count_in_workouts_list);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(),WorkoutDetailsActivity.class);
                    intent.putExtra("WORKOUT_ID", workouts.get(getAdapterPosition()).getID());
                    view.getContext().startActivity(intent);
                }
            });

        }
    }
}
