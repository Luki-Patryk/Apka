package com.example.ofiicial.EXERCISES;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.ofiicial.EXERCISES.Exercises;
import com.example.ofiicial.EXERCISES.ExercisesDataBaseOpenHelper;
import com.example.ofiicial.EXERCISES.ExercisesTypes;
import com.example.ofiicial.Fragment.ExercisesFragment;

import java.util.ArrayList;

public class ExercisesDataBaseAccess
{
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static ExercisesDataBaseAccess instance;

    public static final String EXERCISES_TABLE = "EXERCISES_TABLE";
    public static final String EXERCISES_TABLE_ID = "EXERCISES_TABLE.id";
    public static final String EXERCISE_NAME = "EXERCISES_TABLE.exercise_name";
    public static final String EXERCISE_TYPE_ID = "EXERCISES_TABLE.exercise_type_id";
    public static final String EXERCISE_IMAGE_URL = "EXERCISES_TABLE.exercise_image_url";
    public static final String IS_EXERCISE_ORIGINAL = "EXERCISES_TABLE.is_exercise_original";
    public static final String EXERCISE_TYPE_TABLE = "EXERCISE_TYPE_TABLE";
    public static final String EXERCISE_TYPE_TABLE_ID = "EXERCISE_TYPE_TABLE.id";
    public static final String EXERCISE_TYPE = "EXERCISE_TYPE_TABLE.exercise_type";

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
        /*String queryString = "SELECT EXERCISES_TABLE.EXERCISE_NAME, EXERCISE_TYPE_TABLE.EXERCISE_TYPE, EXERCISES_TABLE.EXERCISE_IMAGE_URL\n" +
                "FROM EXERCISES_TABLE\n" +
                "INNER JOIN EXERCISE_TYPE_TABLE\n" +
                "ON EXERCISES_TABLE.TYPE_ID = EXERCISE_TYPE_TABLE.ID\n";*/

        String queryString = "SELECT " + EXERCISES_TABLE_ID + ", " + EXERCISE_NAME + ", " + EXERCISE_TYPE + ", " + EXERCISE_IMAGE_URL + ", " + IS_EXERCISE_ORIGINAL + "\n" +
                "FROM " + EXERCISES_TABLE + "\n" +
                "INNER JOIN " + EXERCISE_TYPE_TABLE + "\n" +
                "ON " + EXERCISE_TYPE_ID + " = " + EXERCISE_TYPE_TABLE_ID;

        Cursor cursor = database.rawQuery(queryString, null);


        //if database isn't empty proceed
        if(cursor.moveToFirst())
        {
            //loop through all elements of database that adds them to returnList
            do
            {
                int ID = cursor.getInt(0);
                String exerciseName = cursor.getString(1);
                String exerciseType = cursor.getString(2);
                String exerciseImageURL = cursor.getString(3);
                int isExerciseOriginal = cursor.getInt(4);

                boolean isOriginal;

                //Determining if exercise was originally in database or was created by user
                switch (isExerciseOriginal)
                {
                    case 0:
                        isOriginal = false;
                        break;
                    case 1:
                        isOriginal = true;
                        break;
                    default:
                        isOriginal = true;
                        break;
                }

                Exercises exercise = new Exercises(ID, exerciseName, exerciseType, exerciseImageURL, isOriginal);
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
        /*String queryString = "SELECT EXERCISES_TABLE.EXERCISE_NAME, EXERCISE_TYPE_TABLE.EXERCISE_TYPE, EXERCISES_TABLE.EXERCISE_IMAGE_URL\n" +
                "FROM EXERCISES_TABLE\n" +
                "INNER JOIN EXERCISE_TYPE_TABLE\n" +
                "ON EXERCISES_TABLE.TYPE_ID = EXERCISE_TYPE_TABLE.ID\n" +
                "ORDER BY EXERCISES_TABLE.EXERCISE_NAME ASC";*/

        String queryString = "SELECT " + EXERCISES_TABLE_ID + ", " + EXERCISE_NAME + ", " + EXERCISE_TYPE + ", " + EXERCISE_IMAGE_URL + ", " + IS_EXERCISE_ORIGINAL + "\n" +
                "FROM " + EXERCISES_TABLE + "\n" +
                "INNER JOIN " + EXERCISE_TYPE_TABLE + "\n" +
                "ON " + EXERCISE_TYPE_ID + " = " + EXERCISE_TYPE_TABLE_ID + "\n" +
                "ORDER BY " + EXERCISE_NAME + " ASC";

        Cursor cursor = database.rawQuery(queryString, null);

        //if database isn't empty proceed
        if(cursor.moveToFirst())
        {
            //loop through all elements of database that adds them to returnList
            do
            {
                int ID = cursor.getInt(0);
                String exerciseName = cursor.getString(1);
                String exerciseType = cursor.getString(2);
                String exerciseImageURL = cursor.getString(3);
                int isExerciseOriginal = cursor.getInt(4);

                boolean isOriginal;

                //Determining if exercise was originally in database or was created by user
                switch (isExerciseOriginal)
                {
                    case 0:
                        isOriginal = false;
                        break;
                    case 1:
                        isOriginal = true;
                        break;
                    default:
                        isOriginal = true;
                        break;
                }

                Exercises exercise = new Exercises(ID, exerciseName, exerciseType, exerciseImageURL, isOriginal);
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
        /*String queryString = "SELECT EXERCISES_TABLE.EXERCISE_NAME, EXERCISE_TYPE_TABLE.EXERCISE_TYPE, EXERCISES_TABLE.EXERCISE_IMAGE_URL\n" +
                "FROM EXERCISES_TABLE\n" +
                "INNER JOIN EXERCISE_TYPE_TABLE\n" +
                "ON EXERCISES_TABLE.TYPE_ID = EXERCISE_TYPE_TABLE.ID\n" +
                "ORDER BY EXERCISES_TABLE.EXERCISE_NAME DESC";*/

        String queryString = "SELECT " + EXERCISES_TABLE_ID + ", " + EXERCISE_NAME + ", " + EXERCISE_TYPE + ", " + EXERCISE_IMAGE_URL + ", " + IS_EXERCISE_ORIGINAL + "\n" +
                "FROM " + EXERCISES_TABLE + "\n" +
                "INNER JOIN " + EXERCISE_TYPE_TABLE + "\n" +
                "ON " + EXERCISE_TYPE_ID + " = " + EXERCISE_TYPE_TABLE_ID + "\n" +
                "ORDER BY " + EXERCISE_NAME + " DESC";

        Cursor cursor = database.rawQuery(queryString, null);

        //if database isn't empty proceed
        if(cursor.moveToFirst())
        {
            //loop through all elements of database that adds them to returnList
            do
            {
                int ID = cursor.getInt(0);
                String exerciseName = cursor.getString(1);
                String exerciseType = cursor.getString(2);
                String exerciseImageURL = cursor.getString(3);
                int isExerciseOriginal = cursor.getInt(4);

                boolean isOriginal;

                //Determining if exercise was originally in database or was created by user
                switch (isExerciseOriginal)
                {
                    case 0:
                        isOriginal = false;
                        break;
                    case 1:
                        isOriginal = true;
                        break;
                    default:
                        isOriginal = true;
                        break;
                }

                Exercises exercise = new Exercises(ID, exerciseName, exerciseType, exerciseImageURL, isOriginal);
                returnList.add(exercise);

            }while (cursor.moveToNext());
        }

        cursor.close();
        return returnList;
    }

    //Getting all exercises based on filter
    public ArrayList<Exercises> filterExercisesInSearchField(String filter)
    {
        ArrayList<Exercises> returnList = new ArrayList<>();

        //SQL query
        /*String queryString = "SELECT EXERCISES_TABLE.EXERCISE_NAME, EXERCISE_TYPE_TABLE.EXERCISE_TYPE, EXERCISES_TABLE.EXERCISE_IMAGE_URL\n" +
                "FROM EXERCISES_TABLE\n" +
                "INNER JOIN EXERCISE_TYPE_TABLE\n" +
                "ON EXERCISES_TABLE.TYPE_ID = EXERCISE_TYPE_TABLE.ID\n" +
                "WHERE EXERCISES_TABLE.EXERCISE_NAME\n" +
                "LIKE " + "\"%" + filter + "%\"" +
                "ORDER BY EXERCISES_TABLE.EXERCISE_NAME ASC";*/

        String queryString = "SELECT " + EXERCISES_TABLE_ID + ", " + EXERCISE_NAME + ", " + EXERCISE_TYPE + ", " + EXERCISE_IMAGE_URL + ", " + IS_EXERCISE_ORIGINAL + "\n" +
                "FROM " + EXERCISES_TABLE + "\n" +
                "INNER JOIN " + EXERCISE_TYPE_TABLE + "\n" +
                "ON " + EXERCISE_TYPE_ID + " = " + EXERCISE_TYPE_TABLE_ID + "\n" +
                "WHERE " + EXERCISE_NAME + "\n" +
                "LIKE " + "\"%" + filter + "%\"" +
                "ORDER BY " + EXERCISE_NAME + " ASC";

        Cursor cursor = database.rawQuery(queryString, null);

        //if database isn't empty proceed
        if(cursor.moveToFirst())
        {
            //loop through all elements of database that adds them to returnList
            do
            {
                int ID = cursor.getInt(0);
                String exerciseName = cursor.getString(1);
                String exerciseType = cursor.getString(2);
                String exerciseImageURL = cursor.getString(3);
                int isExerciseOriginal = cursor.getInt(4);

                boolean isOriginal;

                //Determining if exercise was originally in database or was created by user
                switch (isExerciseOriginal)
                {
                    case 0:
                        isOriginal = false;
                        break;
                    case 1:
                        isOriginal = true;
                        break;
                    default:
                        isOriginal = true;
                        break;
                }

                Exercises exercise = new Exercises(ID, exerciseName, exerciseType, exerciseImageURL, isOriginal);
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
        /*String queryString = "SELECT EXERCISE_TYPE, ID\n" +
                "FROM EXERCISE_TYPE_TABLE\n" +
                "ORDER BY ID ASC";*/

        String queryString = "SELECT " + EXERCISE_TYPE + ", " + EXERCISE_TYPE_TABLE_ID + "\n" +
                "FROM " + EXERCISE_TYPE_TABLE + "\n" +
                "ORDER BY " + EXERCISE_TYPE_TABLE_ID + " ASC";

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
            values.put("exercise_type", type);

            database.insert(EXERCISE_TYPE_TABLE, null, values);

            //next available ID
            typeID++;
        }

        ContentValues values = new ContentValues();
        values.put("exercise_name", name);
        values.put("exercise_type_id", typeID);
        values.put("exercise_image_url", URL);
        values.put("is_exercise_original", 0);

        database.insert(EXERCISES_TABLE, null, values);

        cursor.close();

        return returnList;
    }

    public ArrayList<ExercisesTypes> getExercisesTypesAtoZ()
    {
        ArrayList<ExercisesTypes> returnList = new ArrayList<>();

        //SQL query
        String queryString = "SELECT EXERCISE_TYPE\n" +
                "FROM EXERCISE_TYPE_TABLE\n" +
                "ORDER BY EXERCISE_TYPE ASC";

        Cursor cursor = database.rawQuery(queryString, null);

        //if database isn't empty proceed
        if(cursor.moveToFirst())
        {
            //loop through all elements of database that adds them to returnList
            do
            {
                String type = cursor.getString(0);


                ExercisesTypes exercisesTypes = new ExercisesTypes(type);
                returnList.add(exercisesTypes);

            }while (cursor.moveToNext());
        }

        cursor.close();
        return returnList;
    }

    public ArrayList<Exercises> filterExercisesByAttributes(ArrayList<String> types, ExercisesFragment.SortFilter sorter)
    {
        ArrayList<Exercises> returnList = new ArrayList<>();

        //SQL query
        /*String queryString = "SELECT EXERCISES_TABLE.EXERCISE_NAME, EXERCISE_TYPE_TABLE.EXERCISE_TYPE, EXERCISES_TABLE.EXERCISE_IMAGE_URL\n" +
                "FROM EXERCISES_TABLE\n" +
                "INNER JOIN EXERCISE_TYPE_TABLE\n" +
                "ON EXERCISES_TABLE.TYPE_ID = EXERCISE_TYPE_TABLE.ID\n" +
                "WHERE ";*/

        String queryString = "SELECT " + EXERCISES_TABLE_ID + ", " + EXERCISE_NAME + ", " + EXERCISE_TYPE + ", " + EXERCISE_IMAGE_URL + ", " + IS_EXERCISE_ORIGINAL + "\n" +
                "FROM " + EXERCISES_TABLE + "\n" +
                "INNER JOIN " + EXERCISE_TYPE_TABLE + "\n" +
                "ON " + EXERCISE_TYPE_ID + " = " + EXERCISE_TYPE_TABLE_ID + "\n" +
                "WHERE ";

        //adding every type to queryString
        for (String ex_type: types)
        {
            queryString += EXERCISE_TYPE + " = " + "\"" + ex_type + "\" OR ";
        }

        //Deleting last OR statement from String
        queryString = queryString.substring(0, queryString.length()-3);

        if(types.size() == 0)
        {
            queryString = queryString.substring(0, queryString.length()-3);
        }

        //adding sorter to queryString
        switch (sorter)
        {
            case NAME_A_Z:
                //queryString += "\nORDER BY EXERCISES_TABLE.EXERCISE_NAME ASC";
                queryString += "\nORDER BY " + EXERCISE_NAME + " ASC";
                break;
            case NAME_Z_A:
                //queryString += "\nORDER BY EXERCISES_TABLE.EXERCISE_NAME DESC";
                queryString += "\nORDER BY " + EXERCISE_NAME + " DESC";
                break;
            case TYPE_A_Z:
                //queryString += "\nORDER BY EXERCISE_TYPE_TABLE.EXERCISE_TYPE ASC";
                queryString += "\nORDER BY " + EXERCISE_TYPE + " ASC";
                break;
            case TYPE_Z_A:
                //queryString += "\nORDER BY EXERCISE_TYPE_TABLE.EXERCISE_TYPE DESC";
                queryString += "\nORDER BY " + EXERCISE_TYPE + " DESC";
                break;
            default:
                break;
        }

        Cursor cursor = database.rawQuery(queryString, null);

        //if database isn't empty proceed
        if(cursor.moveToFirst())
        {
            //loop through all elements of database that adds them to returnList
            do
            {
                int ID = cursor.getInt(0);
                String exerciseName = cursor.getString(1);
                String exerciseType = cursor.getString(2);
                String exerciseImageURL = cursor.getString(3);
                int isExerciseOriginal = cursor.getInt(4);

                boolean isOriginal;

                //Determining if exercise was originally in database or was created by user
                switch (isExerciseOriginal)
                {
                    case 0:
                        isOriginal = false;
                        break;
                    case 1:
                        isOriginal = true;
                        break;
                    default:
                        isOriginal = true;
                        break;
                }

                Exercises exercise = new Exercises(ID, exerciseName, exerciseType, exerciseImageURL, isOriginal);
                returnList.add(exercise);

            }while (cursor.moveToNext());
        }

        cursor.close();

        return returnList;
    }

    public void changeExercise(int ID, String new_name, String new_type)
    {

        //This is a proper method to update database
        ContentValues values = new ContentValues();
        values.put("exercise_name", new_name);
        database.update(EXERCISES_TABLE, values, "id == " + ID, null);

        String queryString;

        queryString = "SELECT " + EXERCISE_TYPE_TABLE_ID + ", " + EXERCISE_TYPE + " FROM " + EXERCISE_TYPE_TABLE + " WHERE " + EXERCISE_TYPE + " == " + "\"" + new_type + "\"";

        int type_ID;

        Cursor cursor = database.rawQuery(queryString, null);
        if(cursor.moveToFirst())
        {
            type_ID = cursor.getInt(0);

            ContentValues values1 = new ContentValues();
            values1.put("exercise_type_id", type_ID);
            database.update(EXERCISES_TABLE, values1, "id == " + ID, null);
        }
        else
        {
            //adding new exercise type to database
            ContentValues values1 = new ContentValues();
            values1.put("exercise_type", new_type);
            database.insert(EXERCISE_TYPE_TABLE, null, values1);

            //getting id of recently added exercise type and updating exercise type id in database
            String queryString1 = "SELECT " + EXERCISE_TYPE_TABLE_ID + " FROM " + EXERCISE_TYPE_TABLE + " ORDER BY " + EXERCISE_TYPE_TABLE_ID + " DESC LIMIT 1";
            cursor = database.rawQuery(queryString1, null);
            if(cursor.moveToFirst())
            {
                type_ID = cursor.getInt(0);

                values1.clear();
                values1.put("exercise_type_id", type_ID);
                database.update(EXERCISES_TABLE, values1, "id == " + ID, null);
            }
        }

        cursor.close();
    }

    public void deleteExercise(int ID)
    {
        //TODO: if type of this exercise was created by user and it is the only exercise where its appear then delete the type from database also
        database.delete(EXERCISES_TABLE, "id == " + ID, null);
    }
}
