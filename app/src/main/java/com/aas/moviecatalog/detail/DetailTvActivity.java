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
import com.aas.moviecatalog.model.TvShow;
import com.aas.moviecatalog.util.DetailTvShowAsyncTaskCallback;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

public class DetailTvActivity extends AppCompatActivity implements DetailTvShowAsyncTaskCallback {
    public static final String EXTRA_MOVIE = "extra_movie";
    private TextView tvJudul, tvOverview, tvTglRilis;
    private ImageView ivPoster;
    private ProgressBar progressBar;

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

        TvShow listTv = getIntent().getParcelableExtra(EXTRA_MOVIE);
        if (listTv.getName() != null){
            setTitle(listTv.getName());
        }
        new DetailAsyncTask(this).execute(listTv);
    }

    private void showLoading(boolean loading){
        if(loading){
            progressBar.setVisibility(View.VISIBLE);
        }
        else {
            progressBar.setVisibility(View.GONE);
        }
    }


    @Override
    public void onPreExecute() {
        showLoading(true);
    }

    @Override
    public void onPostExecute(TvShow tvShow) {
        tvJudul.setText(tvShow.getName());
        tvTglRilis.setText(tvShow.getFirstAirDate());
        tvOverview.setText(tvShow.getOverview());

        Picasso.get().load(TheMovieDBAPI.getPoster(tvShow.getPosterPath())).into(ivPoster);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(tvShow.getName());
        }

        showLoading(false);
    }

    private static class DetailAsyncTask extends AsyncTask<TvShow, Void, TvShow> {

        WeakReference<DetailTvShowAsyncTaskCallback> callback;

        DetailAsyncTask(DetailTvShowAsyncTaskCallback callback){
            this.callback = new WeakReference<>(callback);
        }

        @Override
        protected TvShow doInBackground(TvShow... tvShows) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return tvShows[0];
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            DetailTvShowAsyncTaskCallback callback = this.callback.get();
            if(callback != null){
                callback.onPreExecute();
            }
        }

        @Override
        protected void onPostExecute(TvShow tvShow) {
            super.onPostExecute(tvShow);

            DetailTvShowAsyncTaskCallback callback = this.callback.get();
            if (callback != null){
                callback.onPostExecute(tvShow);
            }
        }
    }
}
