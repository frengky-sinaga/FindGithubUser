package com.project.findgithubuser.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.findgithubuser.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch(Dispatchers.Main) {
            delay(3000)

            val mFragmentManager = activity?.supportFragmentManager
            val mHomeFragment = HomeFragment()
            mFragmentManager?.beginTransaction()?.apply {
                replace(
                    R.id.frame_container,
                    mHomeFragment,
                    HomeFragment::class.java.simpleName
                )
                commit()
            }
        }
    }
}