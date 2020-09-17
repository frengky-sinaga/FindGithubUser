package com.project.findgithubuser.views.fragments.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.findgithubuser.R
import com.project.findgithubuser.adapters.UserAdapter
import com.project.findgithubuser.viewmodels.FollowViewModel
import com.project.findgithubuser.viewmodels.SharedViewModel

class FollowersFragment : Fragment() {

    private lateinit var rvUsers: RecyclerView
    private lateinit var adapter: UserAdapter
    private lateinit var followViewModel: FollowViewModel
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvUsers = view.findViewById(R.id.rv_followersFragment)
        init()
        showRecycleList()
        followViewModel()
    }

    private fun init() {
        followViewModel = ViewModelProvider(requireActivity()).get(FollowViewModel::class.java)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        sharedViewModel.getDataItem().observe(viewLifecycleOwner, { data ->
            followViewModel.setRequestApi(data.login.toString(), true)
        })
    }

    private fun showRecycleList() {
        adapter = UserAdapter(ArrayList())
        rvUsers.setHasFixedSize(true)
        rvUsers.layoutManager = LinearLayoutManager(context)
        rvUsers.adapter = adapter
    }

    private fun followViewModel() {
        followViewModel.getDataFollowers().observe(viewLifecycleOwner, { dataItem ->
            if (dataItem != null) {
                adapter.clearUser()
                adapter.addNewData(dataItem)
            }
        })
    }
}