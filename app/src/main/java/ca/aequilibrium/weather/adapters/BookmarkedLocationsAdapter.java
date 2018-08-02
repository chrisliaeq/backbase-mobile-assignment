package ca.aequilibrium.weather.adapters;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ca.aequilibrium.weather.R;
import ca.aequilibrium.weather.adapters.BookmarkedLocationsAdapter.LocationItemViewHolder;
import ca.aequilibrium.weather.models.BookmarkedLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris Li on 2018-08-01.
 * Copyright © 2018 Aequilibrium. All rights reserved.
 */
public class BookmarkedLocationsAdapter extends RecyclerView.Adapter<LocationItemViewHolder> {

    private List<BookmarkedLocation> mLocations = new ArrayList<>();

    @Override
    public int getItemCount() {
        return mLocations.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final LocationItemViewHolder holder, final int position) {
        BookmarkedLocation location = mLocations.get(position);

    }

    @NonNull
    @Override
    public LocationItemViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        return new LocationItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false));
    }

    public void setLocationItems(List<BookmarkedLocation> locations) {
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

        private List<BookmarkedLocation> newListData;
        private List<BookmarkedLocation> oldListData;

        public LocationDiffCallback(List<BookmarkedLocation> oldListData, List<BookmarkedLocation> newListData) {
            this.oldListData = oldListData;
            this.newListData = newListData;
        }

        @Override
        public boolean areContentsTheSame(final int oldItemPosition, final int newItemPosition) {
            BookmarkedLocation oldLocation = oldListData.get(oldItemPosition);
            BookmarkedLocation newLocation = newListData.get(newItemPosition);
            return oldLocation.getId().equals(newLocation.getId());
        }

        @Override
        public boolean areItemsTheSame(final int oldItemPosition, final int newItemPosition) {
            BookmarkedLocation oldLocation = oldListData.get(oldItemPosition);
            BookmarkedLocation newLocation = newListData.get(newItemPosition);
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
