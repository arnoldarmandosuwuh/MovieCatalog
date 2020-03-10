package com.aas.moviecatalog.tvshow

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aas.moviecatalog.R
import com.aas.moviecatalog.api.ApiRepository
import com.aas.moviecatalog.model.TvShow
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item.view.*

class TvShowAdapter(
    private val context: Context,
    private val tvShows: MutableList<TvShow>,
    private val onClickListener: (TvShow) -> Unit
) : RecyclerView.Adapter<TvShowAdapter.ViewHolder>() {

    fun setTv(tv: List<TvShow>) {
        tvShows.clear()
        tvShows.addAll(tv)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
    )

    override fun getItemCount(): Int = tvShows.size

    override fun onBindViewHolder(holder: TvShowAdapter.ViewHolder, position: Int) {
        holder.bind(context, tvShows[position], onClickListener)
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(context: Context, tv: TvShow, onClickListener: (TvShow) -> Unit) {
            itemView.tvJudul.text = tv.name
            itemView.tvOverview.text = tv.overview
            Picasso
                .get()
                .load(ApiRepository.BASE_IMAGE_URL + tv.poster_path)
                .into(itemView.ivGambar)
            containerView.setOnClickListener { onClickListener(tv) }
        }
    }
}