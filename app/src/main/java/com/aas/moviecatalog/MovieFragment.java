package com.aas.moviecatalog;


import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aas.moviecatalog.adapter.MovieAdapter;
import com.aas.moviecatalog.model.Movie;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private String[] dataJudul, dataSinopsis;
    private TypedArray dataPoster;
    private MovieAdapter movieAdapter;
    private RecyclerView rvMovie;
    private ArrayList<Movie> listMovie = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        movieAdapter = new MovieAdapter(getContext());
        rvMovie = view.findViewById(R.id.rvMovie);
        rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovie.setAdapter(movieAdapter);

        addItem();

        return view;
    }


    private void addItem() {

        dataJudul = getResources().getStringArray(R.array.data_judul_movie);
        dataSinopsis = getResources().getStringArray(R.array.data_sinopsis_movie);
        dataPoster = getResources().obtainTypedArray(R.array.data_poster_movie);
        listMovie = new ArrayList<>();

        for (int i = 0; i < dataJudul.length; i++) {
            Movie movie = new Movie();
            movie.setJudul(dataJudul[i]);
            movie.setSinopsis(dataSinopsis[i]);
            movie.setPoster(dataPoster.getResourceId(i, -1));
            listMovie.add(movie);
        }
        movieAdapter.setListMovie(listMovie);
    }


}
