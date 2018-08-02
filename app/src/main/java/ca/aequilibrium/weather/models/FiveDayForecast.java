package ca.aequilibrium.weather.models;

import java.util.ArrayList;
import java.util.List;

public class FiveDayForecast {

    private City city;
    private List<Weather> list = new ArrayList<>();

    public City getCity() {
        return city;
    }

    public void setCity(final City city) {
        this.city = city;
    }
}
