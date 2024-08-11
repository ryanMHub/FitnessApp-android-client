package com.ryanmhub.fitnessapp.android_client.features.login.data

import com.ryanmhub.fitnessapp.android_client.common.retrofit.RetrofitSingleton
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiService {
    @POST("/api/auth/login")
    suspend fun loginUser(@Body request: LoginDTO) : Response<LoginResponse>
}

object LoginApi {
    fun create() : LoginApiService? {
        val retrofit = RetrofitSingleton.getRetrofit()
        if(retrofit != null) return retrofit.create(LoginApiService::class.java)
        return null
    }
}