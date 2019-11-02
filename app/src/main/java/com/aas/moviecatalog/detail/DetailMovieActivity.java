package com.aas.moviecatalog.detail;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.aas.moviecatalog.R;
import com.aas.moviecatalog.api.TheMovieDBAPI;
import com.aas.moviecatalog.model.Movie;
import com.aas.moviecatalog.util.DetailMovieAsyncTaskCallback;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailMovieActivity extends AppCompatActivity implements DetailMovieAsyncTaskCallback {

    public static final String EXTRA_MOVIE = "extra_movie";
    private TextView tvJudul, tvOverview, tvTglRilis;
    private ImageView ivPoster;
    private ProgressBar progressBar;
    public static final SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    public static final SimpleDateFormat EEEddMMMyyyy = new SimpleDateFormat("EEE, dd MMM yyyy", Locale.US);

    public String formatDate(String inputDate, SimpleDateFormat inputDateFormat, SimpleDateFormat outputDateFormat) {
        Date date = null;
        String outputDate = null;

        try {
            date = inputDateFormat.parse(inputDate);
            outputDate = outputDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return outputDate;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        tvJudul = findViewById(R.id.tvJudulFilm);
        tvOverview = findViewById(R.id.tvOverview);
        ivPoster = findViewById(R.id.ivPosterFilm);
        tvTglRilis = findViewById(R.id.tvDate);
        progressBar = findViewById(R.id.pbList);

        Movie listMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        new DetailAsyncTask(this).execute(listMovie);
    }

    private void showLoading(boolean loading) {
        if (loading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }


    @Override
    public void onPreExecute() {
        showLoading(true);
    }

    @Override
    public void onPostExecute(Movie movie) {

        tvJudul.setText(movie.getTitle());
        tvTglRilis.setText(formatDate(movie.getReleaseDate(), ymdFormat, EEEddMMMyyyy));
        tvOverview.setText(movie.getOverview());

        Picasso.get().load(TheMovieDBAPI.getPoster(movie.getPosterPath())).into(ivPoster);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(movie.getTitle());
        }

        showLoading(false);
    }

    private static class DetailAsyncTask extends AsyncTask<Movie, Void, Movie> {

        WeakReference<DetailMovieAsyncTaskCallback> callback;

        DetailAsyncTask(DetailMovieAsyncTaskCallback callback) {
            this.callback = new WeakReference<>(callback);
        }

        @Override
        protected Movie doInBackground(Movie... movies) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return movies[0];
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            DetailMovieAsyncTaskCallback callback = this.callback.get();
            if (callback != null) {
                callback.onPreExecute();
            }
        }

        @Override
        protected void onPostExecute(Movie movie) {
            super.onPostExecute(movie);

            DetailMovieAsyncTaskCallback callback = this.callback.get();
            if (callback != null) {
                callback.onPostExecute(movie);
            }
        }
    }
}
