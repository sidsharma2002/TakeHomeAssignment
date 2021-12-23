package com.siddharth.takehomeassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.siddharth.takehomeassignment.data.user.User
import com.siddharth.takehomeassignment.repository.HomeRepository
import com.siddharth.takehomeassignment.utils.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repository = HomeRepository()

    private val _userList = MutableLiveData<Response<MutableList<User>?>>()
    var userList: LiveData<Response<MutableList<User>?>> = _userList

    init {
        fetchUserList()
    }

    private fun fetchUserList() = CoroutineScope(Dispatchers.Main).launch {
        _userList.postValue(Response.Loading())
        val result = repository.fetchUserData()
        _userList.postValue(result)
    }
}