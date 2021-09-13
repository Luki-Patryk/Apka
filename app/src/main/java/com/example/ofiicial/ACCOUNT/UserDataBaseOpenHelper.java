package com.example.ofiicial.ACCOUNT;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class UserDataBaseOpenHelper extends SQLiteAssetHelper
{
    public UserDataBaseOpenHelper(Context context)
    {
        super(context,"user_manager.db", null, 1);
    }
}
