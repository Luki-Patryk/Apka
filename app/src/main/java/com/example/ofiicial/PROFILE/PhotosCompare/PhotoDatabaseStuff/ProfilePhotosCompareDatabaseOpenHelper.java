package com.example.ofiicial.PROFILE.PhotosCompare.PhotoDatabaseStuff;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class ProfilePhotosCompareDatabaseOpenHelper extends SQLiteAssetHelper
{
    public ProfilePhotosCompareDatabaseOpenHelper(Context context)
    {
        super(context, "user_photos.db", null, 1);
    }
}
