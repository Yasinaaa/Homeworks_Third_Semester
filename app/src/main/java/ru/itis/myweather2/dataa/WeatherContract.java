package ru.itis.myweather2.dataa;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class WeatherContract implements BaseColumns {


    public static final String CONTENT_AUTHORITY = "ru.itis.myweather2";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_WEATHER = "weather";
    public static final Uri CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(PATH_WEATHER).build();

    public static final String CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WEATHER;
    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WEATHER;


    public static final String TABLE_NAME_WEATHER = "weather_table";
    public static final String COLUMN_LON = "lon";
    public static final String COLUMN_LAT = "lat";
    public static final String COLUMN_TEMP = "temp";
    public static final String COLUMN_PRESSURE= "pressure";
    public static final String COLUMN_HUMIDITY= "humidity";
    public static final String COLUMN_TEMP_MIN= "temp_min";
    public static final String COLUMN_TEMP_MAX = "temp_max";
    public static final String COLUMN_SEA_LEVEL= "sea_level";
    public static final String COLUMN_GRND_LEVEL = "grnd_level";
    public static final String COLUMN_SPEED = "speed";
    public static final String COLUMN_DEG = "deg";
    public static final String COLUMN_ALL  = "all_clouds";

    public static Uri buildWeatherUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    public static Uri buildWeatherLocation(String locationSetting) {
        return CONTENT_URI.buildUpon().appendPath(locationSetting).build();
    }

    public static String getLocationSettingFromUri(Uri uri) {
        return uri.getPathSegments().get(1);
    }


}
