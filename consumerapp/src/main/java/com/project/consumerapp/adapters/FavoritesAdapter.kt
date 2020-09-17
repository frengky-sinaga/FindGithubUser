package com.project.consumerapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.project.consumerapp.R
import com.project.consumerapp.entity.FavoriteEntity

class FavoritesAdapter(private val favorites: ArrayList<FavoriteEntity>) :
    RecyclerView.Adapter<FavoritesAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_list_favorites, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = favorites.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val fav = favorites[position]

        holder.tvName.text = fav.username
        holder.tvEmail.text = fav.email
        Glide.with(holder.itemView.context)
            .load(fav.img)
            .apply(RequestOptions().override(70, 70))
            .into(holder.ivAvatar)
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tv_itemListFav_name)
        var tvEmail: TextView = itemView.findViewById(R.id.tv_itemListFav_email)
        var ivAvatar: ImageView = itemView.findViewById(R.id.img_itemListFav)
    }

    fun addNewData(newData: ArrayList<FavoriteEntity>) {
        favorites.addAll(newData)
        notifyDataSetChanged()
    }

    fun clearUser() {
        favorites.clear()
        notifyDataSetChanged()
    }
}