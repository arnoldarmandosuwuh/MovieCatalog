package com.aas.moviecatalog.movie


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
import androidx.recyclerview.widget.RecyclerView
import com.aas.moviecatalog.MainActivity
import com.aas.moviecatalog.R
import com.aas.moviecatalog.api.ApiRepository
import com.aas.moviecatalog.detail.DetailActivity
import com.aas.moviecatalog.model.Movie
import org.jetbrains.anko.*
import kotlinx.android.synthetic.main.fragment_movie.*

/**
 * A simple [Fragment] subclass.
 */
class MovieFragment : Fragment(), MovieInterface {
    private lateinit var mPresenter: MoviePresenter
    private lateinit var mAdapter: MovieAdapter
    private var movies = mutableListOf<Movie>()
    private lateinit var viewModel: MovieViewModel
    private lateinit var rvMovie: RecyclerView

    override fun showLoading() {
        pbMovie.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pbMovie.visibility = View.GONE
    }

    override fun movieData(movie: MovieResponseModel) {
        viewModel.setMovie(movie)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvMovie = view.findViewById(R.id.rvMovie)
        super.onViewCreated(view, savedInstanceState)
    }

    @Suppress("DEPRECATION")
    override fun onActivityCreated(savedInstanceState: Bundle?) {

        viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        viewModel.getMovie().observe(this, getMovie)

        val service = ApiRepository.create()
        mPresenter = MoviePresenter(this, service)

        rvMovie.addItemDecoration(
            DividerItemDecoration(
                rvMovie.context,
                DividerItemDecoration.VERTICAL
            )
        )
        rvMovie.layoutManager = LinearLayoutManager(requireContext())
        mAdapter = MovieAdapter(requireContext(), movies) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(MainActivity.DATA_EXTRA, it.id)
            intent.putExtra(MainActivity.TYPE, MainActivity.MOVIE)
            startActivity(intent)
        }
        rvMovie.adapter = mAdapter
        if (savedInstanceState == null) {
            mPresenter.loadMovie()
        } else {
            hideLoading()
        }

        super.onActivityCreated(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(MainActivity.INSTANCE, MovieFragment::class.java.simpleName)
        super.onSaveInstanceState(outState)
    }

    private val getMovie = Observer<MovieResponseModel> {
        if (it != null) {
            mAdapter.setMovie(it.results)
        }
    }
}
