package com.aas.moviecatalog;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.nav_movie:
                    fragment = new MovieFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.flContent, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
                case R.id.nav_tv:
                    fragment = new TvShowFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.flContent, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            Fragment fragment = null;
            Class fragmentClass = MovieFragment.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_change_en){
            restartInLocale(Locale.ENGLISH);
        }
        if (item.getItemId() == R.id.action_change_in){
           /* Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);*/
            restartInLocale(Locale.forLanguageTag("in-ID"));
        }
        return super.onOptionsItemSelected(item);
    }

    private void restartInLocale(Locale locale)
    {
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        Resources resources = getResources();
        resources.updateConfiguration(config, resources.getDisplayMetrics());
//        recreate();
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
