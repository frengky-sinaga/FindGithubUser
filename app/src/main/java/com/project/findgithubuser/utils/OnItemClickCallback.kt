package com.project.findgithubuser.utils

import com.project.findgithubuser.entity.FavoriteEntity
import com.project.findgithubuser.models.DataItem

interface OnItemClickCallback {
    fun onItemClicked(dataItem: DataItem)
    fun onItemClicked(favorite: FavoriteEntity)
    fun onDeleteClicked(favorite: FavoriteEntity)
}