package com.aas.moviecatalog.movie

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aas.moviecatalog.R
import com.aas.moviecatalog.api.ApiRepository
import com.aas.moviecatalog.model.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item.view.*

class MovieAdapter(
    private val context: Context,
    private val movies: MutableList<Movie>,
    private val onClickListener: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    fun setMovie(movie: List<Movie>) {
        movies.clear()
        movies.addAll(movie)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
    )

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, movies[position], onClickListener)
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(context: Context, movie: Movie, onClickListener: (Movie) -> Unit) {
            itemView.tvJudul.text = movie.title
            itemView.tvOverview.text = movie.overview
            Picasso
                .get()
                .load(ApiRepository.BASE_IMAGE_URL + movie.poster_path)
                .into(itemView.ivGambar)
            containerView.setOnClickListener { onClickListener(movie) }
        }
    }
}