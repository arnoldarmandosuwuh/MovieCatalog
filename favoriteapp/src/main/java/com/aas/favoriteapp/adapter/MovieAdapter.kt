package com.aas.favoriteapp.adapter

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.aas.favoriteapp.MainActivity
import com.aas.favoriteapp.R
import com.aas.favoriteapp.api.ApiRepository
import com.aas.favoriteapp.model.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item.view.*

class MovieAdapter(
    private val context: Context,
    private val movies: MutableList<Movie>
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    fun setMovies(movie: List<Movie>) {
        movies.clear()
        movies.addAll(movie)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(context, movies[position])
    }

    override fun getItemCount(): Int = movies.size

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bindItem(
            context: Context, movie: Movie
        ) {
            itemView.tvJudul.text = movie.title
            itemView.tvOverview.text = movie.overview
            Picasso.get().load(ApiRepository.BASE_IMAGE_URL + movie.poster_path)
                .into(itemView.ivGambar)
            containerView.setOnClickListener {
                Toast.makeText(context, movie.title, Toast.LENGTH_LONG).show()
            }
        }
    }
}