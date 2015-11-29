package com.shaznee.kayak.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

    //Constants for db name and version
    private static final String DATABASE_NAME = "airlines.db";
    private static final int DATABASE_VERSION = 1;

    //Constants for identifying table and columns
    public static final String TABLE_AIRLINES = "airlines";
    public static final String AIRLINE_ID = "_id";
    public static final String AIRLINE_CODE = "code";
    public static final String AIRLINE_NAME = "airlineName";
    public static final String AIRLINE_PHONE = "airlinePhone";
    public static final String AIRLINE_WEBSITE = "airlineWebsite";
    public static final String AIRLINE_LOGOURL = "airlineLogoUrl";

    public static final String[] ALL_COLUMNS =
            {AIRLINE_ID, AIRLINE_CODE, AIRLINE_NAME, AIRLINE_PHONE, AIRLINE_WEBSITE, AIRLINE_LOGOURL};

    //SQL to create table
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_AIRLINES + " (" +
                    AIRLINE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + AIRLINE_CODE + " TEXT, "
                    + AIRLINE_NAME + " TEXT, " + AIRLINE_PHONE + " TEXT, " + AIRLINE_WEBSITE + " TEXT, "
                    + AIRLINE_LOGOURL + " TEXT" + ")";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_AIRLINES);
        onCreate(db);
    }
}