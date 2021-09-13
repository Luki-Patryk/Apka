package com.example.ofiicial.EXERCISES;

import android.widget.Toast;

import java.util.Comparator;

public class Exercises {
    private String exercise_type;
    private String exercise_name;
    private String img_URL;
    private boolean is_original;
    private int ID;

    public Exercises(int ID, String exercise_name, String exercise_type, String img_URL, boolean is_original) {
        this.exercise_type = exercise_type;
        this.exercise_name = exercise_name;
        this.img_URL = img_URL;
        this.is_original = is_original;
        this.ID = ID;
    }

    public String getExercise_type() {
        return exercise_type;
    }

    public void setExercise_type(String exercise_type) {
        this.exercise_type = exercise_type;
    }

    public String getExercise_name() {
        return exercise_name;
    }

    public void setExercise_name(String exercise_name) {
        this.exercise_name = exercise_name;
    }

    public String getImg_URL() {
        return img_URL;
    }

    public void setImg_URL(String img_URL) {
        this.img_URL = img_URL;
    }

    public boolean isIs_original()
    {
        return is_original;
    }

    public void setIs_original(boolean is_original)
    {
        this.is_original = is_original;
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
