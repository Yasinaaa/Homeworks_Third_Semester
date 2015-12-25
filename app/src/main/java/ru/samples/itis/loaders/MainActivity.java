package ru.samples.itis.loaders;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import ru.samples.itis.loaders.weather.RetrofitLoader;
import ru.samples.itis.loaders.weather.Weather;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Weather> {
    TextView weather_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        weather_text = (TextView) findViewById(R.id.weather_text);
        getLoaderManager().initLoader(R.id.weather_loader, Bundle.EMPTY, this);
    }

    @Override
    public Loader<Weather> onCreateLoader(int id, Bundle args) {
        if (id == R.id.weather_loader) {
            return new RetrofitLoader(this);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Weather> loader, Weather data) {
        if (loader.getId() == R.id.weather_loader) {
            if (data == null) {
                weather_text.setText("Error!");
            } else {

                weather_text.setText(data.getName()+" " + data.getTemp());
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Weather> loader) {
    }
}


