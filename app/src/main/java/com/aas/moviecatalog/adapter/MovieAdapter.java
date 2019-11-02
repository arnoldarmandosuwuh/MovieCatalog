package com.aas.moviecatalog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aas.moviecatalog.R;
import com.aas.moviecatalog.api.TheMovieDBAPI;
import com.aas.moviecatalog.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private Context context;
    private ArrayList<Movie> listMovie;
    private MovieListener movieListener;

    public void setListMovie(ArrayList<Movie> listMovie) {
        this.listMovie = listMovie;
        notifyDataSetChanged();
    }

    public MovieAdapter(Context context, MovieListener movieListener) {
        this.context = context;
        this.movieListener = movieListener;
        listMovie = new ArrayList<>();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new MovieViewHolder(view, movieListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(listMovie.get(position));

    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvJudul;
        ImageView ivGambar;
        MovieListener movieListener;

        MovieViewHolder(@NonNull View itemView, MovieListener movieListener) {
            super(itemView);

            tvJudul = itemView.findViewById(R.id.tvJudul);
            ivGambar = itemView.findViewById(R.id.ivGambar);
            this.movieListener = movieListener;

            itemView.setOnClickListener(this);
        }

        void bind(Movie movie){
            tvJudul.setText(movie.getTitle());
            Picasso.get().load(TheMovieDBAPI.getPoster(movie.getPosterPath())).into(ivGambar);

        }

        @Override
        public void onClick(View view) {
            movieListener.onMovieItemClick(getAdapterPosition());
        }
    }
    public interface MovieListener{
        void onMovieItemClick(int position);
    }
}
