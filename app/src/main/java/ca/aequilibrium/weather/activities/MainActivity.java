package ca.aequilibrium.weather.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import ca.aequilibrium.weather.R;
import ca.aequilibrium.weather.fragments.CityFragment;
import ca.aequilibrium.weather.fragments.CityFragment.CityFragmentListener;
import ca.aequilibrium.weather.fragments.HelpFragment;
import ca.aequilibrium.weather.fragments.HomeFragment;
import ca.aequilibrium.weather.fragments.HomeFragment.HomeFragmentListener;
import ca.aequilibrium.weather.fragments.SettingsFragment;
import ca.aequilibrium.weather.models.BookmarkedLocation;

public class MainActivity extends AppCompatActivity implements HomeFragmentListener, CityFragmentListener {

    private HelpFragment mHelpFragment = null;
    private HomeFragment mHomeFragment = null;
    private Fragment mPreviousFragment = null;
    private SettingsFragment mSettingsFragment = null;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            setFragment(item.getItemId());
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setFragment(R.id.navigation_home);
    }

    @Override
    public void onBackIconPressed() {
        onBackPressed();
    }

    @Override
    public void onBookmarkedLocationClicked(final BookmarkedLocation location) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container,
                        CityFragment.newInstance(location.getLatitude(), location.getLongitude()), CityFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

    private void addFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit();
    }

    private void displayFragment(Fragment fragment) {
        if (mPreviousFragment != null) {
            hideFragment(mPreviousFragment);
        }
        showFragment(fragment);
        mPreviousFragment = fragment;
    }

    private void hideFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .hide(fragment)
                .commit();
    }

    private void setFragment(final int itemId) {
        // Remove CityFragment if present.
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
        switch (itemId) {
            case R.id.navigation_home:
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                    addFragment(mHomeFragment);
                }
                displayFragment(mHomeFragment);
                break;
            case R.id.navigation_settings:
                if (mSettingsFragment == null) {
                    mSettingsFragment = new SettingsFragment();
                    addFragment(mSettingsFragment);
                }
                displayFragment(mSettingsFragment);
                break;
            case R.id.navigation_help:
                if (mHelpFragment == null) {
                    mHelpFragment = new HelpFragment();
                    addFragment(mHelpFragment);
                }
                displayFragment(mHelpFragment);
                break;
        }
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .show(fragment)
                .commit();
    }
}
