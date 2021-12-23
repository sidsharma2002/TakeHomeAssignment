package com.siddharth.takehomeassignment.api

import com.siddharth.takehomeassignment.constants.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserApiManager {
    val userApi: UsersApi =  Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(UsersApi::class.java)
}