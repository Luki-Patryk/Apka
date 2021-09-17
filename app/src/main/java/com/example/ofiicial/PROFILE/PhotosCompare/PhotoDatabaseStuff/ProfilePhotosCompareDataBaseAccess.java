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

    public ArrayList<Photos> addPhoto(Bitmap photo_url, String photo_date, int photo_weight)
    {
        ArrayList<Photos> return_list = getAllPhotos();

        ContentValues values =  new ContentValues();

        Bitmap imageToStore = photo_url;
        objectByteArrayOutputStream = new ByteArrayOutputStream();
        imageToStore.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream);
        imageInByte = objectByteArrayOutputStream.toByteArray();
        values.put("photo_url", imageInByte);

        values.put("photo_date", photo_date);
        values.put("photo_weight", photo_weight);

        profile_photos_compare_database.insert("user_photos", null, values);

        return return_list;
    }

    public ArrayList<Photos> getAllPhotos()
    {
        ArrayList<Photos> returnList = new ArrayList<>();

        String queryString = "SELECT USER_PHOTOS.photo_id, USER_PHOTOS.photo_url, USER_PHOTOS.photo_date, USER_PHOTOS.photo_weight" +
                " FROM USER_PHOTOS";

        Cursor cursor =  profile_photos_compare_database.rawQuery(queryString, null);

        if(cursor.moveToFirst())
        {
            do
            {
                int photo_id = cursor.getInt(0);

                byte [] photo_url_in_byte = cursor.getBlob(1);
                Bitmap photo_url_bitmap  = BitmapFactory.decodeByteArray(photo_url_in_byte,0, photo_url_in_byte.length);

                String photo_date = cursor.getString(2);
                int photo_weight = cursor.getInt(3);

                Photos photo = new Photos(photo_id, photo_url_bitmap, photo_date, photo_weight);
                returnList.add(photo);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return returnList;
    }

    public void deletePhotoById(int id)
    {
        profile_photos_compare_database.delete("USER_PHOTOS", "photo_id == " + id, null);
    }

}
