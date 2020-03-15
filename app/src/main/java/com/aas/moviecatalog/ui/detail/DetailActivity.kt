package com.aas.moviecatalog.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.aas.moviecatalog.MainActivity
import com.aas.moviecatalog.R
import com.aas.moviecatalog.repository.api.ApiRepository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.toast
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DetailActivity : AppCompatActivity(), DetailInterface {

    private lateinit var mPresenter: DetailPresenter
    private lateinit var view: View
    private var type: String = "type"
    private var id: String = "0"
    private var posterPath: String = "poster_path"
    private var title: String = "title"
    private var overview: String = "overview"
    private var releaseDate: String = "release_date"
    private var backdropPath: String = "backdrop_path"
    private var menuItem: Menu? = null
    private var isFav: Boolean = false

    companion object {
        const val INTENT_RESULT_CODE = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        id = intent.getStringExtra(MainActivity.DATA_EXTRA)
        type = intent.getStringExtra(MainActivity.TYPE)

        val service = ApiRepository.create()
        mPresenter = DetailPresenter(this, service)

        if (type == MainActivity.MOVIE) {
            mPresenter.loadMovieDetail(id)
        } else if (type == MainActivity.TV) {
            mPresenter.loadTvShowDetail(id)
        }

        isFav = mPresenter.setFavorite(this, id)
    }

    override fun showLoading() {
        pbDetail.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pbDetail.visibility = View.GONE
    }

    override fun showMovie(movie: DetailMovieModel) {
        title = movie.title
        posterPath = movie.poster_path
        overview = movie.overview
        releaseDate = movie.release_date
        backdropPath = movie.backdrop_path

        supportActionBar?.title = title

        var date = LocalDate.parse(releaseDate)
        var formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy")
        var formattedDate = date.format(formatter)


        tvJudulFilm.text = title
        tvDate.text = formattedDate
        tvOverview.text = overview

        Picasso
            .get()
            .load(ApiRepository.BASE_IMAGE_URL + posterPath)
            .into(ivPosterFilm)

        Picasso
            .get()
            .load(ApiRepository.BASE_IMAGE_URL+backdropPath)
            .into(ivBackdrop)

    }

    override fun showTvShow(tv: DetailTvShowModel) {
        title = tv.name
        posterPath = tv.poster_path
        overview = tv.overview
        releaseDate = tv.first_air_date
        backdropPath = tv.backdrop_path

        supportActionBar?.title = title

        var date = LocalDate.parse(releaseDate)
        var formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy")
        var formattedDate = date.format(formatter)


        tvJudulFilm.text = title
        tvDate.text = formattedDate
        tvOverview.text = overview

        Picasso
            .get()
            .load(ApiRepository.BASE_IMAGE_URL + posterPath)
            .into(ivPosterFilm)

        Picasso
            .get()
            .load(ApiRepository.BASE_IMAGE_URL+backdropPath)
            .into(ivBackdrop)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        menuItem = menu
        setFavorite()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                finish()
                true
            }
            R.id.fav -> {
                if (isFav) {
                    mPresenter.removeFavorite(this, id)
                    isFav = !isFav
                    setFavorite()
                } else {
                    if (id != "0" && title != "title" && type != "type" && posterPath != "poster_path" && overview != "overview" && releaseDate != "release_date") {
                        mPresenter.addFavorite(
                            this,
                            id,
                            type,
                            title,
                            posterPath,
                            overview,
                            releaseDate
                        )
                        isFav = !isFav
                         setFavorite()
                    } else {
                        toast("Not Available")
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }

    private fun setFavorite() {
        if (isFav)
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_black_24dp)
        else
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_black_24dp)
    }

    override fun onBackPressed() {
        setResult(INTENT_RESULT_CODE, Intent())
        super.onBackPressed()
    }
}
