package com.example.ofiicial.WORKOUT;

import com.example.ofiicial.EXERCISES.Exercises;

//Class created for representing exercises inside workout details,
//it extends Exercises class and adds more detailed data about exercise based on to which workout it belongs
public class ExerciseByWorkout extends Exercises
{
    private int sets, reps;

    public ExerciseByWorkout(int ID, String exercise_name, String exercise_type, String img_URL, boolean is_original, int sets, int reps)
    {
        super(ID, exercise_name, exercise_type, img_URL, is_original);
        this.reps = reps;
        this.sets = sets;
    }

    public int getSets()
    {
        return sets;
    }

    public void setSets(int sets)
    {
        this.sets = sets;
    }

    public int getReps()
    {
        return reps;
    }

    public void setReps(int reps)
    {
        this.reps = reps;
    }
}
