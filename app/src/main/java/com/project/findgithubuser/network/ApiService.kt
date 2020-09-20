package com.project.findgithubuser.network

import com.project.findgithubuser.models.DataItem
import com.project.findgithubuser.models.DetailUser
import com.project.findgithubuser.models.ResponseUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/search/users")
    fun getListUsers(@Query("q") q: String): Call<ResponseUser>

    @GET("/users/{username}")
    fun getDetailUser(@Path("username") user: String): Call<DetailUser>

    @GET("/users/{username}/followers")
    fun getFollowersUser(@Path("username") user: String): Call<List<DataItem>>

    @GET("/users/{username}/following")
    fun getFollowingUser(@Path("username") user: String): Call<List<DataItem>>
}