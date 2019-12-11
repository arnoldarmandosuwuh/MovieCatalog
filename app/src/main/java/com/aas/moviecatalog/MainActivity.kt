package com.aas.moviecatalog

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.aas.moviecatalog.favorite.FavoriteFragment
import com.aas.moviecatalog.movie.MovieFragment
import com.aas.moviecatalog.setting.SettingsActivity
import com.aas.moviecatalog.tvshow.TvShowFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import java.util.*

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
                    val fragment = TvShowFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.flContent, fragment, TvShowFragment::class.java.simpleName)
                        .commit()

                    supportActionBar?.setTitle(R.string.title_tv)
                }
                R.id.nav_fav -> {
                    val fragment = FavoriteFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.flContent, fragment, FavoriteFragment::class.java.simpleName)
                        .commit()

                    supportActionBar?.setTitle(R.string.favorite)
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
                TvShowFragment::class.java.simpleName -> {
                    nav_view.selectedItemId = R.id.nav_tv
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_change_en -> {
                restartInLocale(Locale.ENGLISH)
            }
            R.id.action_change_in -> {
                restartInLocale(Locale.forLanguageTag("in-ID"))
            }
            R.id.notification_menu -> {
                startActivity<SettingsActivity>()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @Suppress("DEPRECATION")
    private fun restartInLocale(locale: Locale) {
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        val resources = resources
        resources.updateConfiguration(config, resources.displayMetrics)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
        super.onBackPressed()
    }
}
