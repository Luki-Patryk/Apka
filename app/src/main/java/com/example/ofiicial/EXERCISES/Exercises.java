package com.example.ofiicial.EXERCISES;

import java.util.Comparator;

public class Exercises {
    private String exercise_type;
    private String exercise_name;
    private String img_URL;

    public Exercises( String exercise_name, String exercise_type, String img_URL) {
        this.exercise_type = exercise_type;
        this.exercise_name = exercise_name;
        this.img_URL = img_URL;
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
}
