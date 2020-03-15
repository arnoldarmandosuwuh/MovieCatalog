package com.aas.moviecatalog

import android.content.ComponentName
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.aas.moviecatalog.ui.favorite.FavoriteFragment
import com.aas.moviecatalog.ui.movie.MovieFragment
import com.aas.moviecatalog.ui.setting.SettingsActivity
import com.aas.moviecatalog.ui.tvshow.TvShowFragment
import com.aas.moviecatalog.util.App
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private var app: App = App()

    companion object {
        const val DATA_EXTRA = "data"
        const val MOVIE = "movie"
        const val TV = "tv"
        const val TYPE = "type"
        const val INSTANCE = "instance"
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val dark = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean(resources.getString(R.string.dark_theme), false)

            when (dark) {
                true -> app.theme(AppCompatDelegate.MODE_NIGHT_YES)
                else -> app.theme(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

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
                R.id.nav_fav_app -> {
                    val intent = Intent()
                    intent.component =
                        ComponentName("com.aas.favoriteapp", "com.aas.favoriteapp.MainActivity")
                    startActivity(intent)
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
            R.id.action_fav_app -> {
                val intent = Intent()
                intent.component =
                    ComponentName("com.aas.favoriteapp", "com.aas.favoriteapp.MainActivity")
                startActivity(intent)
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
