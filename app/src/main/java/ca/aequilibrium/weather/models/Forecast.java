package ca.aequilibrium.weather.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris Li on 2018-08-02.
 * Copyright © 2018 Aequilibrium. All rights reserved.
 */
public class Forecast {

    private Clouds clouds;
    private long dt;
    private Main main;
    private List<Weather> weather = new ArrayList<>();
    private Wind wind;

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(final Clouds clouds) {
        this.clouds = clouds;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(final long dt) {
        this.dt = dt;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(final Main main) {
        this.main = main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(final List<Weather> weather) {
        this.weather = weather;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(final Wind wind) {
        this.wind = wind;
    }
}
