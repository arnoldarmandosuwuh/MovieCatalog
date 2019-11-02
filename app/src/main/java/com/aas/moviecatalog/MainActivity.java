package com.aas.moviecatalog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.aas.moviecatalog.adapter.MovieAdapter;
import com.aas.moviecatalog.model.Movie;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String[] dataJudul;
    private String[] dataOverview;
    private TypedArray dataPoster;
    private MovieAdapter mAdapter;
    private ListView lvMovie;
    private ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new MovieAdapter(this);
        lvMovie = findViewById(R.id.lvMovie);

        lvMovie.setAdapter(mAdapter);

        prepare();
        addItem();

        lvMovie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(MainActivity.this, movies.get(i).getJudul(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_MOVIE, movies.get(i));
                startActivity(intent);
            }
        });
    }

    private void addItem(){
        movies = new ArrayList<>();

        for (int i=0; i < dataJudul.length; i++){
            Movie movie = new Movie();
            movie.setJudul(dataJudul[i]);
            movie.setSinopsis(dataOverview[i]);
            movie.setPoster(dataPoster.getResourceId(i, -1));
            movies.add(movie);
        }
        mAdapter.setMovies(movies);
    }
    private void prepare(){
        dataJudul = getResources().getStringArray(R.array.data_judul);
        dataOverview = getResources().getStringArray(R.array.data_overview);
        dataPoster = getResources().obtainTypedArray(R.array.data_poster);
    }
}
