package com.example.android.movieapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by badrawy on 06/04/2018.
 */

public class MoviesDatabaseHelper extends SQLiteOpenHelper {


    public static final String TABLE_FAV_MOVIES = "favMovies";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ORG_TITLE = "original_title";
    public static final String COLUMN_OVERVIEW = "overview";
    public static final String COLUMN_POSTER_PATH = "poster_path";
    public static final String COLUMN_RELEASE_DATE = "release_date";
    public static final String COLUMN_VOTE_AVERAGE = "vote_average";



    private static final String DATABASE_NAME = "favMovies.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_FAV_MOVIES
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_ORG_TITLE + " text not null, "
            + COLUMN_OVERVIEW + " text not null,"
            + COLUMN_POSTER_PATH + " text not null,"
            + COLUMN_RELEASE_DATE + " text not null,"
            + COLUMN_VOTE_AVERAGE + " double"
            + ");";



    public MoviesDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FAV_MOVIES);
        onCreate(sqLiteDatabase);

    }
}
