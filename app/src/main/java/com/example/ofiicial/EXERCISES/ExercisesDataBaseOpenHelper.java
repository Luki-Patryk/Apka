package com.example.ofiicial.EXERCISES;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class ExercisesDataBaseOpenHelper extends SQLiteAssetHelper
{
    //constructor that passes name of our database and version which is 1 for the moment
    public ExercisesDataBaseOpenHelper(Context context)
    {
        super(context, "exercises_db.db", null, 1);
    }
}