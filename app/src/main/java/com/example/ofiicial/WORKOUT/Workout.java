package com.example.ofiicial.WORKOUT;

public class Workout
{
    String workout_name;
    boolean isOriginal;
    String workout_type;
    int exercises_count;

    public Workout(String workout_name, boolean isOriginal, String workout_type, int exercises_count)
    {
        this.workout_name = workout_name;
        this.isOriginal = isOriginal;
        this.workout_type = workout_type;
        this.exercises_count = exercises_count;
    }

    public String getWorkout_name()
    {
        return workout_name;
    }

    public void setWorkout_name(String workout_name)
    {
        this.workout_name = workout_name;
    }

    public boolean isOriginal()
    {
        return isOriginal;
    }

    public void setOriginal(boolean original)
    {
        isOriginal = original;
    }

    public String getWorkout_type()
    {
        return workout_type;
    }

    public void setWorkout_type(String workout_type)
    {
        this.workout_type = workout_type;
    }

    public int getExercises_count()
    {
        return exercises_count;
    }

    public void setExercises_count(int exercises_count)
    {
        this.exercises_count = exercises_count;
    }
}
