package com.ryanmhub.fitnessapp.android_client.features.dashboard.data

import com.ryanmhub.fitnessapp.android_client.common.retrofit.RetrofitSingleton
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET

interface DashboardApiService {
    @GET("/test/print")
    suspend fun getUserData() : Response<UserDataResponse>
}

object DashboardApi {
    fun create() : DashboardApiService? {
        val retrofit = RetrofitSingleton.getRetrofit()
        if(retrofit!=null){
            return retrofit.create(DashboardApiService::class.java)
        }
        return null
    }
}