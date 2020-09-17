package com.project.findgithubuser.models

import com.google.gson.annotations.SerializedName

data class DetailUser(
    @field:SerializedName("name")
    val name: String? = null,
    @field:SerializedName("email")
    val email: String? = null,
    @field:SerializedName("location")
    val location: String? = null,
    @field:SerializedName("followers")
    val followers: Int? = null,
    @field:SerializedName("following")
    val following: Int? = null,
    @field:SerializedName("bio")
    val bio: String? = null
)