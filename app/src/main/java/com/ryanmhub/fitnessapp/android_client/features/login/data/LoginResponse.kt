package com.ryanmhub.fitnessapp.android_client.features.login.data

import com.google.gson.annotations.SerializedName
import com.ryanmhub.fitnessapp.android_client.common.retrofit.response.ApiResponse

class LoginResponse(
    message : String,
    success : Boolean,
    @SerializedName("access_token") val accessToken : String,
    @SerializedName("refresh_token") val refreshToken : String
) : ApiResponse(message, success)