package com.project.findgithubuser.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.findgithubuser.models.DetailUser
import com.project.findgithubuser.network.ApiConfig
import retrofit2.Call
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private var listData = MutableLiveData<DetailUser>()

    fun setRequestApi(point: String) {
        val client = ApiConfig.getApiService().getDetailUser(point)
        client.enqueue(object : retrofit2.Callback<DetailUser> {
            override fun onResponse(call: Call<DetailUser>, response: Response<DetailUser>) {
                val result = response.body()
                listData.postValue(result)
            }

            override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                t.printStackTrace()
                listData.postValue(null)
            }
        })
    }

    fun getDataApi(): LiveData<DetailUser> {
        return listData
    }
}