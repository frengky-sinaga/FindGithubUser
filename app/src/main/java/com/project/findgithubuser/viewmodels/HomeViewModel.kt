package com.project.findgithubuser.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.findgithubuser.models.DataItem
import com.project.findgithubuser.models.ResponseUser
import com.project.findgithubuser.network.ApiConfig
import retrofit2.Call
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private var listData = MutableLiveData<List<DataItem>>()
    private var failure = MutableLiveData<String>()

    fun setRequestApi(point: String) {
        val client = ApiConfig.getApiService().getListUsers(point)
        client.enqueue(object : retrofit2.Callback<ResponseUser> {
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                val dataArray = response.body()?.items as List<DataItem>
                listData.postValue(dataArray)
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                t.printStackTrace()
                listData.postValue(null)
                failure.postValue(t.message.toString())
            }
        })
    }

    fun getDataApi(): LiveData<List<DataItem>> {
        return listData
    }

    fun getFailure(): LiveData<String> {
        return failure
    }
}