package com.project.findgithubuser.models

import com.google.gson.annotations.SerializedName

data class ResponseUser(
    @field:SerializedName("total_count")
    val totalCount: Int? = null,
    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean? = null,
    @field:SerializedName("items")
    val items: List<DataItem>? = null
)