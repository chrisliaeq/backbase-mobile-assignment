package ca.aequilibrium.weather.managers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Chris Li on 2018-08-02.
 * Copyright © 2018 Aequilibrium. All rights reserved.
 */
public class SettingsManager {

    private static SettingsManager INSTANCE = null;
    private static final String USER_SETTINGS = "user_settings";
    private static final String IS_METRIC_SETTING_KEY = "is_metric";

    private SharedPreferences mSharedPreferences;

    public static SettingsManager getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SettingsManager(context);
        }
        return INSTANCE;
    }

    private SettingsManager(Context context) {
        mSharedPreferences = context.getSharedPreferences(USER_SETTINGS, Context.MODE_PRIVATE);
    }


    public void setIsMetric(boolean isMetric) {
        mSharedPreferences
                .edit()
                .putBoolean(IS_METRIC_SETTING_KEY, isMetric)
                .apply();
    }

    public boolean isMetric() {
        return mSharedPreferences.getBoolean(IS_METRIC_SETTING_KEY, true);
    }

}
