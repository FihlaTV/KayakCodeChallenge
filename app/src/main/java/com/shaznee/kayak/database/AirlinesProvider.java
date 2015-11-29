package com.shaznee.kayak.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

public class AirlinesProvider extends ContentProvider {

    private static final String AUTHORITY = "com.shaznee.kayak.database.airlinesprovider";
    private static final String BASE_PATH = "airlines";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );

    // Constant to identify the requested operation
    private static final int AIRLINES = 1;
    private static final int AIRLINE_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    public static final String CONTENT_ITEM_TYPE = "airline";

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, AIRLINES);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", AIRLINE_ID);
    }

    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        DBOpenHelper dbHelper = new DBOpenHelper(getContext());
        database = dbHelper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return database.query(DBOpenHelper.TABLE_AIRLINES, DBOpenHelper.ALL_COLUMNS,
                selection, selectionArgs, null, null, DBOpenHelper.AIRLINE_ID + " DESC");
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = database.insert(DBOpenHelper.TABLE_AIRLINES, null, values);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return  database.delete(DBOpenHelper.TABLE_AIRLINES, selection, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return database.update(DBOpenHelper.TABLE_AIRLINES, values, selection, selectionArgs);
    }
}
