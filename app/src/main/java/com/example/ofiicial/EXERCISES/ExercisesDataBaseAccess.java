package com.example.ofiicial.EXERCISES;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ofiicial.EXERCISES.Exercises;
import com.example.ofiicial.EXERCISES.ExercisesDataBaseOpenHelper;
import com.example.ofiicial.EXERCISES.ExercisesTypes;
import com.example.ofiicial.Fragment.ExercisesFragment;
import com.example.ofiicial.WORKOUT.ExerciseByWorkout;
import com.example.ofiicial.WORKOUT.Workout;

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
    public static final String IS_EXERCISE_TYPE_ORIGINAL = "EXERCISE_TYPE_TABLE.is_type_original";
    public static final String EXERCISE_TYPE_TABLE = "EXERCISE_TYPE_TABLE";
    public static final String EXERCISE_TYPE_TABLE_ID = "EXERCISE_TYPE_TABLE.id";
    public static final String EXERCISE_TYPE = "EXERCISE_TYPE_TABLE.exercise_type";
    public static final String WORKOUT_LIST_TABLE = "WORKOUT_LIST_TABLE";
    public static final String WORKOUT_LIST_TABLE_EXERCISE_ID = "WORKOUT_LIST_TABLE.exercise_id";
    public static final String WORKOUT_LIST_TABLE_WORKOUT_ID = "WORKOUT_LIST_TABLE.workout_id";
    public static final String WORKOUT_TABLE = "WORKOUT_TABLE";
    public static final String WORKOUT_TABLE_ID = "WORKOUT_TABLE.id";
    public static final String WORKOUT_NAME = "WORKOUT_TABLE.workout_name";
    public static final String WORKOUT_TYPE = "WORKOUT_TABLE.workout_type";
    public static final String WORKOUT_EXERCISES_COUNT = "WORKOUT_TABLE.exercises_count";
    public static final String WORKOUT_IMAGE_URL = "WORKOUT_TABLE.workout_image_URL";
    public static final String IS_WORKOUT_ORIGINAL = "WORKOUT_TABLE.is_workout_original";
    public static final String EXERCISE_SETS = "WORKOUT_LIST_TABLE.exercise_sets";
    public static final String EXERCISE_REPS = "WORKOUT_LIST_TABLE.exercise_reps";

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

                boolean isOriginal = true;

                //Determining if exercise was originally in database or was created by user
                if(cursor.getInt(4) == 0)
                {
                    isOriginal = false;
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
    //TODO: Check if this can be void type
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
            values.put("is_original", 0);

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
        //getting type_id to check if this is original or added by user
        String queryString;
        queryString = "SELECT " + EXERCISE_TYPE_ID + " FROM " + EXERCISES_TABLE + " WHERE " + EXERCISES_TABLE_ID + " = " + ID;

        int type_id = 0;

        Cursor cursor = database.rawQuery(queryString, null);

        if(cursor.moveToFirst())
        {
            type_id = cursor.getInt(0);
        }

        //checking if this exercise is original or not
        queryString = "SELECT " + IS_EXERCISE_TYPE_ORIGINAL + " FROM " + EXERCISE_TYPE_TABLE + " WHERE " + EXERCISE_TYPE_TABLE_ID + " = " + type_id;

        cursor = database.rawQuery(queryString, null);

        if(cursor.moveToFirst())
        {
            int is_original = cursor.getInt(0);

            //if exercise type is added by user check if this is last exercise of this type, and delete this type if that's true
            if(is_original == 0)
            {
                queryString = "SELECT " + EXERCISES_TABLE_ID + " FROM " + EXERCISES_TABLE + " WHERE " + EXERCISE_TYPE_ID + " = " + is_original;

                cursor = database.rawQuery(queryString, null);

                //if this is false, then there is no more exercises with our not original type, so delete it from database
                if(!cursor.moveToFirst())
                {
                    database.delete(EXERCISE_TYPE_TABLE, "id == " + type_id, null);

                }
            }
        }

        cursor.close();

        database.delete(EXERCISES_TABLE, "id == " + ID, null);
    }

    //Getting all workouts that was originally in application in A to Z order
    public ArrayList<Workout> getSuggestedWorkoutsAtoZ()
    {
        ArrayList<Workout> returnList = new ArrayList<>();

        String queryString = "SELECT * \n" +
                "FROM " + WORKOUT_TABLE + "\n" +
                "WHERE " + IS_WORKOUT_ORIGINAL + " = 1 \n" +
                "ORDER BY " + WORKOUT_TABLE_ID + " ASC";

        Cursor cursor = database.rawQuery(queryString, null);

        //if database isn't empty proceed
        if(cursor.moveToFirst())
        {
            //loop through all elements of database that adds them to returnList
            do
            {
                int ID = cursor.getInt(0);
                String workout_name = cursor.getString(1);
                int estimated_workout_time = cursor.getInt(2);
                int exercises_count = cursor.getInt(3);
                String workout_image_URL = cursor.getString(4);

                boolean isOriginal = true;

                //Determining if exercise was originally in database or was created by user
                if(cursor.getInt(5) == 0)
                {
                    isOriginal = false;
                }

                Workout workout = new Workout(ID, workout_name, estimated_workout_time, exercises_count, workout_image_URL, isOriginal);
                returnList.add(workout);

            }while (cursor.moveToNext());
        }

        cursor.close();
        return returnList;
    }

    //Getting all workouts created by user in A to Z order
    public ArrayList<Workout> getUserCreatedWorkoutsAtoZ()
    {
        ArrayList<Workout> returnList = new ArrayList<>();

        String queryString = "SELECT * \n" +
                "FROM " + WORKOUT_TABLE + "\n" +
                "WHERE " + IS_WORKOUT_ORIGINAL + " = 0 \n" +
                "ORDER BY " + WORKOUT_TABLE_ID + " ASC";

        Cursor cursor = database.rawQuery(queryString, null);

        //if database isn't empty proceed
        if(cursor.moveToFirst())
        {
            //loop through all elements of database that adds them to returnList
            do
            {
                int ID = cursor.getInt(0);
                String workout_name = cursor.getString(1);
                int estimated_workout_time = cursor.getInt(2);
                int exercises_count = cursor.getInt(3);
                String workout_image_URL = cursor.getString(4);

                boolean isOriginal = true;

                //Determining if exercise was originally in database or was created by user
                if(cursor.getInt(5) == 0)
                {
                    isOriginal = false;
                }

                Workout workout = new Workout(ID, workout_name, estimated_workout_time, exercises_count, workout_image_URL, isOriginal);
                returnList.add(workout);

            }while (cursor.moveToNext());
        }

        cursor.close();
        return returnList;
    }

    //Getting workout by pased id
    public Workout getWorkoutById(int id)
    {
        Workout workout = new Workout(0, null, 0, 0, null, false);

        String queryString = "SELECT * \n" +
                "FROM " + WORKOUT_TABLE + "\n" +
                "WHERE " + WORKOUT_TABLE_ID + " = " + id;

        Cursor cursor = database.rawQuery(queryString, null);

        if(cursor.moveToFirst())
        {
            int ID = cursor.getInt(0);
            String workout_name = cursor.getString(1);
            int estimated_workout_time = cursor.getInt(2);
            int exercises_count = cursor.getInt(3);
            String workout_image_URL = cursor.getString(4);

            boolean isOriginal = true;

            //Determining if exercise was originally in database or was created by user
            if(cursor.getInt(5) == 0)
            {
                isOriginal = false;
            }

            workout = new Workout(ID, workout_name, estimated_workout_time, exercises_count, workout_image_URL, isOriginal);
        }

        return workout;
    }

    //Getting all exercises by passed workoutID in A to Z order
    public ArrayList<ExerciseByWorkout> getExercisesByWorkoutAtoZ(int workoutID)
    {
        ArrayList<ExerciseByWorkout> returnList = new ArrayList<>();

        String queryString =
        "SELECT " + "\n" +
                EXERCISES_TABLE_ID + ",\n" +
                EXERCISE_NAME + ",\n" +
                EXERCISE_TYPE + ",\n" +
                EXERCISE_IMAGE_URL + ",\n" +
                IS_EXERCISE_ORIGINAL + ",\n" +
                EXERCISE_SETS + ",\n" +
                EXERCISE_REPS + "\n" +
        "FROM " + "\n" +
                WORKOUT_LIST_TABLE + "\n" +
        "INNER JOIN " + "\n" +
                EXERCISES_TABLE + "\n" +
        "ON " + "\n" +
                EXERCISES_TABLE_ID + " = " + WORKOUT_LIST_TABLE_EXERCISE_ID + "\n" +
        "INNER JOIN " + "\n" +
                EXERCISE_TYPE_TABLE + "\n" +
        "ON " + "\n" +
                EXERCISE_TYPE_TABLE_ID + " = " + EXERCISE_TYPE_ID + "\n" +
        "WHERE " + "\n" +
                WORKOUT_LIST_TABLE_WORKOUT_ID + " = " + String.valueOf(workoutID) + "\n" +
        "ORDER BY " + "\n" +
                EXERCISE_TYPE_TABLE_ID + "\n" +
        "ASC";

        Cursor cursor = database.rawQuery(queryString, null);

        if (cursor.moveToFirst())
        {
            do
            {
                int ID = cursor.getInt(0);
                String name = cursor.getString(1);
                String type = cursor.getString(2);
                String image_URL = cursor.getString(3);
                boolean is_original = true;
                if(cursor.getInt(4) == 0)
                {
                    is_original = false;
                }
                int sets = cursor.getInt(5);
                int reps = cursor.getInt(6);

                ExerciseByWorkout exerciseByWorkout = new ExerciseByWorkout(ID, name, type, image_URL, is_original, sets, reps);
                returnList.add(exerciseByWorkout);

            }while (cursor.moveToNext());
        }

        return returnList;
    }

    //delete exercise from workout by passed exercise id and workout id
    public void deleteExerciseByWorkout(int exerciseID, int workoutID)
    {
        int exercises_count = 0;

        database.delete(WORKOUT_LIST_TABLE,  "exercise_id = " + exerciseID + " AND workout_id = " + workoutID, null);

        String queryString = "SELECT " + WORKOUT_EXERCISES_COUNT +
                " FROM " + WORKOUT_TABLE +
                " WHERE " + WORKOUT_TABLE_ID + " = 1";

        Cursor cursor = database.rawQuery(queryString, null);

        if(cursor.moveToFirst())
        {
            exercises_count = cursor.getInt(0);
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put("exercises_count", exercises_count-1);
        database.update(WORKOUT_TABLE, contentValues, "id = " + workoutID, null);

        cursor.close();
    }

    public int updateWorkoutExercisesCount (int workoutID)
    {
        int exercises_count = 0;

        String queryString =
                "SELECT * " +
                "FROM " + WORKOUT_LIST_TABLE + " " +
                "WHERE " + WORKOUT_LIST_TABLE_WORKOUT_ID + " = " + workoutID;

        Cursor cursor = database.rawQuery(queryString, null);

        //Looping through all results of query to count all exercises in particular workout
        if(cursor.moveToFirst())
        {
            exercises_count++;
            while (cursor.moveToNext())
            {
                exercises_count++;
            }
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put("exercises_count", exercises_count);
        database.update(WORKOUT_TABLE, contentValues, "id = " + workoutID, null);

        cursor.close();

        return exercises_count;
    }
}
