package com.dr.database;

import com.dr.objects.dao.JobDAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "DR.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseManager(Context context)
    {
	super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
	db.execSQL(JobDAO.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
	// TODO Auto-generated method stub
    }

}
