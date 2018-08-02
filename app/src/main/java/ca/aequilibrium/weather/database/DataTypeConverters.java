package ca.aequilibrium.weather.database;

import android.arch.persistence.room.TypeConverter;
import ca.aequilibrium.weather.models.CurrentWeather;
import com.google.gson.Gson;

/**
 * Created by Chris Li on 2018-08-02.
 * Copyright © 2018 Aequilibrium. All rights reserved.
 */
public class DataTypeConverters {

    @TypeConverter
    public static String currentWeatherToString(CurrentWeather currentWeather) {
        return new Gson().toJson(currentWeather);
    }

    @TypeConverter
    public static CurrentWeather stringToCurrentWeather(String currentWeatherString) {
        return new Gson().fromJson(currentWeatherString, CurrentWeather.class);
    }

}
