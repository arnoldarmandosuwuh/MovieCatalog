package com.aas.moviecatalog.tvshow


import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.aas.moviecatalog.MainActivity

import com.aas.moviecatalog.R
import com.aas.moviecatalog.api.ApiRepository
import com.aas.moviecatalog.detail.DetailActivity
import com.aas.moviecatalog.model.TvShow
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.fragment_tv_show.*

/**
 * A simple [Fragment] subclass.
 */
class TvShowFragment : Fragment(), TvShowInterface {

    private lateinit var mPresenter: TvShowPresenter
    private lateinit var mAdapter: TvShowAdapter
    private var tvShows = mutableListOf<TvShow>()
    private lateinit var viewModel: TvShowViewModel

    override fun tvData(tv: TvShowResponseModel) {
        viewModel.setMovie(tv)
    }

    override fun showLoading() {
        pbTV.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pbTV.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)

        viewModel = ViewModelProviders.of(this).get(TvShowViewModel::class.java)
        viewModel.getMovie().observe(this, getTvShow)

        val service = ApiRepository.create()
        mPresenter = TvShowPresenter(this, service)

        rvTv.addItemDecoration(
            DividerItemDecoration(
                rvTv.context,
                DividerItemDecoration.VERTICAL
            )
        )
        rvTv.layoutManager = LinearLayoutManager(requireContext())
        mAdapter = TvShowAdapter(requireContext(), tvShows ) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(MainActivity.DATA_EXTRA, it.id)
            intent.putExtra(MainActivity.TYPE, MainActivity.TV)
            startActivity(intent)
        }
        rvTv.adapter = mAdapter
        if (savedInstanceState == null) {
            mPresenter.loadTvShow()
        } else {
            hideLoading()
        }
        super.onActivityCreated(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(MainActivity.INSTANCE, TvShowFragment::class.java.simpleName)
        super.onSaveInstanceState(outState)
    }

    private val getTvShow = Observer<TvShowResponseModel> {
        if (it != null) {
            mAdapter.setTv(it.results)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val menuItem = menu.findItem(R.id.action_search)
        if(menuItem != null){
            val searchView: SearchView = menuItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String): Boolean {
                    mPresenter.searchTvShow(query)
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
        }
        super.onCreateOptionsMenu(menu, inflater)
    }
}
