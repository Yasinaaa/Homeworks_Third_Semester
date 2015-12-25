package ru.itis.myweather2.dataa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WeatherDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    static final String DATABASE_NAME = "weather.db";

    public WeatherDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + WeatherContract.TABLE_NAME_WEATHER + " (" +
                WeatherContract._ID + " INTEGER PRIMARY KEY," +
                WeatherContract.COLUMN_LAT + " REAL , " +
                WeatherContract.COLUMN_LON + " REAL , " +
                WeatherContract.COLUMN_TEMP + " REAL , " +
                WeatherContract.COLUMN_PRESSURE + " REAL , " +
                WeatherContract.COLUMN_HUMIDITY + " INTEGER , " +
                WeatherContract.COLUMN_TEMP_MIN + " REAL , " +
                WeatherContract.COLUMN_TEMP_MAX + " REAL , " +
                WeatherContract.COLUMN_SEA_LEVEL + " REAL , " +
                WeatherContract.COLUMN_GRND_LEVEL + " REAL , " +
                WeatherContract.COLUMN_SPEED + " REAL , " +
                WeatherContract.COLUMN_DEG + " REAL , " +
                WeatherContract.COLUMN_ALL + " INTEGER  " +
                " );";
//   + " TEXT UNIQUE NOT NULL, " +
        sqLiteDatabase.execSQL(SQL_CREATE_LOCATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WeatherContract.TABLE_NAME_WEATHER);
        onCreate(sqLiteDatabase);
    }
}
