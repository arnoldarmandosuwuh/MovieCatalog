package com.aas.moviecatalog.ui.setting

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.aas.moviecatalog.R
import com.aas.moviecatalog.util.App

class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var dailyReminderSwitch: SwitchPreference
    private lateinit var releaseTodaySwitch: SwitchPreference
    private lateinit var darkMode: SwitchPreference
    private lateinit var darkPref: PreferenceCategory
    private var app: App = App()
    private var keyDaily = ""
    private var keyReleaseToday = ""
    private var keyDark = ""

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.root_preferences)
        init()
        setSettings()
    }

    private fun init() {
        darkPref =
            findPreference<PreferenceCategory>(resources.getString(R.string.setting_key)) as PreferenceCategory
        dailyReminderSwitch =
            findPreference<SwitchPreference>(resources.getString(R.string.daily_key)) as SwitchPreference
        releaseTodaySwitch =
            findPreference<SwitchPreference>(resources.getString(R.string.release_today_key)) as SwitchPreference
        darkMode =
            findPreference<SwitchPreference>(resources.getString(R.string.dark_theme)) as SwitchPreference

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            darkPref.removePreference(darkMode)
        }

        keyDaily = resources.getString(R.string.daily_key)
        keyReleaseToday = resources.getString(R.string.release_today_key)
        keyDark = resources.getString(R.string.dark_theme)
    }

    private fun setSettings() {
        val sPreference = preferenceManager.sharedPreferences
        dailyReminderSwitch.isChecked = sPreference.getBoolean(keyDaily, false)
        releaseTodaySwitch.isChecked = sPreference.getBoolean(keyReleaseToday, false)
        darkMode.isChecked = sPreference.getBoolean(keyDark, false)
    }

    override fun onSharedPreferenceChanged(
        sharedPreferences: SharedPreferences,
        key: String?
    ) {
        when (key) {
            resources.getString(R.string.daily_key) -> {
                dailyReminderSwitch.isChecked = sharedPreferences.getBoolean(keyDaily, false)
                setReminder(keyDaily)
            }
            resources.getString(R.string.release_today_key) -> {
                releaseTodaySwitch.isChecked = sharedPreferences.getBoolean(keyReleaseToday, false)
                setReminder(keyReleaseToday)
            }
            resources.getString(R.string.dark_theme) -> {
                darkMode.isChecked = sharedPreferences.getBoolean(keyDark, false)
                setDarkMode(keyDark)
            }
        }
    }

    private fun setDarkMode(key: String?) {
        val isActive = preferenceManager.sharedPreferences.getBoolean(key, false)
        when (isActive) {
            true -> app.theme(AppCompatDelegate.MODE_NIGHT_YES)
            else -> app.theme(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun setReminder(key: String?) {
        val isActive = preferenceManager.sharedPreferences.getBoolean(key, false)
        val reminderReceiver = ReminderReceiver()
        if (isActive) {
            if (key == keyDaily) {
                reminderReceiver.setRepeatingAlarm(activity, ReminderReceiver.DAILY)
            } else if (key == keyReleaseToday) {
                reminderReceiver.setRepeatingAlarm(activity, ReminderReceiver.RELEASE_DATE)
            }
        } else {
            if (key == keyDaily) {
                reminderReceiver.stopRepeatingAlarm(activity, ReminderReceiver.DAILY)
            } else {
                reminderReceiver.stopRepeatingAlarm(activity, ReminderReceiver.RELEASE_DATE)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
}