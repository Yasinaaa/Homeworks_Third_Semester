package ru.itis.myweather2.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import ru.itis.myweather2.MainActivity;
import ru.itis.myweather2.R;
import ru.itis.myweather2.dataa.WeatherContract;
import ru.itis.myweather2.model.Forecast;
import ru.itis.myweather2.openweatherapi.WeatherRequest;
import ru.itis.myweather2.utility.Utility;

/**
 * Created by yasina on 08.11.15.
 */

public class SyncAdapter extends AbstractThreadedSyncAdapter {

    public final String LOG_TAG = SyncAdapter.class.getSimpleName();
    public static final int SYNC_INTERVAL = 60 * 180;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL/3;
    /*private static final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;*/
    private static final int WEATHER_NOTIFICATION_ID = 3004;

    private static final String[] NOTIFY_WEATHER_PROJECTION = new String[] {
            WeatherContract._ID,
            WeatherContract.COLUMN_TEMP_MAX,
            WeatherContract.COLUMN_TEMP_MIN,
            WeatherContract.COLUMN_SPEED
    };
    private static final int INDEX_WEATHER_ID = 0;
    private static final int INDEX_MAX_TEMP = 1;
    private static final int INDEX_MIN_TEMP = 2;
    private static final int INDEX_SPEED = 3;
    private static final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
    public static final Forecast[] mForecast = new Forecast[2];

    private Context mContext;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        this.mContext = context;
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        LatLng mCurrentLocation = Utility.getLanLon(mContext);
        processLocation(mCurrentLocation);

        return;
    }

    public void processLocation(LatLng mCurrentLocation) {

        WeatherRequest request = new WeatherRequest(mCurrentLocation.longitude, mCurrentLocation.latitude);
        MainActivity.spiceManager.execute(request, new RequestListener<Forecast>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {

            }

            @Override
            public void onRequestSuccess(Forecast currentForecast) {
                Log.d("TAG Adapter", currentForecast.name);
                content(currentForecast);
                notifyWeather();
            }
        });
    }

    private void notifyWeather() {
        Context context = getContext();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String displayNotificationsKey = context.getString(R.string.pref_enable_notifications_key);
        boolean displayNotifications = prefs.getBoolean(displayNotificationsKey,
                Boolean.parseBoolean(context.getString(R.string.pref_enable_notifications_default)));

//        if ( displayNotifications ) {


                LatLng lanLon = Utility.getLanLon(context);
                String locationQuery = lanLon.latitude + "_" + lanLon.longitude;

                Uri weatherUri = WeatherContract.buildWeatherLocation(locationQuery);

                Cursor cursor = context.getContentResolver().query(weatherUri, NOTIFY_WEATHER_PROJECTION, null, null, null);
            Log.d(LOG_TAG, "weatherUri" +  weatherUri.toString());

                if (cursor.moveToFirst()) {
                    int weatherId = cursor.getInt(INDEX_WEATHER_ID);
                    double high = cursor.getDouble(INDEX_MAX_TEMP);
                    double low = cursor.getDouble(INDEX_MIN_TEMP);
                    String desc = cursor.getString(INDEX_SPEED);

                    String contentText = desc;
                    String title = context.getString(R.string.app_name);

                    Resources resources = context.getResources();
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(getContext())
                                    .setColor(resources.getColor(R.color.sunshine_light_blue))
                                    .setContentTitle(title)
                                    .setContentText(contentText);

                    Intent resultIntent = new Intent(context, MainActivity.class);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                    stackBuilder.addNextIntent(resultIntent);
                    PendingIntent resultPendingIntent =
                            stackBuilder.getPendingIntent(
                                    0,
                                    PendingIntent.FLAG_UPDATE_CURRENT
                            );
                    mBuilder.setContentIntent(resultPendingIntent);

                    NotificationManager mNotificationManager =
                            (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManager.notify(WEATHER_NOTIFICATION_ID, mBuilder.build());

                }
                cursor.close();
  //          }
    }

    public void content(Forecast mForecast){
        ContentValues[] weatherValues = new ContentValues[2];
        Log.d(LOG_TAG, mForecast.main.temp + " ");
        weatherValues[0] = new ContentValues();
                weatherValues[0].put(WeatherContract.COLUMN_TEMP, mForecast.main.temp);
                weatherValues[0].put(WeatherContract.COLUMN_PRESSURE, mForecast.main.pressure);
                weatherValues[0].put(WeatherContract.COLUMN_HUMIDITY, mForecast.main.humidity);
                weatherValues[0].put(WeatherContract.COLUMN_TEMP_MIN, mForecast.main.temp_min);
                weatherValues[0].put(WeatherContract.COLUMN_TEMP_MAX, mForecast.main.temp_max);
                weatherValues[0].put(WeatherContract.COLUMN_SEA_LEVEL, mForecast.main.sea_level);
                weatherValues[0].put(WeatherContract.COLUMN_GRND_LEVEL, mForecast.main.grnd_level);
                weatherValues[0].put(WeatherContract.COLUMN_SPEED, mForecast.wind.speed);

        weatherValues[0].put(WeatherContract.COLUMN_DEG, mForecast.wind.deg);
        getContext().getContentResolver().bulkInsert(WeatherContract.CONTENT_URI, weatherValues);

    }

    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }
    }


    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }

    public static Account getSyncAccount(Context context) {

        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));


        if ( null == accountManager.getPassword(newAccount) ) {
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        SyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);
        syncImmediately(context);
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }
}