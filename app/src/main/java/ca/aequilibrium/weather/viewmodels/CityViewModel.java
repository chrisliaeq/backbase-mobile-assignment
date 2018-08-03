package ca.aequilibrium.weather.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import ca.aequilibrium.weather.managers.SettingsManager;
import ca.aequilibrium.weather.models.CurrentWeather;
import ca.aequilibrium.weather.models.FiveDayForecast;
import ca.aequilibrium.weather.network.GetCurrentForecastAsyncTask;
import ca.aequilibrium.weather.network.GetFiveDayForecastAsyncTask;
import ca.aequilibrium.weather.network.GetRequestAsyncTask.RequestListener;

/**
 * Created by Chris Li on 2018-08-01.
 * Copyright © 2018 Aequilibrium. All rights reserved.
 */
public class CityViewModel extends AndroidViewModel {

    private final MutableLiveData<CurrentWeather> mCurrentWeatherMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<FiveDayForecast> mFiveDayForecastMutableLiveData = new MutableLiveData<>();
    private double mLatitude;
    private double mLongitude;
    private SettingsManager mSettingsManager;

    public CityViewModel(@NonNull final Application application) {
        super(application);
        mSettingsManager = SettingsManager.getInstance(application.getApplicationContext());
    }

    public LiveData<CurrentWeather> getCurrentWeather() {
        return mCurrentWeatherMutableLiveData;
    }

    public LiveData<FiveDayForecast> getFiveDayForecast() {
        return mFiveDayForecastMutableLiveData;
    }

    public void retrieveCurrentWeatherData() {
        GetCurrentForecastAsyncTask getCurrentForecastAsyncTask = new GetCurrentForecastAsyncTask(
                mSettingsManager.isMetric(),
                mLatitude,
                mLongitude,
                CurrentWeather.class);
        getCurrentForecastAsyncTask.request(new RequestListener<CurrentWeather>() {
            @Override
            public void onComplete(final CurrentWeather response) {
                if (response != null) {
                    mCurrentWeatherMutableLiveData.setValue(response);
                }
            }
        });
    }

    public void retrieveFiveDayForecastData() {
        GetFiveDayForecastAsyncTask getFiveDayForecastAsyncTask = new GetFiveDayForecastAsyncTask(
                mSettingsManager.isMetric(),
                mLatitude,
                mLongitude,
                FiveDayForecast.class);
        getFiveDayForecastAsyncTask.request(new RequestListener<FiveDayForecast>() {
            @Override
            public void onComplete(final FiveDayForecast response) {
                if (response != null) {
                    mFiveDayForecastMutableLiveData.setValue(response);
                }
            }
        });
    }

    public void setCoordinates(final double latitude, final double longitude) {
        mLatitude = latitude;
        mLongitude = longitude;
    }
}
