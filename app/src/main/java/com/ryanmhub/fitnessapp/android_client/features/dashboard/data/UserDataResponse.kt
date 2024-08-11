package com.ryanmhub.fitnessapp.android_client.features.dashboard.data

import com.google.gson.annotations.SerializedName
import com.ryanmhub.fitnessapp.android_client.common.retrofit.response.ApiResponse

class UserDataResponse(
    message : String,
    success : Boolean,
    @SerializedName("first_name") val firstName : String,
    @SerializedName("last_name") val lastName : String,
    @SerializedName("username") val username : String,
    @SerializedName("email") val email : String,
    @SerializedName("password") val password : String
) : ApiResponse(message, success)