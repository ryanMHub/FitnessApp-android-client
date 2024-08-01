package com.ryanmhub.fitnessapp.android_client.common.retrofit

import com.ryanmhub.fitnessapp.android_client.features.register.data.RegisterDTO
import com.ryanmhub.fitnessapp.android_client.features.register.data.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApiService {
    @POST("/api/auth/register")
    suspend fun registerUser(@Body request:RegisterDTO): Response<RegisterResponse>


}