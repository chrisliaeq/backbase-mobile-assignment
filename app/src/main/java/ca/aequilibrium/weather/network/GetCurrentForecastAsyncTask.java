package ca.aequilibrium.weather.network;


import android.os.AsyncTask;
import ca.aequilibrium.weather.models.CurrentWeather;

/**
 * Created by Chris Li on 2018-08-01.
 * Copyright © 2018 Aequilibrium. All rights reserved.
 */
public class GetCurrentForecastAsyncTask extends GetRequestAsyncTask<CurrentWeather> {

    public GetCurrentForecastAsyncTask(Class<CurrentWeather> clazz) {
        super(clazz);
    }

    @Override
    public void request(RequestListener<CurrentWeather> listener) {
        mListener = listener;
        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://api.openweathermap.org/data/2.5/weather?lat=0&lon=0&appid=c6e381d8c7ff98f0fee43775817cf6ad&units=metric");
    }
}
