package com.siddharth.takehomeassignment.repository

import com.siddharth.takehomeassignment.api.UserApiManager.userApi
import com.siddharth.takehomeassignment.data.user.User
import com.siddharth.takehomeassignment.utils.Response
import com.siddharth.takehomeassignment.utils.safeCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class HomeRepository {

    suspend fun fetchUserData() = withContext(Dispatchers.IO) {
        safeCall {
            val userResponse = userApi.getUserData()
            if (userResponse.isSuccessful) {
                val result = increaseListSize(userResponse.body())
                Response.Success(result)
            } else {
                Response.Error("Not successful")
            }
        }
    }

    private suspend fun increaseListSize(body: MutableList<User>?): MutableList<User>? =  withContext(Dispatchers.Default) {
        body?.let {
            val newList = mutableListOf<User>()
            newList.addAll(it)
            newList.addAll(it)
            newList.addAll(it)
            newList
        }
    }
}