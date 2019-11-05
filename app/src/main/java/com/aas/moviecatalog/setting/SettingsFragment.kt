package com.aas.moviecatalog.setting

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference

import com.aas.moviecatalog.R

class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var dailyReminderSwitch: SwitchPreference
    private lateinit var releaseTodaySwitch: SwitchPreference
    private var keyDaily = ""
    private var keyReleaseToday = ""

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.root_preferences)
        init()
        setSettings()
    }

    private fun init(){
        dailyReminderSwitch = findPreference<SwitchPreference>(resources.getString(R.string.daily_key)) as SwitchPreference
        releaseTodaySwitch = findPreference<SwitchPreference>(resources.getString(R.string.release_today_key)) as SwitchPreference

        keyDaily = resources.getString(R.string.daily_key)
        keyReleaseToday = resources.getString(R.string.release_today_key)
    }

    private fun setSettings(){
        val sPreference = preferenceManager.sharedPreferences
        dailyReminderSwitch.isChecked = sPreference.getBoolean(keyDaily, false)
        releaseTodaySwitch.isChecked = sPreference.getBoolean(keyReleaseToday, false)
    }

    override fun onSharedPreferenceChanged(
        sharedPreferences: SharedPreferences,
        key: String?
    ) {
        when(key){
            resources.getString(R.string.daily_key)->{
                dailyReminderSwitch.isChecked = sharedPreferences.getBoolean(keyDaily, false)
                setReminder(keyDaily)
            }
            resources.getString(R.string.release_today_key)->{
                releaseTodaySwitch.isChecked = sharedPreferences.getBoolean(keyReleaseToday, false)
                setReminder(keyReleaseToday)
            }
        }
    }
    private fun setReminder(key: String?){
        val isActive = preferenceManager.sharedPreferences.getBoolean(key, false)
        val reminderReceiver = ReminderReceiver()
        if(isActive){
            if(key == keyDaily){
                reminderReceiver.setRepeatingAlarm(activity, ReminderReceiver.DAILY)
            } else if (key==keyReleaseToday){
                reminderReceiver.setRepeatingAlarm(activity, ReminderReceiver.RELEASE_DATE)
            }
        } else {
            if (key == keyDaily){
                reminderReceiver.stopRepeatingAlarm(activity, ReminderReceiver.DAILY)
            }
            else {
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