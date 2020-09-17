package com.project.findgithubuser.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.findgithubuser.models.DataItem
import com.project.findgithubuser.network.ApiConfig
import retrofit2.Call
import retrofit2.Response

class FollowViewModel : ViewModel() {
    private var listDataFollowers = MutableLiveData<List<DataItem>>()
    private var listDataFollowing = MutableLiveData<List<DataItem>>()
    private var statusFollowers = MutableLiveData<String>()
    private var statusFollowing = MutableLiveData<String>()

    fun setRequestApi(point: String, boolean: Boolean) {
        var client: Call<List<DataItem>>
        when (boolean) {
            true -> {
                client = ApiConfig.getApiService().getFollowersUser(point)
                if (point != statusFollowers.value.toString()) {
                    listDataFollowers.value = null
                }
            }
            false -> {
                client = ApiConfig.getApiService().getFollowingUser(point)
                if (point != statusFollowing.value.toString()) {
                    listDataFollowing.value = null
                }
            }
        }
        client.enqueue(object : retrofit2.Callback<List<DataItem>> {
            override fun onResponse(
                call: Call<List<DataItem>>,
                response: Response<List<DataItem>>
            ) {
                if (boolean) {
                    val dataArray = response.body() as List<DataItem>
                    listDataFollowers.postValue(dataArray)
                    statusFollowers.postValue(point)
                } else {
                    val dataArray = response.body() as List<DataItem>
                    listDataFollowing.postValue(dataArray)
                    statusFollowing.postValue(point)
                }
            }

            override fun onFailure(call: Call<List<DataItem>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun getDataFollowers(): LiveData<List<DataItem>> {
        return listDataFollowers
    }

    fun getDataFollowing(): LiveData<List<DataItem>> {
        return listDataFollowing
    }
}