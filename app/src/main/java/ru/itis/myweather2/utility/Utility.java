package ru.itis.myweather2.utility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import ru.itis.myweather2.MainActivity;
import ru.itis.myweather2.model.Forecast;
import ru.itis.myweather2.openweatherapi.WeatherRequest;

/**
 * Created by yasina on 08.11.15.
 */
public class Utility {

    public static final String AUTH_SHARED_PREFS = "ru.itis.myweather2.utility.auth_shared_prefs";
    public static final String LAT_VALUE = "ru.itis.myweather2.utility.lat_value";
    public static final String LON_VALUE = "ru.itis.myweather2.utility.lon_value";


    public static LatLng getLanLon(Context context){
        SharedPreferences prefs = context.getSharedPreferences(AUTH_SHARED_PREFS, Activity.MODE_PRIVATE);
        double mLat = Double.parseDouble(prefs.getString(LAT_VALUE, " "));
        double mLon =  Double.parseDouble(prefs.getString(LON_VALUE, " "));
        return new LatLng(mLat, mLon);
    }

    public static void setLatLon(Context context, LatLng mLatLon){
        SharedPreferences prefs = context.getSharedPreferences(AUTH_SHARED_PREFS, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(LAT_VALUE, mLatLon.latitude + "");
        editor.putString(LON_VALUE, mLatLon.longitude + "");
        editor.apply();
    }


}
