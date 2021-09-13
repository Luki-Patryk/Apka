package com.example.ofiicial.ACCOUNT;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    public void addUser(User user)
    {
        ContentValues values = new ContentValues();
        values.put("user_name", user.getNick_name());
        values.put("user_email", user.getEmail());
        values.put("user_password", user.getPassword());

        profile_database.insert("user_table", null, values);
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

    public void changeUserDataFS(byte[] imageInByte, String profile_name, String profile_gender, String profile_location)
    {
        String queryString =  "SELECT user_name, user_gender, user_location" +
                " FROM user_table";
        Cursor cursor = profile_database.rawQuery(queryString, null);

        if(cursor.moveToFirst())
        {
            do
            {
                if(cursor.getString(1).equals(profile_name))
                {
                    profile_database.execSQL("UPDATE user_table " +
                            "SET user_img_url " + imageInByte +
                            ", user_name = " + profile_name +
                            ", user_gender = " + profile_gender +
                            ", user_location = " + profile_location +
                            " WHERE user_name = " + profile_name + ";");
                }
            }while (cursor.moveToNext());
        }
    }

}
