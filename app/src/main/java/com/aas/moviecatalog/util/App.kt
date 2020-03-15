package com.aas.moviecatalog.util

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class App {

    fun theme(themeMode: Int) {
        AppCompatDelegate.setDefaultNightMode(themeMode)
    }

}