package com.aas.moviecatalog.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aas.moviecatalog.api.TheMovieDBAPI;
import com.aas.moviecatalog.model.Movie;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ListMovieViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Movie>> listMovies = new MutableLiveData<>();

    public void setListMovies() {
        final ArrayList<Movie> list = new ArrayList<>();

        TheMovieDBAPI.get("movie", new RequestParams(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");

                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonItem = jsonArray.getJSONObject(i);
                        Movie movie = new Movie();
                        movie.setId(jsonItem.getInt("id"));
                        movie.setTitle(jsonItem.getString("title"));
                        movie.setReleaseDate(jsonItem.getString("release_date"));
                        movie.setOverview(jsonItem.getString("overview"));
                        movie.setPosterPath(jsonItem.getString("poster_path"));

                        list.add(movie);
                    }
                    listMovies.postValue(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public LiveData<ArrayList<Movie>> getListMovie() {
        return listMovies;
    }
}
