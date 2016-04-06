package ru.itis.myweather2.openweatherapi;

import android.net.Uri;

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

import org.apache.commons.lang3.NotImplementedException;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import ru.itis.myweather2.BuildConfig;


/**
 * Created by yasina on 08.11.15.
 */
public class ForecastService extends RetrofitGsonSpiceService {

    RestAdapter.Builder restAdapterBuilder;
    private Map<Class<?>, Object> retrofitInterfaceToServiceMap = new HashMap<Class<?>, Object>();
    private Uri built;
    final String APPID_PARAM = "APPID";
    private String BACKEND_URL = "http://api.openweathermap.org/data/2.5";
    private Map<Class<?>, String> retrofitInterfaceToUrl = new HashMap<Class<?>, String>() {{
        put(OpenWeatherAPI.class, BACKEND_URL);
    }};

    @Override
    public void onCreate() {
        super.onCreate();
       // built = Uri.parse("http://api.openweathermap.org/data/2.5").buildUpon()
        //        .appendQueryParameter(APPID_PARAM, BuildConfig.OPEN_WEATHER_MAP_API_KEY)
        //        .build();
     //   restAdapterBuilder = new RestAdapter.Builder().setConverter(getConverter());

          restAdapterBuilder = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL)
            .setEndpoint(BACKEND_URL).setRequestInterceptor(new RequestInterceptor() {
                      @Override
                      public void intercept(RequestInterceptor.RequestFacade request) {
                          request.addQueryParam("APPID", BuildConfig.OPEN_WEATHER_MAP_API_KEY);
                      }
                  });

    }

    @Override
    protected String getServerUrl() {
        throw new NotImplementedException("Method should not be used");
    }

    @Override
    protected RestAdapter.Builder createRestAdapterBuilder() {

        return new RestAdapter.Builder().setConverter(getConverter()).setEndpoint("http://google.com");
    }

    @Override
    protected <T> T getRetrofitService(Class<T> serviceClass) {
        T service = (T) retrofitInterfaceToServiceMap.get(serviceClass);
        if (service == null) {
           // service = restAdapterBuilder.setEndpoint(retrofitInterfaceToUrl.get(serviceClass)).build().create(serviceClass);
            service = restAdapterBuilder.build().create(serviceClass);
            retrofitInterfaceToServiceMap.put(serviceClass, service);
        }
        return service;
    }
}
