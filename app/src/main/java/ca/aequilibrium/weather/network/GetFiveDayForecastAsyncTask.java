package ca.aequilibrium.weather.network;



import ca.aequilibrium.weather.models.FiveDayForecast;


/**
 * Created by Chris Li on 2018-08-01.
 * Copyright © 2018 Aequilibrium. All rights reserved.
 */
public class GetFiveDayForecastAsyncTask extends GetRequestAsyncTask<FiveDayForecast> {


    GetFiveDayForecastAsyncTask(final Class<FiveDayForecast> clazz) {
        super(clazz);
    }

    @Override
    String buildUrl() {
        return "http://api.openweathermap.org/data/2.5/forecast?lat=0&lon=0&appid=c6e381d8c7ff98f0fee43775817cf6ad&units=metric";
    }
}
