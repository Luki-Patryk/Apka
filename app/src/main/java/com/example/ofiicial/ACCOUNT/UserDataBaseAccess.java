package com.example.ofiicial.ACCOUNT;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.example.ofiicial.PROFILE.PhotosCompare.Photos;

import java.util.ArrayList;
import java.util.Arrays;

public class UserDataBaseAccess
{
    private SQLiteOpenHelper profile_openHelper;
    private SQLiteDatabase profile_database;
    private static UserDataBaseAccess profile_instance;

    //private constructor to make sure that this object will not be created outside this class
    private UserDataBaseAccess(Context context)
    {
        this.profile_openHelper = new UserDataBaseOpenHelper(context);
    }

    //making this class singleton
    public static UserDataBaseAccess getProfile_instance(Context context)
    {
        if(profile_instance == null)
        {
            profile_instance = new UserDataBaseAccess(context);
        }
        return profile_instance;
    }

    //opening database connection
    public void open()
    {
        this.profile_database = profile_openHelper.getWritableDatabase();
    }

    public void close()
    {
      if(profile_database != null)
      {
          profile_database.close();
      }
    }

    public ArrayList<User> getAllUsers()
    {
        ArrayList<User> returnList = new ArrayList<>();

        String queryString = "SELECT user_id, user_name, user_email, user_password" +
                " FROM USER_PHOTOS";

        Cursor cursor =  profile_database.rawQuery(queryString, null);

        if(cursor.moveToFirst())
        {
            do
            {
                int user_id = cursor.getInt(0);
                String user_name = cursor.getString(1);
                String user_email = cursor.getString(2);
                String user_password = cursor.getString(3);

                User user = new User(user_id, user_name, user_email, user_password);
                returnList.add(user);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return returnList;
    }

    public ArrayList<User> addUser(String user_name, String user_email, String user_password)
    {
        ArrayList<User> return_list = getAllUsers();

        ContentValues values = new ContentValues();
        values.put("user_name", user_name);
        values.put("user_email", user_email);
        values.put("user_password", user_password);

        profile_database.insert("user_table", null, values);

        return  return_list;
    }

    public boolean checkUser(String email)
    {
        boolean is_emails_equals = false;

        String queryString = "SELECT  user_table.user_email\n" +
                "FROM user_table";

        Cursor cursor = profile_database.rawQuery(queryString, null);

        //if database isn't empty proceed
        if(cursor.moveToFirst())
        {
            //loop through all elements of database
            do
            {
                if(cursor.getString(0).equals(email))
                {
                    is_emails_equals = true;
                }
            }while (cursor.moveToNext());
        }

        return is_emails_equals;
    }

    public boolean checkUser(String email, String password)
    {
        boolean is_emails_passwords_equals = false;

        String queryString = "SELECT user_email, user_password" +
                " FROM user_table";

        Cursor cursor = profile_database.rawQuery(queryString, null);

        //if database isn't empty proceed
        if(cursor.moveToFirst())
        {
            //loop through all elements of database
            do
            {
                if(cursor.getString(0).equals(email) && cursor.getString(1).equals(password))
                {
                    is_emails_passwords_equals = true;
                }
            }while (cursor.moveToNext());
        }

        return is_emails_passwords_equals;
    }

    public boolean checkUsername(String username)
    {
        boolean is_username_took = false;

        String queryString = "SELECT user_name" +
                " FROM user_table";

        Cursor cursor = profile_database.rawQuery(queryString, null);

        if(cursor.moveToFirst())
        {
            do
            {
                if(cursor.getString(0).equals(username));
                {
                    is_username_took = true;
                }
            }while (cursor.moveToNext());
        }
        return is_username_took;
    }

    public String getUserNameByUserEmail(String email)
    {
        String name = "";

        String queryString = "SELECT user_email, user_name" +
                " FROM user_table";

        Cursor cursor = profile_database.rawQuery(queryString, null);

        if(cursor.moveToFirst())
        {
            do
            {
                if(cursor.getString(0).equals(email))
                {
                    name = cursor.getString(1);
                }
            }while(cursor.moveToNext());
        }
        return name;
    }

    public int getUserIdByUserEmail(String email)
    {
        int id = -1;

        String queryString = "SELECT user_id, user_email" +
                " FROM user_table";

        Cursor cursor = profile_database.rawQuery(queryString, null);

        if(cursor.moveToFirst())
        {
            do
            {
                if(cursor.getString(1).equals(email))
                {
                    id = cursor.getInt(0);
                }
            }while(cursor.moveToNext());
        }

        try
        {
            return id;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    public String getUserPictureByUserEmail(String email)
    {
        String img_url = "";

        String queryString = "SELECT user_email, user_img_url" +
                " FROM user_table";

        Cursor cursor = profile_database.rawQuery(queryString, null);

        if(cursor.moveToFirst())
        {
            do
            {
                if(cursor.getString(0).equals(email))
                {
                    img_url = cursor.getString(1);
                }
            }while(cursor.moveToNext());
        }
        return img_url;
    }

    public String[] getFastSettingsStartDataByProfileName(String profile_name)
    {
        String fs_gender = "";
        String fs_location = "";

        String queryString = "SELECT user_name, user_gender, user_location" +
                " FROM user_table";

        Cursor cursor = profile_database.rawQuery(queryString, null);

        if(cursor.moveToFirst())
        {
            do
            {
                if(cursor.getString(0).equals(profile_name))
                {
                    fs_gender = cursor.getString(1);
                    fs_location = cursor.getString(2);
                }
            }while(cursor.moveToNext());
        }
        return new String[]{fs_gender, fs_location};
    }

//    public void changeUserDataFS(byte[] imageInByte, String profile_name, String profile_gender, String profile_location)
//    {
//        String queryString =  "SELECT user_name, user_gender, user_location, user_img_url" +
//                " FROM user_table";
//        Cursor cursor = profile_database.rawQuery(queryString, null);
//
//        if(cursor.moveToFirst())
//        {
//            do
//            {
//                if(cursor.getString(1).equals(profile_name))
//                {
//                    profile_database.execSQL("UPDATE user_table " +
//                            "SET user_name = " + profile_name +
//                            ", user_gender = " + profile_gender +
//                            ", user_location = " + profile_location +
//                            ", user_img_url = " + imageInByte +
//                            " WHERE user_name = " + profile_name + ";");
//                }
//            }while (cursor.moveToNext());
//        }
//    }

    public boolean changeUserDataFS_test(int user_id, byte[] imageInByte, String profile_name, String profile_gender, String profile_location)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_img_url", imageInByte);
        contentValues.put("user_name", profile_name);
        contentValues.put("user_gender", profile_gender);
        contentValues.put("user_location", profile_location);

        profile_database.update("user_table", contentValues, "user_id = ?", new String [] {String.valueOf(user_id)});
        return  true;
    }

}
