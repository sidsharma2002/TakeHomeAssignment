package com.siddharth.takehomeassignment.api

import com.siddharth.takehomeassignment.data.user.User
import retrofit2.Response
import retrofit2.http.GET

interface UsersApi {
    @GET("users")
    suspend fun getUserData(): Response<MutableList<User>>
}