package com.aas.moviecatalog.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aas.moviecatalog.detail.DetailMovieActivity;
import com.aas.moviecatalog.R;
import com.aas.moviecatalog.adapter.MovieAdapter;
import com.aas.moviecatalog.model.Movie;
import com.aas.moviecatalog.viewmodel.ListMovieViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment implements MovieAdapter.MovieListener {

    private MovieAdapter movieAdapter;
    private RecyclerView rvMovie;
    private ArrayList<Movie> listMovie;
    private ProgressBar progressBar;
    private ListMovieViewModel movieViewModel;

    private void showLoading(boolean loading){
        if (loading){
            progressBar.setVisibility(View.VISIBLE);
        }
        else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvMovie = view.findViewById(R.id.rvMovie);
        progressBar = view.findViewById(R.id.pbMovie);
        movieAdapter = new MovieAdapter(getContext(), this);

        movieViewModel = ViewModelProviders.of(this)
                .get(ListMovieViewModel.class);
        movieViewModel.getListMovie().observe(this, getListMovie);

        //rvMovie.setHasFixedSize(true);
        rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovie.setAdapter(movieAdapter);

        showLoading(true);
        movieViewModel.setListMovies();
    }

    private Observer<ArrayList<Movie>> getListMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if (movies != null){
                listMovie = movies;
                movieAdapter.setListMovie(movies);
            }
            showLoading(false);
        }
    };

    @Override
    public void onMovieItemClick(int position) {
        Intent intent = new Intent(getContext(), DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, listMovie.get(position));
        startActivity(intent);
    }
}
