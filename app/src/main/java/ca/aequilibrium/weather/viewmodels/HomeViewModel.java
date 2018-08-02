package ca.aequilibrium.weather.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;
import ca.aequilibrium.weather.database.AppDatabase;
import ca.aequilibrium.weather.models.BookmarkedLocation;
import ca.aequilibrium.weather.models.CurrentWeather;
import ca.aequilibrium.weather.network.GetCurrentForecastAsyncTask;
import ca.aequilibrium.weather.network.GetRequestAsyncTask;
import ca.aequilibrium.weather.network.NetworkConstants;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Chris Li on 2018-08-01.
 * Copyright © 2018 Aequilibrium. All rights reserved.
 */
public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<List<BookmarkedLocation>> mBookmarkedLocationData = new MutableLiveData<>();

    public HomeViewModel(Application application) {
        super(application);
    }

    public void addLocation(final LatLng latLng) {
        BookmarkedLocation location = new BookmarkedLocation();
        location.setId(latLng.toString());
        location.setLatitude(latLng.latitude);
        location.setLongitude(latLng.longitude);
        AppDatabase.getDatabase(getApplication().getApplicationContext()).getLocationDao()
                .addLocation(location);
    }

    public LiveData<List<BookmarkedLocation>> getBookmarkedLocations() {
        return AppDatabase.getDatabase(getApplication().getApplicationContext()).getLocationDao()
                .getAllBookmarkedLocations();
    }

    public void getCurrentWeather() {
        GetCurrentForecastAsyncTask getRequestAsyncTask = new GetCurrentForecastAsyncTask(CurrentWeather.class);
        getRequestAsyncTask.request(new GetRequestAsyncTask.RequestListener<CurrentWeather>() {
            @Override
            public void onComplete(CurrentWeather response) {

            }
        });
    }
}
