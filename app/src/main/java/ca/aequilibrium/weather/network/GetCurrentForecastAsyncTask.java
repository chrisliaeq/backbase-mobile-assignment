package ca.aequilibrium.weather.network;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Chris Li on 2018-08-01.
 * Copyright © 2018 Aequilibrium. All rights reserved.
 */
public class GetCurrentForecastAsyncTask extends AsyncTask<LatLng, Void, Void> {


    @Override
    protected Void doInBackground(LatLng... latLngs) {
        LatLng latLng = latLngs[0];
        return null;
    }
}
