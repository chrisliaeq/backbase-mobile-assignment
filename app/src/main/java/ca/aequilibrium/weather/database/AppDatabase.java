package ca.aequilibrium.weather.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import ca.aequilibrium.weather.models.BookmarkedLocation;

/**
 * Created by Chris Li on 2018-08-01.
 * Copyright © 2018 Aequilibrium. All rights reserved.
 */
@Database(entities = {BookmarkedLocation.class}, version = 1, exportSchema = false)
@TypeConverters({DataTypeConverters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "weather_db")
                    .allowMainThreadQueries() // Done for simplicity, normally would run all queries off the main thread.
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public abstract LocationDao getLocationDao();
}
