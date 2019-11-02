package com.aas.moviecatalog.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aas.moviecatalog.R;
import com.aas.moviecatalog.adapter.TvAdapter;
import com.aas.moviecatalog.detail.DetailTvActivity;
import com.aas.moviecatalog.model.TvShow;
import com.aas.moviecatalog.viewmodel.ListTvViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment implements TvAdapter.TvListener {

    private TvAdapter tvAdapter;
    private RecyclerView rvTv;
    private ArrayList<TvShow> listTv;
    private ProgressBar progressBar;
    private ListTvViewModel tvViewModel;

    private void showLoading(boolean loading) {
        if (loading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvTv = view.findViewById(R.id.rvTv);
        progressBar = view.findViewById(R.id.pbTV);
        tvAdapter = new TvAdapter(getContext(), this);

        tvViewModel = ViewModelProviders.of(this)
                .get(ListTvViewModel.class);
        tvViewModel.getListTv().observe(this, getListTv);

//        rvTv.setHasFixedSize(true);
        rvTv.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTv.setAdapter(tvAdapter);

        showLoading(true);
        tvViewModel.setListTv();
    }

    private Observer<ArrayList<TvShow>> getListTv = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(ArrayList<TvShow> tvShow) {
            if (tvShow != null){
                listTv = tvShow;
                tvAdapter.setListTv(tvShow);
            }
            showLoading(false);
        }
    };

    @Override
    public void onTvItemClick(int position) {
        Intent intent = new Intent(getContext(), DetailTvActivity.class);
        intent.putExtra(DetailTvActivity.EXTRA_MOVIE, listTv.get(position));
        startActivity(intent);
    }
}
