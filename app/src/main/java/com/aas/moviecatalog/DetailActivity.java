package com.aas.moviecatalog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.aas.moviecatalog.model.Movie;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tvJudul, tvOverview;
        ImageView ivPoster;
        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        setTitle(movie.getJudul());

        tvJudul = findViewById(R.id.tvJudulFilm);
        tvOverview = findViewById(R.id.tvOverview);
        ivPoster = findViewById(R.id.ivPosterFilm);

        tvJudul.setText(movie.getJudul());
        tvOverview.setText(movie.getSinopsis());
        ivPoster.setImageResource(movie.getPoster());
    }
}
