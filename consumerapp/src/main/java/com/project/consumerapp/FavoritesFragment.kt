package com.project.consumerapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.consumerapp.adapters.FavoritesAdapter
import com.project.consumerapp.viewmodels.FavoriteViewModel

class FavoritesFragment : Fragment() {

    private lateinit var adapter: FavoritesAdapter
    private lateinit var rvFavorites: RecyclerView
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initRv(view)
        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        favoriteViewModel.contentProvider(requireActivity())
    }

    private fun initRv(view: View) {
        rvFavorites = view.findViewById(R.id.rv_favorites)
        adapter = FavoritesAdapter(ArrayList())
        rvFavorites.setHasFixedSize(true)
        rvFavorites.layoutManager = LinearLayoutManager(context)
        rvFavorites.adapter = adapter
    }
}