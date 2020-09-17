package com.project.findgithubuser.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.findgithubuser.R
import com.project.findgithubuser.adapters.FavoritesAdapter
import com.project.findgithubuser.entity.FavoriteEntity
import com.project.findgithubuser.viewmodels.FavoriteViewModel
import com.project.findgithubuser.models.DataItem
import com.project.findgithubuser.utils.OnItemClickCallback
import com.project.findgithubuser.utils.log
import com.project.findgithubuser.utils.toast
import com.project.findgithubuser.viewmodels.SharedViewModel
import com.project.findgithubuser.views.fragments.detail.DetailFragment

class FavoritesFragment : Fragment() {

    private lateinit var rvFavorites: RecyclerView
    private lateinit var toolbar: Toolbar
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var adapter: FavoritesAdapter
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        showRecycleList()
        viewModel()
    }

    private fun initView(view: View) {
        setHasOptionsMenu(true)
        rvFavorites = view.findViewById(R.id.rv_favorites)
        toolbar = view.findViewById(R.id.toolbar_favorite)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        toolbar.inflateMenu(R.menu.favorite_menu)
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_delete -> {
                    toast(
                        context,
                        requireContext().applicationContext.getString(R.string.deleteAllFavorites)
                    )
                    log("delete")
                    favoriteViewModel.deleteAllData()
                }
                R.id.opt_setting -> {
                    val mSettingsFragment = SettingsFragment()
                    val mFragmentManager = activity?.supportFragmentManager
                    mFragmentManager?.beginTransaction()?.apply {
                        replace(
                            R.id.frame_container,
                            mSettingsFragment,
                            SettingsFragment::class.java.simpleName
                        )
                        addToBackStack(null)
                        commit()
                    }
                }
            }
            true
        }
    }

    private fun viewModel() {
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        favoriteViewModel = ViewModelProvider(requireActivity()).get(FavoriteViewModel::class.java)
        favoriteViewModel.readAllData.observe(viewLifecycleOwner, { data ->
            if (data != null) {
                adapter.clearUser()
                adapter.addNewData(data)
            }
        })
    }

    private fun showRecycleList() {
        adapter = FavoritesAdapter(ArrayList())
        rvFavorites.setHasFixedSize(true)
        rvFavorites.layoutManager = LinearLayoutManager(context)
        rvFavorites.adapter = adapter
        adapter.setOnItemClickCallback(object : OnItemClickCallback {
            override fun onItemClicked(dataItem: DataItem) {}
            override fun onItemClicked(favorite: FavoriteEntity) {
                moveToDetail(favorite)
            }

            override fun onDeleteClicked(favorite: FavoriteEntity) {
                val delete = requireContext().applicationContext.getString(R.string.deleteFavorite)
                toast(context, "${favorite.username} $delete")
                favoriteViewModel.deleteFavorite(favorite.username)
            }
        })
    }

    private fun moveToDetail(favorite: FavoriteEntity) {
        val mDetailFragment = DetailFragment()
        val mFragmentManager = activity?.supportFragmentManager
        mFragmentManager?.beginTransaction()?.apply {
            replace(R.id.frame_container, mDetailFragment, DetailFragment::class.java.simpleName)
            addToBackStack(null)
            commit()
        }
        val dataItem = DataItem(favorite.username, favorite.url, "", "", "")
        sharedViewModel.setDataItem(dataItem)
    }
}