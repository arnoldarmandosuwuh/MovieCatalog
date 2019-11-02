package com.aas.moviecatalog.util;

import com.aas.moviecatalog.model.Movie;

public interface DetailMovieAsyncTaskCallback {
    void onPreExecute();
    void onPostExecute(Movie movie);
}
