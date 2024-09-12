package com.example.android.asynctaskloader.utilities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "movie_reviews";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DATE_TIME = "date_time";
    public static final String COLUMN_SCENARIO = "scenario";
    public static final String COLUMN_REALISATION = "realisation";
    public static final String COLUMN_MUSIC = "music";
    public static final String COLUMN_CRITIQUE = "critique";

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_REVIEW_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT NOT NULL, " +
                COLUMN_DATE_TIME + " TEXT NOT NULL, " +
                COLUMN_SCENARIO + " INTEGER NOT NULL, " +
                COLUMN_REALISATION + " INTEGER NOT NULL, " +
                COLUMN_MUSIC + " INTEGER NOT NULL, " +
                COLUMN_CRITIQUE + " TEXT NOT NULL" +
                ");";
        db.execSQL(SQL_CREATE_MOVIE_REVIEW_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
