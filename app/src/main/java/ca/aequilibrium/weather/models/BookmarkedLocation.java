package ca.aequilibrium.weather.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Chris Li on 2018-08-01.
 * Copyright © 2018 Aequilibrium. All rights reserved.
 */
@Entity
public class BookmarkedLocation {

    private String cityName;
    private CurrentWeather currentWeather;
    // Will be the toString() call on the LatLng model, to prevent potential duplication of locations.
    @PrimaryKey
    @NonNull
    private String id;
    private long lastUpdated;
    private double latitude;
    private double longitude;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(final String cityName) {
        this.cityName = cityName;
    }

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(final CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(final long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
