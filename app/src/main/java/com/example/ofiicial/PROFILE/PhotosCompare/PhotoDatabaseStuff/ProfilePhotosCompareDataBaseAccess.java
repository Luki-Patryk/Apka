package com.example.ofiicial.PROFILE.PhotosCompare.PhotoDatabaseStuff;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.ofiicial.PROFILE.PhotosCompare.Photos;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ProfilePhotosCompareDataBaseAccess
{
    private SQLiteOpenHelper profile_photos_compare_openHelper;
    private SQLiteDatabase profile_photos_compare_database;
    private static ProfilePhotosCompareDataBaseAccess photo_instance;

    private ByteArrayOutputStream objectByteArrayOutputStream;
    private byte[] imageInByte;

    //private constructor to make sure that this object will not be created outside this class
    private ProfilePhotosCompareDataBaseAccess(Context context)
    {
        this.profile_photos_compare_openHelper = new ProfilePhotosCompareDatabaseOpenHelper(context);
    }

    //making this class singleton
    public static ProfilePhotosCompareDataBaseAccess getPhoto_instance(Context context)
    {
        if(photo_instance == null)
        {
            photo_instance = new ProfilePhotosCompareDataBaseAccess(context);
        }
        return  photo_instance;
    }

//    opening database connection
    public void open()
    {
        this.profile_photos_compare_database = profile_photos_compare_openHelper.getWritableDatabase();
    }

    public void close()
    {
        if(photo_instance != null)
        {
            profile_photos_compare_database.close();
        }
    }

    public void addPhoto(Photos photo)
    {
        ContentValues values =  new ContentValues();
        Bitmap imageToStore = photo.getPhoto_url();
        objectByteArrayOutputStream = new ByteArrayOutputStream();
        imageToStore.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream);
        imageInByte = objectByteArrayOutputStream.toByteArray();
        values.put("photo_url", imageInByte);

        values.put("photo_date", photo.getPhoto_date());
        values.put("photo_weight", photo.getPhoto_weight());

        profile_photos_compare_database.insert("user_photos", null, values);
    }

    public ArrayList<Photos> getAllPhotos()
    {
        ArrayList<Photos> returnList = new ArrayList<>();

        String queryString = "SELECT USER_PHOTOS.photo_url, USER_PHOTOS.photo_date, USER_PHOTOS.photo_weight" +
                " FROM USER_PHOTOS";

        Cursor cursor =  profile_photos_compare_database.rawQuery(queryString, null);

        if(cursor.moveToFirst())
        {
            do
            {
                byte [] photo_url_in_byte = cursor.getBlob(0);
                Bitmap photo_url_bitmap  = BitmapFactory.decodeByteArray(photo_url_in_byte,0, photo_url_in_byte.length);

                String photo_date = cursor.getString(1);
                int photo_weight = cursor.getInt(2);

                Photos photo = new Photos(photo_url_bitmap, photo_date, photo_weight);
                returnList.add(photo);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return returnList;
    }

    public void deletePhotoById(int id)
    {
        String queryString = "SELECT USER_PHOTOS.photo_id " +
                " FROM USER_PHOTOS";

        Cursor cursor = profile_photos_compare_database.rawQuery(queryString, null);

        if(cursor.moveToFirst())
        {
            do
            {
                if (cursor.getInt(0) == id)
                {
                    profile_photos_compare_database.execSQL("DELETE FROM" + " USER_PHOTOS" + " WHERE" +
                            " photo_id" + " = " + id +";");
                }
            }while(cursor.moveToNext());
        cursor.close();
        }
    }

}
