package com.ryanmhub.fitnessapp.android_client.common.retrofit

import com.ryanmhub.fitnessapp.android_client.features.login.data.LoginDTO
import com.ryanmhub.fitnessapp.android_client.features.login.data.LoginResponse
import com.ryanmhub.fitnessapp.android_client.common.data.UserDTO
import com.ryanmhub.fitnessapp.android_client.features.register.data.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApiService {
    @POST("/api/auth/register")
    suspend fun registerUser(@Body request: UserDTO): Response<RegisterResponse>

    @POST("/api/auth/login")
    suspend fun loginUser(@Body request: LoginDTO) : Response<LoginResponse>

}