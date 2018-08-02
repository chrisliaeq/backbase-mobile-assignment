package ca.aequilibrium.weather.models;

import com.google.gson.annotations.SerializedName;

public class Snow {

    @SerializedName("3h")
    private int volume;

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
