package ru.samples.itis.loaders.weather;

import com.google.gson.annotations.SerializedName;

/**
 * @author Artur Vasilov
 */
public class Weather {

    @SerializedName("name")
    private String mName;
    private String mTemp;

    @SerializedName("main")
    private MainParams mParams;

    private class MainParams {

        @SerializedName("humidity")
        private float mHumidity;

        @SerializedName("pressure")
        private float mPressure;

        @SerializedName("temp")
        private float mTemperature;

    }

    public String getName() {
        return mName;
    }
    public float getTemp(){
        return mParams.mTemperature;
    }
}
