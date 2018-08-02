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
import ca.aequilibrium.weather.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity {


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

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setFragment(R.id.navigation_home);
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
            case R.id.navigation_city:
                fragmentTag = CityFragment.TAG;
                fragment = fragmentManager.findFragmentByTag(fragmentTag);
                if (fragment == null) {
                    fragment = new CityFragment();
                }
                break;
            case R.id.navigation_settings:

                break;
            case R.id.navigation_help:

                break;
        }
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment, fragmentTag)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

        }
        super.onBackPressed();
    }
}
