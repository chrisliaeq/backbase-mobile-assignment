package ca.aequilibrium.weather.network;



import ca.aequilibrium.weather.BuildConfig;
import ca.aequilibrium.weather.models.FiveDayForecast;


/**
 * Created by Chris Li on 2018-08-01.
 * Copyright © 2018 Aequilibrium. All rights reserved.
 */
public class GetFiveDayForecastAsyncTask extends GetRequestAsyncTask<FiveDayForecast> {

    private double mLatitude;
    private double mLongitude;
    private boolean mIsMetric;

    public GetFiveDayForecastAsyncTask(boolean isMetric, double latitude, double longitude, Class<FiveDayForecast> clazz) {
        super(clazz);
        mLatitude = latitude;
        mLongitude = longitude;
        mIsMetric = isMetric;
    }

    @Override
    String buildUrl() {
        String url = NetworkConstants.BASE_URL + NetworkConstants.FORECAST;
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
