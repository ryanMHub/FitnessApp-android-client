package com.ryanmhub.fitnessapp.android_client.features.register.data

import com.ryanmhub.fitnessapp.android_client.common.data.UserDTO
import com.ryanmhub.fitnessapp.android_client.common.retrofit.RetrofitSingleton
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApiService {
    @POST("/api/auth/register")
    suspend fun registerUser(@Body request: UserDTO): Response<RegisterResponse>
}

object RegisterApi {
    fun create() : RegisterApiService? {
        val retrofit = RetrofitSingleton.getRetrofit()
        if(retrofit!=null){
            return retrofit.create(RegisterApiService::class.java)
        }
        return null;
    }

}