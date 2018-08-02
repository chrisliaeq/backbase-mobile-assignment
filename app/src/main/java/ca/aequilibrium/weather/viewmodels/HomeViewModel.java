package ca.aequilibrium.weather.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import ca.aequilibrium.weather.database.AppDatabase;
import ca.aequilibrium.weather.models.Location;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;

/**
 * Created by Chris Li on 2018-08-01.
 * Copyright © 2018 Aequilibrium. All rights reserved.
 */
public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<List<Location>> mBookmarkedLocationData = new MutableLiveData<>();

    public HomeViewModel(Application application) {
        super(application);
    }

    public void addLocation(final LatLng latLng) {
        Location location = new Location();
        location.setId(latLng.toString());
        location.setLatitude(latLng.latitude);
        location.setLongitude(latLng.longitude);
        AppDatabase.getDatabase(getApplication().getApplicationContext()).getLocationDao()
                .addLocation(location);
    }

    public LiveData<List<Location>> getBookmarkedLocations() {
        return AppDatabase.getDatabase(getApplication().getApplicationContext()).getLocationDao()
                .getAllBookmarkedLocations();
    }
}
