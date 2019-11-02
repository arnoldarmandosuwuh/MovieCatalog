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
import com.aas.moviecatalog.model.TvShow;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.TvViewHolder> {
    private Context context;
    private ArrayList<TvShow> listTv;
    private TvListener tvListener;

    public void setListTv(ArrayList<TvShow> listTv) {
        this.listTv = listTv;
        notifyDataSetChanged();
    }

    public TvAdapter(Context context, TvListener tvListener) {
        this.context = context;
        this.tvListener = tvListener;
        listTv = new ArrayList<>();
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new TvAdapter.TvViewHolder(view, tvListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TvAdapter.TvViewHolder holder, int position) {
        holder.bind(listTv.get(position));
    }

    @Override
    public int getItemCount() {
        return listTv.size();
    }

    public class TvViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvJudul;
        ImageView ivGambar;
        TvListener tvListener;

        TvViewHolder(@NonNull View itemView, TvListener tvListener) {
            super(itemView);

            tvJudul = itemView.findViewById(R.id.tvJudul);
            ivGambar = itemView.findViewById(R.id.ivGambar);
            this.tvListener = tvListener;

            itemView.setOnClickListener(this);
        }

        void bind(TvShow tvShow) {
            tvJudul.setText(tvShow.getName());
            Picasso.get().load(TheMovieDBAPI.getPoster(tvShow.getPosterPath())).into(ivGambar);

        }

        @Override
        public void onClick(View view) {
            tvListener.onTvItemClick(getAdapterPosition());
        }
    }

    public interface TvListener {
        void onTvItemClick(int position);
    }
}
