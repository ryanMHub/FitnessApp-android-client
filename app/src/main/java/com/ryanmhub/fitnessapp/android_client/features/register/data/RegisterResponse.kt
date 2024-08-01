package com.ryanmhub.fitnessapp.android_client.features.register.data

import com.ryanmhub.fitnessapp.android_client.common.retrofit.response.ApiResponse

class RegisterResponse(
    message : String,
    success : Boolean
) : ApiResponse(message, success)