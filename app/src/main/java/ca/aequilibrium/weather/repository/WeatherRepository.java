package ca.aequilibrium.weather.repository;

/**
 * Created by Chris Li on 2018-08-01.
 * Copyright © 2018 Aequilibrium. All rights reserved.
 */
public class WeatherRepository implements WeatherDataSource {

    private static WeatherRepository sInstance = null;

    public static WeatherRepository getInstance() {
        if (sInstance == null) {
            sInstance = new WeatherRepository();
        }
        return sInstance;
    }

    private WeatherRepository() {
    }

}
