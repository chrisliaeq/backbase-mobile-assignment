package ca.aequilibrium.weather.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

    private void setFragment(final int itemId) {
        Fragment fragment = null;
        String fragmentTag = "";
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (itemId) {
            case R.id.navigation_home:
                fragmentTag = HomeFragment.TAG;
                fragment = fragmentManager.findFragmentByTag(fragmentTag);
                if (fragment == null) {
                    fragment = new HomeFragment();
                }
                break;
            case R.id.navigation_settings:
                fragmentTag = SettingsFragment.TAG;
                fragment = fragmentManager.findFragmentByTag(fragmentTag);
                if (fragment == null) {
                    fragment = new SettingsFragment();
                }
                break;
            case R.id.navigation_help:
                fragmentTag = HelpFragment.TAG;
                fragment = fragmentManager.findFragmentByTag(fragmentTag);
                if (fragment == null) {
                    fragment = new HelpFragment();
                }
                break;
        }
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment, fragmentTag)
                    .commit();
        }
    }
}
