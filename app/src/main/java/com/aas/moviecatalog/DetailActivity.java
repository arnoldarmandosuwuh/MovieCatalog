package com.aas.moviecatalog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.aas.moviecatalog.model.Movie;

import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tvJudul, tvOverview;
        ImageView ivPoster;

        tvJudul = findViewById(R.id.tvJudulFilm);
        tvOverview = findViewById(R.id.tvOverview);
        ivPoster = findViewById(R.id.ivPosterFilm);

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        setTitle(movie.getJudul());

        tvJudul.setText(movie.getJudul());
        tvOverview.setText(movie.getSinopsis());
        ivPoster.setImageResource(movie.getPoster());
    }


}
