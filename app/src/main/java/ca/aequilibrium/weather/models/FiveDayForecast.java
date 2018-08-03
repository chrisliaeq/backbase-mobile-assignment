package ca.aequilibrium.weather.models;

import java.util.ArrayList;
import java.util.List;

public class FiveDayForecast {

    private City city;
    private List<Forecast> list = new ArrayList<>();

    public City getCity() {
        return city;
    }

    public void setCity(final City city) {
        this.city = city;
    }

    public List<Forecast> getList() {
        return list;
    }

    public void setList(final List<Forecast> list) {
        this.list = list;
    }
}
