package com.project.findgithubuser.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.project.findgithubuser.R
import com.project.findgithubuser.models.DataItem
import com.project.findgithubuser.utils.OnItemClickCallback

class UserAdapter(private val users: ArrayList<DataItem>) :
    RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun clearUser() {
        users.clear()
        notifyDataSetChanged()
    }

    fun addNewData(newData: Collection<DataItem>) {
        users.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_list_github, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = users[position]

        holder.tvUsername.text = user.login
        holder.tvUrl.text = user.htmlUrl
        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .apply(RequestOptions().override(70, 70))
            .into(holder.ivAvatar)

        holder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(user)
        }
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvUsername: TextView = itemView.findViewById(R.id.tv_itemListGithub_userName)
        var tvUrl: TextView = itemView.findViewById(R.id.tv_itemListGithub_htmlUrl)
        var ivAvatar: ImageView = itemView.findViewById(R.id.img_itemListGithub)
    }
}