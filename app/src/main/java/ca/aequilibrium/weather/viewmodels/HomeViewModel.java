package ca.aequilibrium.weather.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import ca.aequilibrium.weather.database.AppDatabase;
import ca.aequilibrium.weather.models.BookmarkedLocation;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;

/**
 * Created by Chris Li on 2018-08-01.
 * Copyright © 2018 Aequilibrium. All rights reserved.
 */
public class HomeViewModel extends AndroidViewModel {

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
        return AppDatabase
                .getDatabase(getApplication().getApplicationContext()).getLocationDao()
                .getAllBookmarkedLocations();
    }

    public void removeLocation(final BookmarkedLocation location) {
        AppDatabase.getDatabase(getApplication().getApplicationContext()).getLocationDao()
                .removeLocation(location);
    }
}
