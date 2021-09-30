package com.example.ofiicial.WORKOUT;

public class Workout
{
    int ID;
    String workout_name;
    int estimated_workout_time;
    int exercises_count;
    String workout_image_URL;
    boolean isOriginal;

    public Workout(int ID, String workout_name, int estimated_workout_time, int exercises_count, String workout_image_URL, boolean isOriginal)
    {
        this.ID = ID;
        this.workout_name = workout_name;
        this.estimated_workout_time = estimated_workout_time;
        this.exercises_count = exercises_count;
        this.workout_image_URL = workout_image_URL;
        this.isOriginal = isOriginal;
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

    public int getEstimated_workout_time()
    {
        return estimated_workout_time;
    }

    public void setEstimated_workout_time(int estimated_workout_time)
    {
        this.estimated_workout_time = estimated_workout_time;
    }

    public int getExercises_count()
    {
        return exercises_count;
    }

    public void setExercises_count(int exercises_count)
    {
        this.exercises_count = exercises_count;
    }

    public String getWorkout_image_URL()
    {
        return workout_image_URL;
    }

    public void setWorkout_image_URL(String workout_image_URL)
    {
        this.workout_image_URL = workout_image_URL;
    }

    public int getID()
    {
        return ID;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }
}
