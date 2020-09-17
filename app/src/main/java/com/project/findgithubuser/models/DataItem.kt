package com.project.findgithubuser.models

import com.google.gson.annotations.SerializedName

data class DataItem(
    @field:SerializedName("login")
    val login: String? = null,
    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,
    @field:SerializedName("html_url")
    val htmlUrl: String? = null,
    @field:SerializedName("followers_url")
    val followersUrl: String? = null,
    @field:SerializedName("following_url")
    val followingUrl: String? = null
)