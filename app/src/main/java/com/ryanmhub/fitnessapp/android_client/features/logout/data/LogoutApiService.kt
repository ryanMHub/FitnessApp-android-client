package com.ryanmhub.fitnessapp.android_client.features.logout.data

import com.ryanmhub.fitnessapp.android_client.common.retrofit.RetrofitSingleton
import com.ryanmhub.fitnessapp.android_client.features.login.data.LoginResponse
import retrofit2.Response
import retrofit2.http.POST


interface LogoutApiService {
    @POST("/api/auth/logout")
    suspend fun logoutUser() : Response<LogoutResponse>
}

object LogoutApi {
    fun create() : LogoutApiService? {
        val retrofit = RetrofitSingleton.getRetrofit()
        if(retrofit != null) return retrofit.create(LogoutApiService::class.java)
        return null
    }
}

