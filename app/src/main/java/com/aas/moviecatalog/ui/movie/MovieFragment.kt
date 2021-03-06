package com.aas.moviecatalog.ui.movie


import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aas.moviecatalog.MainActivity
import com.aas.moviecatalog.R
import com.aas.moviecatalog.repository.api.ApiRepository
import com.aas.moviecatalog.ui.detail.DetailActivity
import com.aas.moviecatalog.repository.model.Movie
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

        setHasOptionsMenu(true)

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val menuItem = menu.findItem(R.id.action_search)
        if (menuItem != null) {
            val searchView: SearchView = menuItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    mPresenter.searchMovie(query)
                    return false
                }

                override fun onQueryTextChange(query: String): Boolean {
                    return false
                }
            })
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    private val getMovie = Observer<MovieResponseModel> {
        if (it != null) {
            mAdapter.setMovie(it.results)
        }
    }
}
