package com.ryanmhub.fitnessapp.android_client.common.retrofit

import com.ryanmhub.fitnessapp.android_client.features.login.data.LoginResponse
import retrofit2.Response
import retrofit2.http.GET

interface RefreshTokenApiService {
    @GET("/api/auth/refresh-token")
    suspend fun getNewAccessToken() : Response<LoginResponse>
}

object RefreshTokenApi {
    fun create() : RefreshTokenApiService? {
        val retrofit = RetrofitSingleton.getRetrofit()
        if(retrofit!=null){
            return retrofit.create(RefreshTokenApiService::class.java)
        }
        return null
    }
}