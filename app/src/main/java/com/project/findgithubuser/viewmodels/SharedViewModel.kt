package com.project.findgithubuser.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.findgithubuser.models.DataItem

class SharedViewModel : ViewModel() {
    private var dataItem = MutableLiveData<DataItem>()
    fun setDataItem(data: DataItem) {
        dataItem.value = data
    }

    fun getDataItem(): LiveData<DataItem> {
        return dataItem
    }
}