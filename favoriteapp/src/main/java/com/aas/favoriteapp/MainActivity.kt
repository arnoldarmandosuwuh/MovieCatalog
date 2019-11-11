package com.aas.favoriteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aas.favoriteapp.movie.MovieFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val DATA_EXTRA = "data"
        const val MOVIE = "movie"
        const val TV = "tv"
        const val TYPE = "type"
        const val INSTANCE = "instance"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nav_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_movie -> {
                    val fragment = MovieFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.flContent, fragment, MovieFragment::class.java.simpleName)
                        .commit()

                    supportActionBar?.setTitle(R.string.title_movie)
                }
                R.id.nav_tv -> {
//                    val fragment = TvShowFragment()
//                    supportFragmentManager
//                        .beginTransaction()
//                        .replace(R.id.flContent, fragment, TvShowFragment::class.java.simpleName)
//                        .commit()

                    supportActionBar?.setTitle(R.string.title_tv)
                }
            }
            true
        }
        if (savedInstanceState == null) {
            nav_view.selectedItemId = R.id.nav_movie
        } else {
            when (savedInstanceState.getString(INSTANCE)) {
                MovieFragment::class.java.simpleName -> {
                    nav_view.selectedItemId = R.id.nav_movie
                }
//                TvShowFragment::class.java.simpleName -> {
//                    nav_view.selectedItemId = R.id.nav_tv
//                }
            }
        }
    }
    override fun onBackPressed() {
        moveTaskToBack(true)
        super.onBackPressed()
    }
}
