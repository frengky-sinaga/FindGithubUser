package com.project.findgithubuser.views.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.preference.PreferenceFragmentCompat
import com.project.findgithubuser.broadcast.AlarmReceiver
import com.project.findgithubuser.R

class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        alarmReceiver = AlarmReceiver()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val defaultView = super.onCreateView(inflater, container, savedInstanceState)
        val newView = inflater.inflate(R.layout.prefs_layout, container, false) as ViewGroup
        newView.addView(defaultView)
        return newView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar = view.findViewById(R.id.toolbar_prefs)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
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

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        when (key) {
            "alarm" -> {
                val boolean = sharedPreferences.getBoolean("alarm", true)
                if (boolean) {
                    alarmReceiver.setRepeatingAlarm(requireContext().applicationContext)
                } else {
                    alarmReceiver.cancelAlarm(requireContext().applicationContext)
                }
            }
        }
    }
}