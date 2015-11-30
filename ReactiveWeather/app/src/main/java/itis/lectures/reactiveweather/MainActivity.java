package itis.lectures.reactiveweather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String[] WEATHER_ITEMS = {
            "Kazan", "Moscow", "Paris", "London",
            "Washington", "Madrid", "Rome", "Berlin"
    };

    private WeatherAdapter mWeatherAdapter;
    private List<Weather> mWeathers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mWeathers = new ArrayList<Weather>();
        getWeathers();
        mWeatherAdapter = new WeatherAdapter(this, mWeathers);
        recyclerView.setAdapter(mWeatherAdapter);

    }

    private void getWeathers(){
        for(String city: WEATHER_ITEMS){

            Observable<Weather> mObservable = ApiFactory.weatherFromQuery(city);

            mObservable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(weather -> mWeathers.add(weather));
        }
    }

}
