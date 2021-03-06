package com.aas.moviecatalog.ui.favorite.favmovie


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
import com.aas.moviecatalog.repository.model.Movie
import com.aas.moviecatalog.ui.movie.MovieAdapter
import kotlinx.android.synthetic.main.fragment_favorite_movie.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteMovieFragment : Fragment(), FavoriteMovieInterface {

    private var listMovies = mutableListOf<Movie>()
    private lateinit var viewModel: FavoriteMovieViewModel
    private lateinit var mPresenter: FavoriteMoviePresenter
    private lateinit var mAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(FavoriteMovieViewModel::class.java)
        viewModel.getMovie().observe(this, getFavMovie)

        mPresenter = FavoriteMoviePresenter(this)

        rvFavMovie.addItemDecoration(
            DividerItemDecoration(
                rvFavMovie.context,
                DividerItemDecoration.VERTICAL
            )
        )
        rvFavMovie.layoutManager = LinearLayoutManager(context)

        mAdapter = MovieAdapter(requireContext(), listMovies) {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(MainActivity.DATA_EXTRA, it.id)
            intent.putExtra(MainActivity.TYPE, MainActivity.MOVIE)
            startActivityForResult(intent, INTENT_REQUEST_CODE)
        }
        rvFavMovie.adapter = mAdapter

        if (savedInstanceState == null){
            mPresenter.getFavoriteMovie(requireContext())
        }
        else {
            hideLoading()
        }
        super.onActivityCreated(savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == INTENT_REQUEST_CODE){
            if (resultCode == INTENT_RESULT_CODE){
                mPresenter.getFavoriteMovie(requireContext())
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun showLoading() {
        pbFavMovie.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pbFavMovie.visibility = View.GONE
    }

    override fun favoriteMovie(movie: List<Movie>) {
        viewModel.setMovie(movie)
        if (movie.isEmpty()) {
                tvNoData.visibility = View.VISIBLE
        }
    }

    private val getFavMovie = Observer<List<Movie>> {
        if (it != null) {
            mAdapter.setMovie(it)
        }
    }
}
