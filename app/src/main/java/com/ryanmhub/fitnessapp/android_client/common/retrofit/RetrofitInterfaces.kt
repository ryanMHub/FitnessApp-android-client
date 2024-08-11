package com.ryanmhub.fitnessapp.android_client.common.retrofit

import com.ryanmhub.fitnessapp.android_client.features.login.data.LoginDTO
import com.ryanmhub.fitnessapp.android_client.features.login.data.LoginResponse
import com.ryanmhub.fitnessapp.android_client.common.data.UserDTO
import com.ryanmhub.fitnessapp.android_client.features.dashboard.data.UserDataResponse
import com.ryanmhub.fitnessapp.android_client.features.register.data.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

//Todo: Delete this once Retrofit overhaul is completed
interface UserApiService {
    @POST("/api/auth/register")
    suspend fun registerUser(@Body request: UserDTO): Response<RegisterResponse>

    @POST("/api/auth/login")
    suspend fun loginUser(@Body request: LoginDTO) : Response<LoginResponse>

}

interface DashboardApiService {
    @GET("/test/print")
    suspend fun getUserData() : Response<UserDataResponse>
}

//endpoint refreshes the access token using the refresh token
interface RefreshTokenService {
    @GET("/api/auth/refresh-token")
    suspend fun getNewAccessToken() : Response<LoginResponse>
}