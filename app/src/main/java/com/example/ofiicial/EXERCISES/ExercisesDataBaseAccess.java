package com.example.ofiicial.EXERCISES;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ExercisesDataBaseAccess
{
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static ExercisesDataBaseAccess instance;

    //private constructor to make sure that this object will not be created outside this class
    private ExercisesDataBaseAccess(Context context)
    {
        this.openHelper = new ExercisesDataBaseOpenHelper(context);
    }

    //making this class singleton
    public static ExercisesDataBaseAccess getInstance(Context context)
    {
        if(instance == null)
        {
            instance = new ExercisesDataBaseAccess(context);
        }
        return instance;
    }

    //opening database connection
    public void open()
    {
        this.database = openHelper.getWritableDatabase();
    }

    public void close()
    {
        if(database != null)
        {
            database.close();
        }
    }

    //Getting all exercises from a database
    public ArrayList<Exercises> getAllExercises()
    {
        ArrayList<Exercises> returnList = new ArrayList<>();

        //SQL query
        String queryString = "SELECT EXERCISES_TABLE.EXERCISE_NAME, EXERCISE_TYPE_TABLE.EXERCISE_TYPE, EXERCISES_TABLE.EXERCISE_IMAGE_URL\n" +
                "FROM EXERCISES_TABLE\n" +
                "INNER JOIN EXERCISE_TYPE_TABLE\n" +
                "ON EXERCISES_TABLE.TYPE_ID = EXERCISE_TYPE_TABLE.ID\n";

        Cursor cursor = database.rawQuery(queryString, null);

        //if database isn't empty proceed
        if(cursor.moveToFirst())
        {
            //loop through all elements of database that adds them to returnList
            do
            {
                String exerciseName = cursor.getString(0);
                String exerciseType = cursor.getString(1);
                String exerciseImageURL = cursor.getString(2);

                Exercises exercise = new Exercises(exerciseName, exerciseType, exerciseImageURL);
                returnList.add(exercise);

            }while (cursor.moveToNext());
        }

        cursor.close();
        return returnList;
    }

    //Getting all exercises in A to Z order
    public ArrayList<Exercises> getExercisesAtoZ()
    {
        ArrayList<Exercises> returnList = new ArrayList<>();

        //SQL query
        String queryString = "SELECT EXERCISES_TABLE.EXERCISE_NAME, EXERCISE_TYPE_TABLE.EXERCISE_TYPE, EXERCISES_TABLE.EXERCISE_IMAGE_URL\n" +
                "FROM EXERCISES_TABLE\n" +
                "INNER JOIN EXERCISE_TYPE_TABLE\n" +
                "ON EXERCISES_TABLE.TYPE_ID = EXERCISE_TYPE_TABLE.ID\n" +
                "ORDER BY EXERCISES_TABLE.EXERCISE_NAME ASC";

        Cursor cursor = database.rawQuery(queryString, null);

        //if database isn't empty proceed
        if(cursor.moveToFirst())
        {
            //loop through all elements of database that adds them to returnList
            do
            {
                String exerciseName = cursor.getString(0);
                String exerciseType = cursor.getString(1);
                //String exerciseImageURL = cursor.getString(3);

                Exercises exercise = new Exercises(exerciseName, exerciseType, "NULL");
                returnList.add(exercise);

            }while (cursor.moveToNext());
        }

        cursor.close();
        return returnList;
    }

    //Getting all exercises in Z to A order
    public ArrayList<Exercises> getExercisesZtoA()
    {
        ArrayList<Exercises> returnList = new ArrayList<>();

        //SQL query
        String queryString = "SELECT EXERCISES_TABLE.EXERCISE_NAME, EXERCISE_TYPE_TABLE.EXERCISE_TYPE, EXERCISES_TABLE.EXERCISE_IMAGE_URL\n" +
                "FROM EXERCISES_TABLE\n" +
                "INNER JOIN EXERCISE_TYPE_TABLE\n" +
                "ON EXERCISES_TABLE.TYPE_ID = EXERCISE_TYPE_TABLE.ID\n" +
                "ORDER BY EXERCISES_TABLE.EXERCISE_NAME DESC";

        Cursor cursor = database.rawQuery(queryString, null);

        //if database isn't empty proceed
        if(cursor.moveToFirst())
        {
            //loop through all elements of database that adds them to returnList
            do
            {
                String exerciseName = cursor.getString(0);
                String exerciseType = cursor.getString(1);
                String exerciseImageURL = cursor.getString(2);

                Exercises exercise = new Exercises(exerciseName, exerciseType, exerciseImageURL);
                returnList.add(exercise);

            }while (cursor.moveToNext());
        }

        cursor.close();
        return returnList;
    }

    //Getting all exercises based on filter
    public ArrayList<Exercises> filterExercises(String filter)
    {
        ArrayList<Exercises> returnList = new ArrayList<>();

        //SQL query
        String queryString = "SELECT EXERCISES_TABLE.EXERCISE_NAME, EXERCISE_TYPE_TABLE.EXERCISE_TYPE, EXERCISES_TABLE.EXERCISE_IMAGE_URL\n" +
                "FROM EXERCISES_TABLE\n" +
                "INNER JOIN EXERCISE_TYPE_TABLE\n" +
                "ON EXERCISES_TABLE.TYPE_ID = EXERCISE_TYPE_TABLE.ID\n" +
                "WHERE EXERCISES_TABLE.EXERCISE_NAME\n" +
                "LIKE " + "\"%" + filter + "%\"" +
                "ORDER BY EXERCISES_TABLE.EXERCISE_NAME ASC";

        Cursor cursor = database.rawQuery(queryString, null);

        //if database isn't empty proceed
        if(cursor.moveToFirst())
        {
            //loop through all elements of database that adds them to returnList
            do
            {
                String exerciseName = cursor.getString(0);
                String exerciseType = cursor.getString(1);
                String exerciseImageURL = cursor.getString(2);

                Exercises exercise = new Exercises(exerciseName, exerciseType, exerciseImageURL);
                returnList.add(exercise);

            }while (cursor.moveToNext());
        }

        cursor.close();
        return returnList;
    }

    //Adding exercise to a database
    public ArrayList<Exercises> addExercise(String name, String type, String URL)
    {
        ArrayList<Exercises> returnList = getAllExercises();

        //SQL query
        String queryString = "SELECT EXERCISE_TYPE, ID\n" +
                "FROM EXERCISE_TYPE_TABLE\n" +
                "ORDER BY ID ASC";

        //get ID of exercise type and check if it is already in database
        int typeID = 0;
        boolean found = false;

        Cursor cursor = database.rawQuery(queryString, null);

        if(cursor.moveToFirst())
        {
            do
            {
                //if user input type is equal to any existing type in database then remember ID of this type,
                //change found value to true so that we know there is no need to add an type do database and break the loop
                if(type.equals(cursor.getString(0)))
                {
                    typeID = cursor.getInt(1);
                    found = true;
                    break;
                }

                //if there is no existing type of exercise in database, this will track and an highest ID in table
                typeID = cursor.getInt(1);
            }while(cursor.moveToNext());
        }


        //If there is no such a type in a database, add one
        if(!found)
        {
            ContentValues values = new ContentValues();
            values.put("EXERCISE_TYPE", type);

            database.insert("EXERCISE_TYPE_TABLE", null, values);

            //next available ID
            typeID++;
        }

        ContentValues values = new ContentValues();
        values.put("EXERCISE_NAME", name);
        values.put("TYPE_ID", typeID);
        values.put("EXERCISE_IMAGE_URL", URL);

        database.insert("EXERCISES_TABLE", null, values);

        cursor.close();

        return returnList;
    }
}
