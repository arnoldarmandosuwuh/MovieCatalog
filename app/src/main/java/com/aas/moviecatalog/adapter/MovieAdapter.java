package com.aas.moviecatalog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.aas.moviecatalog.R;
import com.aas.moviecatalog.model.Movie;
import java.util.ArrayList;

public class MovieAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Movie> movies;

    public MovieAdapter(Context context) {
        this.context = context;
        movies = new ArrayList<>();
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Movie getItem(int i) {
        return movies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
        }

        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.bind(getItem(i));
        return view;
    }
    private class ViewHolder {
        private TextView tvJudul;
        private ImageView ivPoster;
        ViewHolder(View view){
            tvJudul = view.findViewById(R.id.tvJudul);
            ivPoster = view.findViewById(R.id.ivPoster);
        }

        void bind (Movie movie){
            tvJudul.setText(movie.getJudul());
            ivPoster.setImageResource(movie.getPoster());
        }
    }
}
