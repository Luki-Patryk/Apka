package com.example.ofiicial.EXERCISES;

public class ExercisesTypes
{
    public String type;

    public ExercisesTypes(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "ExercisesTypes{" +
                "type='" + type + '\'' +
                '}';
    }
}
