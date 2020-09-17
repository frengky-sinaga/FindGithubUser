package com.project.consumerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mFragmentManager = supportFragmentManager
        val mSplashFragment = SplashFragment()
        val fragment = mFragmentManager.findFragmentByTag(SplashFragment::class.java.simpleName)

        if (fragment !is SplashFragment) {
            mFragmentManager.beginTransaction()
                .add(R.id.frame_layout, mSplashFragment, SplashFragment::class.java.simpleName)
                .commit()
        }
    }
}
