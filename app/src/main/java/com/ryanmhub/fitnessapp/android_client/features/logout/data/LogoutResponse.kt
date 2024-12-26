package com.ryanmhub.fitnessapp.android_client.features.logout.data

import com.google.gson.annotations.SerializedName
import com.ryanmhub.fitnessapp.android_client.common.retrofit.response.ApiResponse

class LogoutResponse (
    message: String,
    success : Boolean,
    @SerializedName("logout_info") val logoutInfo : String
) : ApiResponse(message, success)