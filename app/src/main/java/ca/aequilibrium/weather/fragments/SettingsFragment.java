package ca.aequilibrium.weather.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import ca.aequilibrium.weather.R;
import ca.aequilibrium.weather.database.AppDatabase;
import ca.aequilibrium.weather.managers.SettingsManager;

/**
 * Created by Chris Li on 2018-08-02.
 * Copyright © 2018 Aequilibrium. All rights reserved.
 */
public class SettingsFragment extends Fragment {

    public static final String TAG = SettingsFragment.class.getSimpleName();


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        final Switch unitSwitch = view.findViewById(R.id.use_metric_switch);
        unitSwitch.setChecked(SettingsManager.getInstance(getContext()).isMetric());
        unitSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton compoundButton, final boolean isChecked) {
                SettingsManager.getInstance(getContext()).setIsMetric(isChecked);
            }
        });
        view.findViewById(R.id.clear_button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                showClearLocationsDialog();
            }
        });

        return view;
    }

    private void showClearLocationsDialog() {
        AlertDialog.Builder dialogBuilder = new Builder(getContext());
        dialogBuilder.setTitle(R.string.clear_locations);
        dialogBuilder.setMessage(R.string.clear_bookmarks_message);
        dialogBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialogInterface, final int i) {
                AppDatabase.getDatabase(getContext().getApplicationContext()).getLocationDao().removeAll();
            }
        });
        dialogBuilder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialogInterface, final int i) {
                dialogInterface.dismiss();
            }
        });
        dialogBuilder
                .create()
                .show();
    }
}
