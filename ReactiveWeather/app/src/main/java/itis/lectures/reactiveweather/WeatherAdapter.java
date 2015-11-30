package itis.lectures.reactiveweather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.PostHolder> {

    private final Context mContext;

    private final List<Weather> mWeathers;

    public WeatherAdapter(Context context, List<Weather> mWeathers) {
        mContext = context;
        this.mWeathers = mWeathers;

    }
    
    public void add(@NonNull Weather weather) {
        mWeathers.add(weather);
        notifyDataSetChanged();
    }

    @Override
    public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PostHolder(
                LayoutInflater.from(mContext)
                        .inflate(R.layout.weather_item, parent, false));
    }

    @Override
    public void onBindViewHolder(PostHolder holder, int position) {
        Weather weather = mWeathers.get(position);

        holder.mWeatherName.setText(weather.getName());
        String temperature = mContext.getString(R.string.temperature, String.valueOf(weather.getTemperature()));
        holder.mWeatherTemp.setText(temperature);
    }

    @Override
    public int getItemCount() {
        return mWeathers.size();
    }

    class PostHolder extends RecyclerView.ViewHolder {

        private final TextView mWeatherName;
        private final TextView mWeatherTemp;

        public PostHolder(View itemView) {
            super(itemView);
            mWeatherName = (TextView) itemView.findViewById(R.id.weatherName);
            mWeatherTemp = (TextView) itemView.findViewById(R.id.weatherTemperature);
        }

    }
}
