package com.aas.moviecatalog.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aas.moviecatalog.api.TheMovieDBAPI;
import com.aas.moviecatalog.model.TvShow;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ListTvViewModel extends ViewModel {
    private MutableLiveData<ArrayList<TvShow>> listTv = new MutableLiveData<>();

    public void setListTv() {
        final ArrayList<TvShow> list = new ArrayList<>();

        TheMovieDBAPI.get("tv", new RequestParams(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");

                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonItem = jsonArray.getJSONObject(i);
                        TvShow tvShow = new TvShow();
                        tvShow.setId(jsonItem.getInt("id"));
                        tvShow.setName(jsonItem.getString("name"));
                        tvShow.setFirstAirDate(jsonItem.getString("first_air_date"));
                        tvShow.setOverview(jsonItem.getString("overview"));
                        tvShow.setPosterPath(jsonItem.getString("poster_path"));

                        list.add(tvShow);
                    }
                    listTv.postValue(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public LiveData<ArrayList<TvShow>> getListTv() {
        return listTv;
    }
}
