package com.project.findgithubuser.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import com.project.findgithubuser.broadcast.AlarmReceiver
import com.project.findgithubuser.R
import com.project.findgithubuser.views.fragments.SplashFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setAlarm()
        setFragment()
    }

    private fun setAlarm(){
        val alarmReceiver = AlarmReceiver()
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val alarm = sharedPreferences.getBoolean("alarm", true)
        if (alarm) {
            alarmReceiver.setRepeatingAlarm(this)
        }
    }
    private fun setFragment(){
        val mFragmentManager = supportFragmentManager
        val mSplashFragment = SplashFragment()
        val fragment = mFragmentManager.findFragmentByTag(SplashFragment::class.java.simpleName)

        if (fragment !is SplashFragment) {
            mFragmentManager.beginTransaction()
                .add(R.id.frame_container, mSplashFragment, SplashFragment::class.java.simpleName)
                .commit()
        }
    }
}