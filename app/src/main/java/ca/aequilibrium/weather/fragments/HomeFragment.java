package ca.aequilibrium.weather.fragments;

import android.Manifest.permission;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ca.aequilibrium.weather.R;
import ca.aequilibrium.weather.adapters.BookmarkedLocationsAdapter;
import ca.aequilibrium.weather.adapters.BookmarkedLocationsAdapter.OnRemoveLocationListener;
import ca.aequilibrium.weather.models.BookmarkedLocation;
import ca.aequilibrium.weather.viewmodels.HomeViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Chris Li on 2018-08-01.
 * Copyright © 2018 Aequilibrium. All rights reserved.
 */
public class HomeFragment extends Fragment implements OnMapLongClickListener,
        Observer<List<BookmarkedLocation>>, OnRemoveLocationListener {

    public static final String TAG = HomeFragment.class.getSimpleName();
    private static final int REQUEST_LOCATION_PERMISSIONS = 102;
    private BookmarkedLocationsAdapter mBookmarkedLocationsAdapter;
    private RecyclerView mBookmarkedLocationsList;
    private HomeViewModel mHomeViewModel;
    private HashMap<String, Marker> mLocationMarkerMap = new HashMap<>();
    private GoogleMap mMap;
    private MapView mMapView;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        mBookmarkedLocationsAdapter = new BookmarkedLocationsAdapter();
        mBookmarkedLocationsAdapter.setListener(this);
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

        mBookmarkedLocationsList = view.findViewById(R.id.bookmarked_locations_list);
        mBookmarkedLocationsList
                .setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mBookmarkedLocationsList.setAdapter(mBookmarkedLocationsAdapter);
        mHomeViewModel.getBookmarkedLocations().observe(this,
                this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
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

        if (mMap != null && locations != null) {
            for (BookmarkedLocation bookmarkedLocation : locations) {
                if (!mLocationMarkerMap.containsKey(bookmarkedLocation.getId())) {
                    Marker marker = mMap.addMarker(new MarkerOptions()
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                            .position(
                                    new LatLng(bookmarkedLocation.getLatitude(), bookmarkedLocation.getLongitude())));
                    mLocationMarkerMap.put(bookmarkedLocation.getId(), marker);
                }
            }
        }
    }

    @Override
    public void onLocationRemoved(final BookmarkedLocation location) {
        AlertDialog.Builder dialogBuilder = new Builder(getContext());
        dialogBuilder.setTitle(R.string.remove_location_title);
        dialogBuilder.setMessage(R.string.remove_location_message);
        dialogBuilder.setPositiveButton(R.string.yes, new OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialogInterface, final int i) {
                if (mLocationMarkerMap.containsKey(location.getId())) {
                    mLocationMarkerMap.get(location.getId()).remove();
                }
                mHomeViewModel.removeLocation(location);
            }
        });
        dialogBuilder.setNegativeButton(R.string.no, new OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialogInterface, final int i) {
                dialogInterface.dismiss();
            }
        });
        dialogBuilder
                .create()
                .show();
    }

    @Override
    public void onLowMemory() {
        mMapView.onLowMemory();
        super.onLowMemory();
    }

    @Override
    public void onMapLongClick(final LatLng latLng) {
        mHomeViewModel.addLocation(latLng);
    }

    private void setupMap() {
        mMap.setOnMapLongClickListener(this);
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
        }
    }
}
