package com.project.findgithubuser.network

import com.project.findgithubuser.BuildConfig
import com.project.findgithubuser.models.DataItem
import com.project.findgithubuser.models.DetailUser
import com.project.findgithubuser.models.ResponseUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("Authorization: token ${BuildConfig.ApiKey}")
    @GET("/search/users")
    fun getListUsers(@Query("q") q: String): Call<ResponseUser>

    @Headers("Authorization: token 30061bc156e3d8fb2c8a503d2af781dbf68eaa73")
    @GET("/users/{username}")
    fun getDetailUser(@Path("username") user: String): Call<DetailUser>

    @Headers("Authorization: token 30061bc156e3d8fb2c8a503d2af781dbf68eaa73")
    @GET("/users/{username}/followers")
    fun getFollowersUser(@Path("username") user: String): Call<List<DataItem>>

    @Headers("Authorization: token 30061bc156e3d8fb2c8a503d2af781dbf68eaa73")
    @GET("/users/{username}/following")
    fun getFollowingUser(@Path("username") user: String): Call<List<DataItem>>
}