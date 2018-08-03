package ca.aequilibrium.weather.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ca.aequilibrium.weather.R;
import ca.aequilibrium.weather.adapters.ForecastAdapter.ForecastItemViewHolder;
import ca.aequilibrium.weather.managers.SettingsManager;
import ca.aequilibrium.weather.models.Forecast;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris Li on 2018-08-02.
 * Copyright © 2018 Aequilibrium. All rights reserved.
 */
public class ForecastAdapter extends RecyclerView.Adapter<ForecastItemViewHolder> {

    static class ForecastItemViewHolder extends RecyclerView.ViewHolder {

        TextView dayText;
        TextView tempText;
        TextView timeText;
        TextView weatherText;

        public ForecastItemViewHolder(final View itemView) {
            super(itemView);
            dayText = itemView.findViewById(R.id.day_text);
            timeText = itemView.findViewById(R.id.time_text);
            tempText = itemView.findViewById(R.id.main_temp_text);
            weatherText = itemView.findViewById(R.id.main_text);
        }
    }
    private List<Forecast> mForecasts = new ArrayList<>();

    @Override
    public int getItemCount() {
        return mForecasts.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final ForecastItemViewHolder holder, final int position) {
        Forecast forecast = mForecasts.get(position);
        Context context = holder.itemView.getContext();
        long timestampMillis = forecast.getDt() * 1000;
        String day = DateUtils.formatDateTime(context, timestampMillis, DateUtils.FORMAT_SHOW_WEEKDAY);
        String time = DateUtils.formatDateTime(context, timestampMillis, DateUtils.FORMAT_SHOW_TIME);
        holder.dayText.setText(day);
        holder.timeText.setText(time);
        if (SettingsManager.getInstance(context).isMetric()) {
            holder.tempText
                    .setText(context.getString(R.string.metric_temp, String.valueOf(forecast.getMain().getTemp())));
        } else {
            holder.tempText
                    .setText(context.getString(R.string.imperial_temp, String.valueOf(forecast.getMain().getTemp())));
        }
        if (!forecast.getWeather().isEmpty()) {
            holder.weatherText.setText(forecast.getWeather().get(0).getMain());
        } else {
            holder.weatherText.setText(null);
        }
    }

    @NonNull
    @Override
    public ForecastItemViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        return new ForecastItemViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forecast, parent, false));
    }

    public void setForecasts(final List<Forecast> list) {
        mForecasts.clear();
        mForecasts.addAll(list);
        notifyDataSetChanged();
    }

}
