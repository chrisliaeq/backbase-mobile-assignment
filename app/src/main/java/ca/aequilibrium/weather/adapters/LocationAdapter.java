package ca.aequilibrium.weather.adapters;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import ca.aequilibrium.weather.adapters.LocationAdapter.LocationItemViewHolder;
import ca.aequilibrium.weather.models.Location;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris Li on 2018-08-01.
 * Copyright © 2018 Aequilibrium. All rights reserved.
 */
public class LocationAdapter extends RecyclerView.Adapter<LocationItemViewHolder> {

    private List<Location> mLocations = new ArrayList<>();

    @Override
    public int getItemCount() {
        return mLocations.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final LocationItemViewHolder holder, final int position) {

    }

    @NonNull
    @Override
    public LocationItemViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        return null;
    }

    public void setLocationItems(List<Location> locations) {
        LocationDiffCallback locationDiffCallback = new LocationDiffCallback(mLocations, locations);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(locationDiffCallback);
        mLocations.clear();
        mLocations.addAll(locations);
        diffResult.dispatchUpdatesTo(this);
    }

    static class LocationItemViewHolder extends RecyclerView.ViewHolder {

        public LocationItemViewHolder(final View itemView) {
            super(itemView);
        }
    }

    static class LocationDiffCallback extends DiffUtil.Callback {

        private List<Location> newListData;
        private List<Location> oldListData;

        public LocationDiffCallback(List<Location> oldListData, List<Location> newListData) {
            this.oldListData = oldListData;
            this.newListData = newListData;
        }

        @Override
        public boolean areContentsTheSame(final int oldItemPosition, final int newItemPosition) {
            Location oldLocation = oldListData.get(oldItemPosition);
            Location newLocation = newListData.get(newItemPosition);
            return oldLocation.getId().equals(newLocation.getId());
        }

        @Override
        public boolean areItemsTheSame(final int oldItemPosition, final int newItemPosition) {
            Location oldLocation = oldListData.get(oldItemPosition);
            Location newLocation = newListData.get(newItemPosition);
            return oldLocation.getLatitude() == newLocation.getLatitude() && oldLocation.getLongitude() == newLocation
                    .getLongitude();
        }

        @Override
        public int getNewListSize() {
            return newListData.size();
        }

        @Override
        public int getOldListSize() {
            return oldListData.size();
        }
    }

}
