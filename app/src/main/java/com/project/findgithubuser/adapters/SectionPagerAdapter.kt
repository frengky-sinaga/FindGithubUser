package com.project.findgithubuser.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.project.findgithubuser.R
import com.project.findgithubuser.views.fragments.detail.FollowersFragment
import com.project.findgithubuser.views.fragments.detail.FollowingFragment

class SectionPagerAdapter(private val context: Context, fm: FragmentManager?) :
    FragmentPagerAdapter(fm!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment =
                FollowersFragment()
            1 -> fragment =
                FollowingFragment()
        }
        return fragment!!
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return context.getString(R.string.tv_detailFollowers)
            1 -> return context.getString(R.string.tv_detailFollowing)
        }
        return null
    }
}