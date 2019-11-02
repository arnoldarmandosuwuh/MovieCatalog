package com.aas.moviecatalog.util;

import com.aas.moviecatalog.model.TvShow;

public interface DetailTvShowAsyncTaskCallback {
    void onPreExecute();
    void onPostExecute(TvShow tvShow);
}
