package com.ryanmhub.fitnessapp.android_client.common.retrofit

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstances {

    //Configure logging interceptor
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    //Build a connection with BASE_URL_API
    private val apiConnection by lazy {
        Retrofit.Builder()
            .baseUrl(ApiBaseUrl.BASE_URL_API)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //all network calls associated with registration and login
    val userService : UserApiService by lazy {
        Log.d("RetrofitInstances", "userService")
        apiConnection.create(UserApiService::class.java)
    }
}