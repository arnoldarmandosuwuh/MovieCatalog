package com.aas.moviecatalog;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.aas.moviecatalog.fragment.MovieFragment;
import com.aas.moviecatalog.fragment.TvShowFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            setFragment(item.getItemId());
            return true;
        }
    };

    private void setFragment(int page){
        Fragment fragment = null;
        String title;
        switch (page) {
            case R.id.nav_movie:
                fragment = new MovieFragment();
                title = "Movie Catalogue";
                break;
            case R.id.nav_tv:
                fragment = new TvShowFragment();
                title = "Tv Shows Catalogue";
                break;

                default:
                    title = "Movie Catalogue";
        }

        if (fragment != null &&
                getSupportFragmentManager().findFragmentByTag(fragment.getClass().getSimpleName()) == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContent, fragment, fragment.getClass().getSimpleName())
                    .commit();
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        setFragment(navView.getSelectedItemId());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_change_en) {
            restartInLocale(Locale.ENGLISH);
        }
        if (item.getItemId() == R.id.action_change_in) {
            restartInLocale(Locale.forLanguageTag("in-ID"));
        }
        return super.onOptionsItemSelected(item);
    }

    private void restartInLocale(Locale locale) {
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        Resources resources = getResources();
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
