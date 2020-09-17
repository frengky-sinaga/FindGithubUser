package com.project.findgithubuser.views.fragments

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.project.findgithubuser.R
import com.project.findgithubuser.adapters.UserAdapter
import com.project.findgithubuser.entity.FavoriteEntity
import com.project.findgithubuser.models.DataItem
import com.project.findgithubuser.utils.OnItemClickCallback
import com.project.findgithubuser.utils.toast
import com.project.findgithubuser.viewmodels.HomeViewModel
import com.project.findgithubuser.viewmodels.SharedViewModel
import com.project.findgithubuser.views.fragments.detail.DetailFragment

class HomeFragment : Fragment() {

    private lateinit var rvUsers: RecyclerView
    private lateinit var adapter: UserAdapter
    private lateinit var toolbar: Toolbar
    private lateinit var progressBar: ProgressBar
    private lateinit var appBarLayout: AppBarLayout
    private lateinit var collapsingToolbarLayout: CollapsingToolbarLayout
    private lateinit var linearLayout: LinearLayout
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        setup()
        showRecycleList()
        appBarLayoutListener()
        homeViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.opt_search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = resources.getString(R.string.sv_hint)
        searchView.setBackgroundColor(
            ContextCompat.getColor(
                requireContext().applicationContext,
                R.color.colorWhite
            )
        )
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val find = requireContext().applicationContext.getString(R.string.find)
                toast(activity, "$find: $query")
                homeViewModel.setRequestApi(query)
                showLoading(true)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.opt_search -> {
                appBarLayout.setExpanded(false, true)
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
            R.id.opt_favorite -> {
                val mFavoritesFragment = FavoritesFragment()
                val mFragmentManager = activity?.supportFragmentManager
                mFragmentManager?.beginTransaction()?.apply {
                    replace(
                        R.id.frame_container,
                        mFavoritesFragment,
                        FavoritesFragment::class.java.simpleName
                    )
                    addToBackStack(null)
                    commit()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initView(view: View) {
        toolbar = view.findViewById(R.id.toolbar_home)
        rvUsers = view.findViewById(R.id.rv_home)
        progressBar = view.findViewById(R.id.progressBar_home)
        appBarLayout = view.findViewById(R.id.appBar_home)
        collapsingToolbarLayout = view.findViewById(R.id.collapsing_home)
        linearLayout = view.findViewById(R.id.linearLayout_home)
    }

    private fun setup() {
        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        homeViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(HomeViewModel::class.java)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun showRecycleList() {
        adapter = UserAdapter(ArrayList())
        rvUsers.setHasFixedSize(true)
        rvUsers.layoutManager = LinearLayoutManager(context)
        rvUsers.adapter = adapter
        adapter.setOnItemClickCallback(object :
            OnItemClickCallback {
            override fun onItemClicked(dataItem: DataItem) {
                moveToDetail(dataItem)
            }

            override fun onItemClicked(favorite: FavoriteEntity) {}
            override fun onDeleteClicked(favorite: FavoriteEntity) {}
        })
    }

    private fun moveToDetail(dataItem: DataItem) {
        val mDetailFragment = DetailFragment()
        val mFragmentManager = activity?.supportFragmentManager
        mFragmentManager?.beginTransaction()?.apply {
            replace(R.id.frame_container, mDetailFragment, DetailFragment::class.java.simpleName)
            addToBackStack(null)
            commit()
        }
        sharedViewModel.setDataItem(dataItem)
    }

    private fun homeViewModel() {
        homeViewModel.getDataApi().observe(viewLifecycleOwner, { dataItem ->
            if (dataItem != null) {
                adapter.clearUser()
                adapter.addNewData(dataItem)
            } else {
                homeViewModel.getFailure().observe(viewLifecycleOwner, { fail ->
                    toast(activity, "Error: $fail")
                })
            }
            showLoading(false)
        })
    }

    private fun appBarLayoutListener() {
        val titleOn = requireContext().applicationContext.getString(R.string.tv_homeInformation)
        val titleOff = ""
        context?.let { it ->
            ContextCompat.getColor(it, R.color.colorWhite).let {
                collapsingToolbarLayout.setCollapsedTitleTextColor(it)
                collapsingToolbarLayout.setExpandedTitleColor(it)
            }
        }
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener
        { appBarLayout, verticalOffset ->
            when {
                kotlin.math.abs(verticalOffset) - appBarLayout.totalScrollRange == 0 -> {
                    linearLayout.visibility = View.INVISIBLE
                    collapsingToolbarLayout.title = titleOn
                }
                verticalOffset == 0 -> {
                    collapsingToolbarLayout.title = titleOff
                    linearLayout.visibility = View.VISIBLE
                    toolbar.collapseActionView()
                }
                else -> {
                    collapsingToolbarLayout.title = titleOff
                    linearLayout.visibility = View.VISIBLE
                    toolbar.collapseActionView()
                }
            }
        })
    }
}