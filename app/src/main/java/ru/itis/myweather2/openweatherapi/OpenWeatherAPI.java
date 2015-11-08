package ru.itis.myweather2.openweatherapi;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.Query;
import ru.itis.myweather2.model.Forecast;

/**
 * Created by yasina on 08.11.15.
 */
public interface OpenWeatherAPI {

    @GET("/weather")
    Forecast getWeatherByLatLon(@Query("lat") double lat,@Query("lon") double lon);

}
