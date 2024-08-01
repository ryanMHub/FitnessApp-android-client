package com.ryanmhub.fitnessapp.android_client.common.retrofit.response

//Base class all response classes are built from
open class ApiResponse(
    val message: String,
    val success: Boolean
)