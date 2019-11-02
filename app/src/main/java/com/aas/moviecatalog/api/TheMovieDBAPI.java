package com.aas.moviecatalog.api;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class TheMovieDBAPI {

    private static final String BASE_URL = "https://api.themoviedb.org/3/discover/";
    private static final String API_KEY = "e852848561694f1300e3eac1deb19c49";
    public static final String API_LANG = "en-US";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams requestParams, AsyncHttpResponseHandler asyncHttpResponseHandler){
        requestParams.put("api_key", API_KEY);
        requestParams.put("language", API_LANG);

        client.get(getAbsoluteUrl(url), requestParams, asyncHttpResponseHandler);

    }

    private static String getAbsoluteUrl(String url) {
        return BASE_URL + url;
    }

    public static String getPoster(String poster){
        return "https://image.tmdb.org/t/p/w185" + poster;
    }
}
