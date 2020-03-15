package com.aas.moviecatalog.ui.favorite.favtvshow

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.aas.moviecatalog.MainActivity

import com.aas.moviecatalog.R
import com.aas.moviecatalog.ui.detail.DetailActivity
import com.aas.moviecatalog.ui.detail.DetailActivity.Companion.INTENT_RESULT_CODE
import com.aas.moviecatalog.ui.favorite.FavoriteFragment.Companion.INTENT_REQUEST_CODE
import com.aas.moviecatalog.repository.model.TvShow
import com.aas.moviecatalog.ui.tvshow.TvShowAdapter
import kotlinx.android.synthetic.main.fragment_favorite_tv_show.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteTvShowFragment : Fragment(), FavoriteTvShowInterface {

    private var listTvShow = mutableListOf<TvShow>()
    private lateinit var viewModel: FavoriteTvShowViewModel
    private lateinit var mPresenter: FavoriteTvShowPresenter
    private lateinit var mAdapter: TvShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_tv_show, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(FavoriteTvShowViewModel::class.java)
        viewModel.getTv().observe(this, getFavTv)

        mPresenter = FavoriteTvShowPresenter(this)

        rvFavTv.addItemDecoration(
            DividerItemDecoration(
                rvFavTv.context,
                DividerItemDecoration.VERTICAL
            )
        )
        rvFavTv.layoutManager = LinearLayoutManager(context)

        mAdapter = TvShowAdapter(requireContext(), listTvShow) {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(MainActivity.DATA_EXTRA, it.id)
            intent.putExtra(MainActivity.TYPE, MainActivity.TV)
            startActivityForResult(intent, INTENT_REQUEST_CODE)
        }
        rvFavTv.adapter = mAdapter

        if (savedInstanceState == null){
            mPresenter.getFavoriteTvShow(requireContext())
        }
        else {
            hideLoading()
        }
        super.onActivityCreated(savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == INTENT_REQUEST_CODE){
            if (resultCode == INTENT_RESULT_CODE){
                mPresenter.getFavoriteTvShow(requireContext())
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun showLoading() {
        pbFavTv.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pbFavTv.visibility = View.GONE
    }

    override fun favoriteTvShow(tv: List<TvShow>) {
        viewModel.setTv(tv)
        if (tv.isEmpty()) {
            tvNoData.visibility = View.VISIBLE
        }
    }

    private val getFavTv = Observer<List<TvShow>> {
        if (it != null) {
            mAdapter.setTv(it)
        }
    }
}
