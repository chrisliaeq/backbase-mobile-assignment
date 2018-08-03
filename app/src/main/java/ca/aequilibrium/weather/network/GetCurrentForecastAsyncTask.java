package ca.aequilibrium.weather.network;


import ca.aequilibrium.weather.BuildConfig;
import ca.aequilibrium.weather.models.CurrentWeather;

/**
 * Created by Chris Li on 2018-08-01.
 * Copyright © 2018 Aequilibrium. All rights reserved.
 */
public class GetCurrentForecastAsyncTask extends GetRequestAsyncTask<CurrentWeather> {

    private double mLatitude;
    private double mLongitude;
    private boolean mIsMetric;

    public GetCurrentForecastAsyncTask(boolean isMetric, double latitude, double longitude, Class<CurrentWeather> clazz) {
        super(clazz);
        mLatitude = latitude;
        mLongitude = longitude;
        mIsMetric = isMetric;
    }

    @Override
    String buildUrl() {
        String url = NetworkConstants.BASE_URL + NetworkConstants.WEATHER;
        url += "?lat=" + mLatitude;
        url += "&lon=" + mLongitude;
        if (mIsMetric) {
            url += "&units=metric";
        } else {
            url += "&units=imperial";
        }
        url += "&appid=" + BuildConfig.WEATHER_APP_ID;
        return url;
    }
}
