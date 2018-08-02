package ca.aequilibrium.weather.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import ca.aequilibrium.weather.models.Location;
import java.util.List;

/**
 * Created by Chris Li on 2018-08-01.
 * Copyright © 2018 Aequilibrium. All rights reserved.
 */
@Dao
public interface LocationDao {

    @Query("SELECT * FROM Location")
    LiveData<List<Location>> getAllBookmarkedLocations();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addLocation(Location location);

    @Delete
    void removeLocation(Location location);
}
