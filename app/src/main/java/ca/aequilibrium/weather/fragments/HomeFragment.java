package ca.aequilibrium.weather.fragments;

import android.Manifest.permission;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.aequilibrium.weather.R;
import ca.aequilibrium.weather.adapters.BookmarkedLocationsAdapter;
import ca.aequilibrium.weather.models.BookmarkedLocation;
import ca.aequilibrium.weather.viewmodels.HomeViewModel;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Created by Chris Li on 2018-08-01.
 * Copyright © 2018 Aequilibrium. All rights reserved.
 */
public class HomeFragment extends Fragment implements OnMapLongClickListener,
        Observer<List<BookmarkedLocation>> {

    public static final String TAG = HomeFragment.class.getSimpleName();
    private static final int REQUEST_LOCATION_PERMISSIONS = 102;

    private HomeViewModel mHomeViewModel;
    private GoogleMap mMap;
    private MapView mMapView;
    private RecyclerView mBookmarkedLocationsList;
    private BookmarkedLocationsAdapter mBookmarkedLocationsAdapter;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        mBookmarkedLocationsAdapter = new BookmarkedLocationsAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mMapView = view.findViewById(R.id.map_view);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                mMap = googleMap;
                setupMap();
            }
        });

        SnapHelper snapHelper = new PagerSnapHelper();
        mBookmarkedLocationsList = view.findViewById(R.id.bookmarked_locations_list);
        mBookmarkedLocationsList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mBookmarkedLocationsList.setAdapter(mBookmarkedLocationsAdapter);

        snapHelper.attachToRecyclerView(mBookmarkedLocationsList);

        mHomeViewModel.getBookmarkedLocations().observe(this,
                this);

        return view;
    }

    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions,
                                           @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length == 2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                setupMap();
            }
        }
    }

    @Override
    public void onChanged(@Nullable final List<BookmarkedLocation> locations) {
        mBookmarkedLocationsAdapter.setLocationItems(locations);
        mBookmarkedLocationsList.smoothScrollToPosition(0);

        if (mMap != null && locations != null) {
            for (BookmarkedLocation bookmarkedLocation : locations) {
                mMap.addMarker(new MarkerOptions().position(new LatLng(bookmarkedLocation.getLatitude(), bookmarkedLocation.getLongitude())));
            }
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onMapLongClick(final LatLng latLng) {
        mHomeViewModel.addLocation(latLng);
        mMap.addMarker(new MarkerOptions().position(latLng));
        mHomeViewModel.getCurrentWeather();
    }

    private void setupMap() {
        if (ActivityCompat.checkSelfPermission(getContext(), permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{permission.ACCESS_FINE_LOCATION, permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION_PERMISSIONS);
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            Location location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .zoom(8.0f)
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            mMap.setOnMapLongClickListener(this);
        }
    }
}
