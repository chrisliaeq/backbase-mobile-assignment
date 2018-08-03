package ca.aequilibrium.weather.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import ca.aequilibrium.weather.R;
import ca.aequilibrium.weather.adapters.ForecastAdapter;
import ca.aequilibrium.weather.managers.SettingsManager;
import ca.aequilibrium.weather.models.CurrentWeather;
import ca.aequilibrium.weather.models.FiveDayForecast;
import ca.aequilibrium.weather.models.Weather;
import ca.aequilibrium.weather.viewmodels.CityViewModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris Li on 2018-08-01.
 * Copyright © 2018 Aequilibrium. All rights reserved.
 */
public class CityFragment extends Fragment {

    public interface CityFragmentListener {

        void onBackIconPressed();
    }

    public static final String TAG = CityFragment.class.getSimpleName();
    private static final String LATITUDE_KEY = "latitude_key";
    private static final String LONGITUDE_KEY = "longitude_key";
    private CityFragmentListener mCityFragmentListener;
    private CityViewModel mCityViewModel;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private TextView mHumidityText;
    private TextView mRainChanceText;
    private TextView mTemperatureText;
    private Toolbar mToolbar;
    private TextView mWindText;
    private TextView mMainText;
    private TextView mDescriptionText;
    private ForecastAdapter mForecastAdapter;

    public static CityFragment newInstance(double latitude, double longitude) {
        CityFragment cityFragment = new CityFragment();
        Bundle arguments = new Bundle();
        arguments.putDouble(LATITUDE_KEY, latitude);
        arguments.putDouble(LONGITUDE_KEY, longitude);
        cityFragment.setArguments(arguments);
        return cityFragment;
    }

    public CityFragment() {
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        if (context instanceof CityFragmentListener) {
            mCityFragmentListener = (CityFragmentListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCityViewModel = ViewModelProviders.of(this).get(CityViewModel.class);
        if (getArguments() != null) {
            double latitude = getArguments().getDouble(LATITUDE_KEY);
            double longitude = getArguments().getDouble(LONGITUDE_KEY);
            mCityViewModel.setCoordinates(latitude, longitude);
        }
        mForecastAdapter = new ForecastAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city, container, false);
        mToolbar = view.findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (mCityFragmentListener != null) {
                    mCityFragmentListener.onBackIconPressed();
                }
            }
        });

        mCollapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar);
        mTemperatureText = view.findViewById(R.id.temperature_value_text);
        mWindText = view.findViewById(R.id.wind_value_text);
        mHumidityText = view.findViewById(R.id.humidity_value_text);
        mRainChanceText = view.findViewById(R.id.rain_chance_value_text);
        mMainText = view.findViewById(R.id.main_text);
        mDescriptionText = view.findViewById(R.id.description_text);

        final RecyclerView forecastList = view.findViewById(R.id.forecast_list);
        forecastList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        forecastList.setAdapter(mForecastAdapter);

        mCityViewModel.getCurrentWeather().observe(this, new Observer<CurrentWeather>() {
            @Override
            public void onChanged(@Nullable final CurrentWeather currentWeather) {
                setCurrentWeatherUI(currentWeather);
            }
        });
        mCityViewModel.getFiveDayForecast().observe(this, new Observer<FiveDayForecast>() {
            @Override
            public void onChanged(@Nullable final FiveDayForecast fiveDayForecast) {
                setFiveDayForecastUI(fiveDayForecast);
            }
        });
        mCityViewModel.retrieveCurrentWeatherData();
        mCityViewModel.retrieveFiveDayForecastData();
        return view;
    }

    public void setCurrentWeatherUI(CurrentWeather currentWeather) {
        if (currentWeather != null) {
            mToolbar.setTitle(currentWeather.getName());
            mCollapsingToolbarLayout.setTitle(currentWeather.getName());

            if (SettingsManager.getInstance(getContext()).isMetric()) {
                mTemperatureText
                        .setText(getString(R.string.metric_temp, String.valueOf(currentWeather.getMain().getTemp())));
                mWindText.setText(getString(R.string.metric_speed, String.valueOf(currentWeather.getWind().getSpeed())));
            } else {
                mTemperatureText
                        .setText(getString(R.string.imperial_temp, String.valueOf(currentWeather.getMain().getTemp())));
                mWindText.setText(getString(R.string.imperial_speed, String.valueOf(currentWeather.getWind().getSpeed())));
            }
            mHumidityText
                    .setText(getString(R.string.percentage, String.valueOf(currentWeather.getMain().getHumidity())));
            if (currentWeather.getRain() != null) {
                mRainChanceText.setText(getString(R.string.volume_mm, String.valueOf(currentWeather.getRain().getVolume())));
            } else {
                mRainChanceText.setText(getString(R.string.volume_mm, String.valueOf(0)));
            }

            List<String> weatherNames = new ArrayList<>();
            for (Weather weather : currentWeather.getWeather()) {
                weatherNames.add(weather.getMain());
            }
            mMainText.setText(TextUtils.join(", ", weatherNames));
            if (!currentWeather.getWeather().isEmpty()) {
                mDescriptionText.setText(currentWeather.getWeather().get(0).getDescription());
            }
        }
    }

    private void setFiveDayForecastUI(final FiveDayForecast fiveDayForecast) {
        if (fiveDayForecast != null) {
            mForecastAdapter.setForecasts(fiveDayForecast.getList());
        }
    }
}
