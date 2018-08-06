package com.example.android.movieapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by badrawy on 06/04/2018.
 */

public class MoviesContentProvider extends ContentProvider {

    private MoviesDatabaseHelper database;
    private static final String AUTHORITY = "com.example.android.movieapp";
    private static final String BASE_PATH = "movies";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int FAVMOVIES = 100;
    private static final int MOVIE_ID = 0;


    static {
        uriMatcher.addURI(AUTHORITY,BASE_PATH, FAVMOVIES);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", MOVIE_ID);

    }


    @Override
    public boolean onCreate() {
        database = new MoviesDatabaseHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {

        int uriType = uriMatcher.match(uri);
        //SQLiteDatabase db = database.getWritableDatabase();
        //Cursor dbCursor ;
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(MoviesDatabaseHelper.TABLE_FAV_MOVIES);

        switch (uriType) {
            case FAVMOVIES:
                break;
            case MOVIE_ID:
                queryBuilder.appendWhere(MoviesDatabaseHelper.COLUMN_ID + "="
                        + uri.getLastPathSegment());
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, strings, s,
                strings1, null, null, s1);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;


    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        int uriType = uriMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case FAVMOVIES:
                id = sqlDB.insert(MoviesDatabaseHelper.TABLE_FAV_MOVIES, null, contentValues);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int uriType = uriMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case FAVMOVIES:
                rowsDeleted = sqlDB.delete(MoviesDatabaseHelper.TABLE_FAV_MOVIES, s,
                        strings);
                break;
            case MOVIE_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(s)) {
                    rowsDeleted = sqlDB.delete(
                            MoviesDatabaseHelper.TABLE_FAV_MOVIES,
                            MoviesDatabaseHelper.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(
                            MoviesDatabaseHelper.TABLE_FAV_MOVIES,
                            MoviesDatabaseHelper.COLUMN_ID + "=" + id
                                    + " and " + s,
                            strings);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
